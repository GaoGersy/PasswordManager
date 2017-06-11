package com.gersion.superlock.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.ToggleButton;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.tb_auto_login)
    ToggleButton mTbAutoLogin;
    @BindView(R.id.tb_is_lock)
    ToggleButton mTbIsLock;
    @BindView(R.id.tb_show_pwd)
    ToggleButton mTbShowPwd;
    @BindView(R.id.tb_show_update_time)
    ToggleButton mTbShowUpdateTime;
    @BindView(R.id.tv_donation)
    TextView mTvDonation;
    @BindView(R.id.tv_about)
    TextView mTvAbout;
    @BindView(R.id.activity_setting)
    LinearLayout mActivitySetting;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTbAutoLogin.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_AUTO_LOGIN, true);
                    ToastUtils.showTasty(SettingActivity.this, "开启自动登录", TastyToast.SUCCESS);
                } else {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_AUTO_LOGIN, false);
                    ToastUtils.showTasty(SettingActivity.this, "关闭自动登录", TastyToast.INFO);
                }
            }
        });
        mTbIsLock.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_LOCK, true);
                    mTbAutoLogin.setEnabled(true);
                    ToastUtils.showTasty(SettingActivity.this, "开启程序锁", TastyToast.SUCCESS);
                } else {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_LOCK, false);
                    mTbAutoLogin.setEnabled(false);
                    ToastUtils.showTasty(SettingActivity.this, "关闭程序锁", TastyToast.INFO);
                }

            }
        });
        mTbShowPwd.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_SHOW_PWD, true);
                    ToastUtils.showTasty(SettingActivity.this, "开启显示密码", TastyToast.SUCCESS);
                } else {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_SHOW_PWD, false);
                    ToastUtils.showTasty(SettingActivity.this, "关闭显示密码", TastyToast.INFO);
                }
                EventBus.getDefault().postSticky("ChangSwitch");
            }
        });
        mTbShowUpdateTime.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                if (on) {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_SHOW_UPDATE_TIME, true);
                    ToastUtils.showTasty(SettingActivity.this, "开启显示更新时间", TastyToast.SUCCESS);
                } else {
                    SpfUtils.putBoolean(SettingActivity.this, MyConstants.IS_SHOW_UPDATE_TIME, false);
                    ToastUtils.showTasty(SettingActivity.this, "关闭显示更新时间", TastyToast.INFO);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean isAutoLogin = SpfUtils.getBoolean(this, MyConstants.IS_AUTO_LOGIN, false);
        setToggleButton(isAutoLogin, mTbAutoLogin);
        boolean isLock = SpfUtils.getBoolean(this, MyConstants.IS_LOCK, true);
        setToggleButton(isLock, mTbIsLock);
        boolean isShowPwd = SpfUtils.getBoolean(this, MyConstants.IS_SHOW_PWD, true);
        setToggleButton(isShowPwd, mTbShowPwd);
        boolean isShowUpdateTime = SpfUtils.getBoolean(this, MyConstants.IS_SHOW_UPDATE_TIME, false);
        setToggleButton(isShowUpdateTime, mTbShowUpdateTime);
        if (!isLock) {
            mTbAutoLogin.setEnabled(false);
        }
    }

    private void setToggleButton(boolean isOpen, ToggleButton tb) {
        if (isOpen) {
            tb.setToggleOn();
        } else {
            tb.setToggleOff();
        }
    }

    @OnClick({R.id.tv_donation, R.id.tv_about})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_donation:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.tv_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }

}
