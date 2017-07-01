package com.gersion.superlock.activity;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.service.FloatBallService;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.SettingView;
import com.gersion.superlock.view.TitleView;
import com.sdsmdg.tastytoast.TastyToast;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

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
    @BindView(R.id.float_ball)
    SettingView mFloatBall;

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
        mOpenLock.setSwitchStatus(ConfigManager.getInstance().isLock());
        mPwdShow.setSwitchStatus(ConfigManager.getInstance().isShowPwd());
        mAutoLogin.setSwitchStatus(ConfigManager.getInstance().isAutoLogin());
        mUpdateTimeShow.setSwitchStatus(ConfigManager.getInstance().isShowUpdateTime());
        mAutoLogin.setEnableEffect(ConfigManager.getInstance().isAutoLogin());
        mFloatBall.setSwitchStatus(ConfigManager.getInstance().isEnableFloatBall());
    }

    @Override
    protected void initListener() {
        mAutoLogin.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean isChecked) {
                String toast = isChecked ? "开启自动登录" : "关闭自动登录";
                ToastUtils.showTasty(SettingActivity.this, toast, TastyToast.INFO);
                ConfigManager.getInstance().setAutoLogin(isChecked);
            }
        });

        mOpenLock.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean isChecked) {
                mAutoLogin.setEnableEffect(isChecked);
                String toast = isChecked ? "开启程序锁" : "关闭程序锁";
                ToastUtils.showTasty(SettingActivity.this, toast, TastyToast.INFO);
                ConfigManager.getInstance().setLock(isChecked);
            }
        });

        mPwdShow.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean isChecked) {
                String toast = isChecked ? "开启显示密码" : "关闭显示密码";
                ToastUtils.showTasty(SettingActivity.this, toast, TastyToast.INFO);
                ConfigManager.getInstance().setShowPwd(isChecked);
                EventBus.getDefault().postSticky("ChangSwitch");
            }
        });
        mUpdateTimeShow.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton switchButton, boolean isChecked) {
                String toast = isChecked ? "开启显示更新时间" : "关闭显示更新时间";
                ToastUtils.showTasty(SettingActivity.this, toast, TastyToast.INFO);
                ConfigManager.getInstance().setShowUpdateTime(isChecked);
            }
        });

        mFloatBall.setSwitchChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                String toast = isChecked ? "开启悬浮球" : "关闭悬浮球";
                ToastUtils.showTasty(SettingActivity.this, toast, TastyToast.INFO);
//                ConfigManager.getInstance().setEnableFloatBall(isChecked);
                setFloatBall(isChecked);
            }
        });

        mDonation.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(AboutActivity.class);
            }
        });
    }


    private void checkBallPermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
                Toast.makeText(this, "请先允许FloatTools出现在顶部", Toast.LENGTH_SHORT).show();
            }
        }
        if (!getAppOps(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 1);
            Toast.makeText(this, "请先允许FloatTools出现在顶部", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFloatBall(boolean enable) {

        int type = enable ? FloatBallService.TYPE_ADD : FloatBallService.TYPE_DEL;
        Intent intent = new Intent(SettingActivity.this, FloatBallService.class);
        stopService(intent);
        Bundle data = new Bundle();
        data.putInt("type", type);
        intent.putExtras(data);
        startService(intent);
    }

    public boolean getAppOps(Context context) {
        try {
            Object object = context.getSystemService("appops");
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(24);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {

        }
        return false;
    }
}
