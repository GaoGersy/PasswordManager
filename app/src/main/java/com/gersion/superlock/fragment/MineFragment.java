package com.gersion.superlock.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.activity.AboutActivity;
import com.gersion.superlock.activity.BackupDataActivity;
import com.gersion.superlock.activity.DonationActivity;
import com.gersion.superlock.activity.ImportOldDataActivity;
import com.gersion.superlock.activity.SelectLockTypeActivity;
import com.gersion.superlock.activity.SettingActivity;
import com.gersion.superlock.base.BaseFragment;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.view.ItemView;

/**
 * Created by a3266 on 2017/6/11.
 */

public class MineFragment extends BaseFragment {
    private ImageView mIcon;
    private TextView mName;
    private ImageView mCode2d;
    private ItemView mCollection;
    private ItemView mSetting;
    private ItemView mAbout;
    private ItemView mShare;
    private ItemView mExit;
    private ItemView mBackup;
    private ItemView mRecovery;
    private ItemView mDonation;
    private View mProjectAddress;
    private View mAppLockType;
    private View mViewSuperPassword;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mIcon = findView(R.id.icon);
        mName = findView(R.id.name);
        mCode2d = findView(R.id.code2d);
        mCollection = findView(R.id.collection);
        mSetting = findView(R.id.setting);
        mAbout = findView(R.id.about);
        mShare = findView(R.id.share);
        mExit = findView(R.id.exit);
        mBackup = findView(R.id.backup);
        mRecovery = findView(R.id.recovery);
        mDonation = findView(R.id.donation);
        mProjectAddress = findView(R.id.project_address);
        mViewSuperPassword = findView(R.id.superpassword);
        mAppLockType = findView(R.id.app_lock);

        mName.setText(ConfigManager.getInstance().getUserName());
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initListener() {
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(SettingActivity.class);
            }
        });
        mBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                backupDB(getActivity());
                toActivity(BackupDataActivity.class);
            }
        });
        mRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recoveryDB();
                toActivity(ImportOldDataActivity.class);
            }
        });
        mDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(DonationActivity.class);
            }
        });
//        mProjectAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toActivity(WebActivity.class);
//            }
//        });

        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(AboutActivity.class);
            }
        });

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        mAppLockType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(SelectLockTypeActivity.class);
            }
        });

        mViewSuperPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void exit() {
        getActivity().finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
