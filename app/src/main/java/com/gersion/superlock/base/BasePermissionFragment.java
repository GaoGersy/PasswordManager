package com.gersion.superlock.base;

import android.content.Intent;

import com.gersion.superlock.utils.PermissionHelper;

public abstract class BasePermissionFragment extends BaseFragment {
    protected boolean mIsCheckPermission = false;//是否申请权限

    protected void checkPermissions() {
        mIsCheckPermission = true;
    }

    private void onCheck(String... permissions) {
        boolean hasPermission = PermissionHelper.getInstance().hasPermission(getActivity(), permissions);
        if (hasPermission) {
            onPermissionSuccess();
        } else {
            PermissionHelper.getInstance()
                    .requestPermission(this, new PermissionHelper.PermissionResultCallback() {
                        @Override
                        public void onSuccess() {
                            onPermissionSuccess();
                        }

                        @Override
                        public void onFailed() {
                            onPermissionFialed();
                        }
                    }, permissions);
        }
    }

    //定位权限申请
    protected void checkLocationPermission() {
        onCheck(PermissionHelper.ACCESS_FINE_LOCATION);
    }

    protected void checkExternalPermission() {
        onCheck(PermissionHelper.WRITE_EXTERNAL_STORAGE);
    }

    //相机权限申请
    protected void checkCameraPermission() {
        PermissionHelper.getInstance()
                .requestCameraPermission(this, new PermissionHelper.PermissionResultCallback() {
                    @Override
                    public void onSuccess() {
                        onPermissionSuccess();
                    }

                    @Override
                    public void onFailed() {
                        onPermissionFialed();
                    }
                });
    }

    protected void onPermissionSuccess() {

    }

    protected void onPermissionFialed() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mIsCheckPermission && requestCode == PermissionHelper.PERMISSION_CODE) {
            mIsCheckPermission = false;
            checkPermissions();
        }
    }
}
