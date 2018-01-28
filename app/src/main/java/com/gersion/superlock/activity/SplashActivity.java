package com.gersion.superlock.activity;

import android.Manifest;
import android.content.Intent;

import com.gersion.superlock.base.BasePermissionActivity;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.PermissionHelper;

public class SplashActivity extends BasePermissionActivity {
    private static final String[] PERMISSIONS = {
//            Manifest.permission.CAMERA,
//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        checkPermissions();
    }

    @Override
    protected void initData() {

    }

    private void onComplete() {
        Intent intent;
        if (ConfigManager.getInstance().isFinishGuide()) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, GuideActivity.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void checkPermissions() {
        super.checkPermissions();
        PermissionHelper instance = PermissionHelper.getInstance();
        boolean hasPermission = instance.hasPermission(this, PERMISSIONS);
        if (hasPermission) {
            onComplete();
        } else {
            instance
                    .requestAllPermission(this, new PermissionHelper.PermissionResultCallback() {
                        @Override
                        public void onSuccess() {
                            onComplete();
                        }

                        @Override
                        public void onFailed() {
                            onComplete();
                        }
                    });
        }
    }
}
