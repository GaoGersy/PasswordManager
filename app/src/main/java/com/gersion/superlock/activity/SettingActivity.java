package com.gersion.superlock.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.SettingView;
import com.gersion.superlock.view.TitleView;
import com.gersion.superlock.view.ToggleButton;
import com.sdsmdg.tastytoast.TastyToast;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView mTitleView;
    @BindView(R.id.auto_login)
    SettingView mAutoLogin;
    @BindView(R.id.open_lock)
    SettingView mOpenLock;
    @BindView(R.id.pwd_show)
    SettingView mPwdShow;
    @BindView(R.id.update_time_show)
    SettingView mUpdateTimeShow;
    @BindView(R.id.donation)
    SettingView mDonation;
    @BindView(R.id.activity_setting)
    LinearLayout mActivitySetting;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleView.setTitleText("设置");
        mTitleView.setAddVisiable(false)
                .setSearchVisiable(false);
    }

    @Override
    protected void initData() {
        boolean isAutoLogin = SpfUtils.getBoolean(this, MyConstants.IS_AUTO_LOGIN, false);
        boolean isLock = SpfUtils.getBoolean(this, MyConstants.IS_LOCK, true);
        boolean isShowPwd = SpfUtils.getBoolean(this, MyConstants.IS_SHOW_PWD, true);
        boolean isShowUpdateTime = SpfUtils.getBoolean(this, MyConstants.IS_SHOW_UPDATE_TIME, false);
        mOpenLock.setSwitchStatus(isLock);
        mPwdShow.setSwitchStatus(isShowPwd);
        mAutoLogin.setSwitchStatus(isAutoLogin);
        mUpdateTimeShow.setSwitchStatus(isShowUpdateTime);
        mAutoLogin.setEnableEffect(isLock);
    }

    @Override
    protected void initListener() {
        mAutoLogin.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean b) {
                if (b) {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_AUTO_LOGIN, true);
                    ToastUtils.showTasty(SettingActivity.this, "开启自动登录", TastyToast.SUCCESS);
                } else {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_AUTO_LOGIN, false);
                    ToastUtils.showTasty(SettingActivity.this, "关闭自动登录", TastyToast.INFO);
                }
            }
        });

        mOpenLock.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean b) {
                mAutoLogin.setEnableEffect(b);
                if (b) {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_LOCK, true);
                    ToastUtils.showTasty(SettingActivity.this, "开启程序锁", TastyToast.SUCCESS);
                } else {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_LOCK, false);
                    ToastUtils.showTasty(SettingActivity.this, "关闭程序锁", TastyToast.INFO);
                }
            }
        });

        mPwdShow.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean b) {
                if (b) {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_SHOW_PWD, true);
                    ToastUtils.showTasty(SettingActivity.this, "开启显示密码", TastyToast.SUCCESS);
                } else {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_SHOW_PWD, false);
                    ToastUtils.showTasty(SettingActivity.this, "关闭显示密码", TastyToast.INFO);
                }
                EventBus.getDefault().postSticky("ChangSwitch");
            }
        });
        mUpdateTimeShow.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean b) {
                if (b) {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_SHOW_UPDATE_TIME, true);
                    ToastUtils.showTasty(SettingActivity.this, "开启显示更新时间", TastyToast.SUCCESS);
                } else {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_SHOW_UPDATE_TIME, false);
                    ToastUtils.showTasty(SettingActivity.this, "关闭显示更新时间", TastyToast.INFO);
                }
            }
        });

        mDonation.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(AboutActivity.class);
            }
        });
    }


}
