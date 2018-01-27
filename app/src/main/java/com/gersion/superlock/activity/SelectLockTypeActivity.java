package com.gersion.superlock.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.view.TitleView;

public class SelectLockTypeActivity extends BaseActivity {
    private static final int CODE = 100;
    private TextView mTvPin;
    private TextView mTvPattern;
    private TextView mTvFingerPrint;
    private TitleView mTitleView;
    private ConfigManager mConfigManager;
    private int currentMode;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_select_lock_type;
    }

    @Override
    protected void initView() {
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mTvPin = (TextView) findViewById(R.id.tv_pin);
        mTvPattern = (TextView) findViewById(R.id.tv_pattern);
        mTvFingerPrint = (TextView) findViewById(R.id.tv_finger_print);
        mConfigManager = ConfigManager.getInstance();
        int lockType = mConfigManager.getLockType();
        mTvPin.setSelected(lockType == MyConstants.LockType.TYPE_PIN);
        mTvPattern.setSelected(lockType == MyConstants.LockType.TYPE_PATTERN);
        mTvFingerPrint.setSelected(lockType == MyConstants.LockType.TYPE_FINGER_PRINT);

        mTitleView.setTitleText("密码设置")
                .setAddVisiable(false)
                .setSearchVisiable(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTvPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patternString = mConfigManager.getPatternString();
                changeLockType(MyConstants.LockType.TYPE_PATTERN);
                if (patternString == null) {
                    toActivityForResult(RegisterActivity.class,CODE);
                }
            }
        });
        mTvPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLockType(MyConstants.LockType.TYPE_PIN);
            }
        });
        mTvFingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLockType(MyConstants.LockType.TYPE_FINGER_PRINT);
            }
        });
    }

    private void changeLockType(int lockType) {
        mTvPin.setSelected(lockType == MyConstants.LockType.TYPE_PIN);
        mTvPattern.setSelected(lockType == MyConstants.LockType.TYPE_PATTERN);
        mTvFingerPrint.setSelected(lockType == MyConstants.LockType.TYPE_FINGER_PRINT);
        mConfigManager.setLockType(lockType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==resultCode){
            if (requestCode==CODE){
                changeLockType(MyConstants.LockType.TYPE_PATTERN);
            }
        }else {
            changeLockType(MyConstants.LockType.TYPE_PIN);
        }
    }
}
