package com.gersion.superlock.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.PermissionHelper;

public class SplashActivity extends AppCompatActivity {

    private boolean mIsCheckPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
    }
    private static final String[] PERMISSIONS = {
//            Manifest.permission.CAMERA,
//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

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

    protected void checkPermissions() {
        mIsCheckPermission = true;
        PermissionHelper instance = PermissionHelper.getInstance();
        boolean hasPermission = instance.hasPermission(this, PERMISSIONS);
        if (hasPermission) {
            onComplete();
        } else {
            instance
                    .requestPermission(this, new PermissionHelper.PermissionResultCallback() {
                        @Override
                        public void onSuccess() {
                            onComplete();
                        }

                        @Override
                        public void onFailed() {
                            onComplete();
                        }
                    },PERMISSIONS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mIsCheckPermission && requestCode == PermissionHelper.PERMISSION_CODE) {
            mIsCheckPermission = false;
            checkPermissions();
        }
    }
}
