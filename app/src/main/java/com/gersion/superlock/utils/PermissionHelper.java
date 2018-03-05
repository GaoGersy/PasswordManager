package com.gersion.superlock.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.AppOpsManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.gersion.superlock.dialog.PermissionDialog;
import com.gersion.superlock.listener.OnDialogClickListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by gersy on 2017/6/20.
 */

public class PermissionHelper {
    public static final int PERMISSION_CODE = 888;
    private PermissionResultCallback mCallback;
    private Fragment mFragment;
    private Disposable mDisposable;
    private boolean shouldRequest = false;
    private boolean isGranted = false;
    private boolean isNeverAsk = false;
    private Camera mCamera;

    private PermissionHelper() {
    }

    private static final int REQUEST_CODE_PERMISSION_ALL = 1001;
    private static final int REQUEST_CODE_SETTING = 1002;
    public static String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static String CAMERA = Manifest.permission.CAMERA;
    private static Map<String, String> mMap = new HashMap<>();
    private Activity mActivity;
    private static PermissionHelper singleInstance;

    public static PermissionHelper getInstance() {
        if (singleInstance == null) {
            synchronized (PermissionHelper.class) {
                if (singleInstance == null) {
                    singleInstance = new PermissionHelper();
                    mMap.put(RECORD_AUDIO, "录音");
                    mMap.put(ACCESS_FINE_LOCATION, "定位");
                    mMap.put(READ_CONTACTS, "读取联系人");
                    mMap.put(WRITE_EXTERNAL_STORAGE, "存储卡写");
                    mMap.put(READ_EXTERNAL_STORAGE, "存储卡读");
                    mMap.put(CAMERA, "相机");
                }
            }
        }
        return singleInstance;
    }

    private void init() {
        isGranted = false;
        isNeverAsk = false;
        shouldRequest = false;
    }

    public boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        for (String permission : permissions) {
            String op = AppOpsManagerCompat.permissionToOp(permission);
            if (TextUtils.isEmpty(op)) continue;
            int result = AppOpsManagerCompat.noteProxyOp(context, op, context.getPackageName());
            if (result == AppOpsManagerCompat.MODE_IGNORED) return false;
            result = ContextCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }

    public void requestAllPermission(final Activity activity, PermissionResultCallback callback) {
        mCallback = callback;
        mActivity = activity;
        request(activity,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA);
    }

    public PermissionHelper requestPermission(final Fragment fragment, PermissionResultCallback callback, String... permissions) {
        init();
        mFragment = fragment;
        mCallback = callback;
        final List<Permission> list = new ArrayList<>();
        RxPermissions rxPermissions = new RxPermissions(fragment.getActivity());
        rxPermissions.setLogging(false);
        rxPermissions.requestEach(permissions)
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Permission permission) {
                        PermissionHelper.this.onNext(permission, list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PermissionHelper.this.onComplite(list, fragment);
                    }
                });
        return this;
    }

    private void onComplite(List<Permission> list, Object object) {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        if (isNeverAsk) {
            List<String> permissionNames = new ArrayList<String>();
            for (Permission permission : list) {
                permissionNames.add(permission.name);
            }

            Activity activity = null;
            if (object instanceof Fragment) {
                activity = ((Fragment) object).getActivity();
                showDialog(activity, permissionNames, fragmentListener);
            } else if (object instanceof Activity) {
                activity = (Activity) object;
                showDialog(activity, permissionNames, activityListener);
            }
        } else {
            if (shouldRequest) {
                if (mCallback != null) {
                    mCallback.onFailed();
                }
            } else {
                if (mCallback != null) {
                    mCallback.onSuccess();
                }
            }
        }
    }

    public void requestCameraPermission(final Object object, PermissionResultCallback callback) {
        init();
        Activity activity = null;
        OnDialogClickListener listener = null;
        if (object instanceof Fragment) {
            mFragment = (Fragment) object;
            activity = mFragment.getActivity();
            listener = fragmentListener;
        } else if (object instanceof Activity) {
            activity = (Activity) object;
            listener = activityListener;
        }
        final List<String> list = new ArrayList<>();
        mCallback = callback;
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.setLogging(false);
        final Activity finalActivity = activity;
        final OnDialogClickListener finalListener = listener;
        rxPermissions.requestEach(CAMERA)
                .subscribe(new io.reactivex.Observer<Permission>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Permission permission) {
                        if (permission.granted) {
                            releaseCamera();
                            try {
                                mCamera = Camera.open(0);
                                mCamera.startPreview();
                                releaseCamera();
                                if (mCallback != null) {
                                    mCallback.onSuccess();
                                }
                            } catch (Exception e) {
                                releaseCamera();
                                if (mCallback != null) {
                                    mCallback.onFailed();
                                }
                            }

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            if (mCallback != null) {
                                mCallback.onFailed();
                            }
                        } else {
                            list.add(CAMERA);
                            showDialog(finalActivity, list, finalListener);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                            mDisposable = null;
                        }
                    }
                });

    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private void request(final Activity activity, String... permissions) {
        init();
        final List<Permission> list = new ArrayList<>();
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.setLogging(false);
        rxPermissions.requestEach(permissions)
                .subscribe(new io.reactivex.Observer<Permission>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Permission permission) {
                        PermissionHelper.this.onNext(permission, list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        PermissionHelper.this.onComplite(list, activity);
                    }
                });
    }

    private void onNext(Permission permission, List<Permission> list) {
        if (permission.granted) {
            isGranted = true;
        } else if (permission.shouldShowRequestPermissionRationale) {
            shouldRequest = true;
        } else {
            isNeverAsk = true;
            list.add(permission);
        }
    }

    public PermissionHelper requestPermission(final Context activity, PermissionResultCallback callback, String... permissions) {
        mActivity = (Activity) activity;
        mCallback = callback;
        request(mActivity, permissions);
        return this;
    }

    private void showDialog(Activity activity, List<String> list, OnDialogClickListener listener) {
        PermissionDialog dialog = new PermissionDialog(activity, listener);
        dialog.setContent(getContent(list));
    }

    OnDialogClickListener fragmentListener = new OnDialogClickListener() {
        @Override
        public void OnConfirmClick() {
            startSetting(mFragment);
        }

        @Override
        public void OnCancelClick() {
            if (mCallback != null) {
                mCallback.onFailed();
            }
        }
    };

    OnDialogClickListener activityListener = new OnDialogClickListener() {
        @Override
        public void OnConfirmClick() {
            startSetting(mActivity);
        }

        @Override
        public void OnCancelClick() {
            if (mCallback != null) {
                mCallback.onFailed();
            }
        }
    };

    private String getContent(List<String> list) {
        StringBuffer sb = new StringBuffer();
        sb.append("我们需要");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String name = mMap.get(list.get(i));
            if (size == 1 || i == size - 1) {
                sb.append(name);
            } else {
                sb.append(name).append("、");
            }
        }
        sb.substring(0, sb.length() - 1);
        sb.append("权限，否则将影响部分功能的使用！");
        return sb.toString();
    }

    private void startSetting(Object object) {
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            startSetting(fragment);
        } else if (object instanceof Activity) {
            Activity activity = (Activity) object;
            startSetting(activity);
        }
    }

    public void startSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, PERMISSION_CODE);
    }

    public void startSetting(Fragment fragment) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", fragment.getActivity().getPackageName(), null);
        intent.setData(uri);
        fragment.startActivityForResult(intent, PERMISSION_CODE);
    }

    public interface PermissionResultCallback {
        void onSuccess();

        void onFailed();
    }
}
