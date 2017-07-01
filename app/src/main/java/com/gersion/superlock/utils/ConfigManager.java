package com.gersion.superlock.utils;

import android.content.Context;

import com.gersion.superlock.activity.SuperLockApplication;

/**
 * Created by gersy on 2017/7/1.
 */

public class ConfigManager {
    private boolean mIsEnableFloatBall;
    private int mPwdLength;
    private boolean mIsChangePwd;
    private boolean mIsAutoLogin;
    private boolean mIsLock;
    private boolean mIsShowPwd;
    private boolean mIsShowUpdateTime;
    public boolean mIsFinishGuide;
    public final Context mContext;

    private ConfigManager() {
        mContext = SuperLockApplication.getContext();
        mIsAutoLogin = SpfUtils.getBoolean(mContext, MyConstants.IS_AUTO_LOGIN, false);
        mIsLock = SpfUtils.getBoolean(mContext, MyConstants.IS_LOCK, true);
        mIsShowPwd = SpfUtils.getBoolean(mContext, MyConstants.IS_SHOW_PWD, true);
        mIsShowUpdateTime = SpfUtils.getBoolean(mContext, MyConstants.IS_SHOW_UPDATE_TIME, false);
        mPwdLength = SpfUtils.getInt(mContext, MyConstants.LENGTH, 0);
        mIsChangePwd = SpfUtils.getBoolean(mContext, MyConstants.IS_CHANGE_PWD, false);
        mIsFinishGuide = SpfUtils.getBoolean(mContext, MyConstants.IS_FINISH_GUIDE, false);
        mIsEnableFloatBall = SpfUtils.getBoolean(mContext, MyConstants.ENABLE_FLOAT_BALL, false);
    }

    private static ConfigManager singleInstance = new ConfigManager();

    public static ConfigManager getInstance() {
        return singleInstance;
    }

    public int getPwdLength() {
        return mPwdLength;
    }

    public void setPwdLength(int pwdLength) {
        mPwdLength = pwdLength;
        SpfUtils.putInt(mContext, MyConstants.LENGTH, pwdLength);
    }

    public boolean isChangePwd() {
        return mIsChangePwd;
    }

    public void setChangePwd(boolean changePwd) {
        mIsChangePwd = changePwd;
        SpfUtils.putBoolean(mContext, MyConstants.IS_CHANGE_PWD, changePwd);
    }

    public boolean isAutoLogin() {
        return mIsAutoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        mIsAutoLogin = autoLogin;
        SpfUtils.putBoolean(mContext, MyConstants.IS_AUTO_LOGIN, autoLogin);
    }

    public boolean isLock() {
        return mIsLock;
    }

    public void setLock(boolean lock) {
        mIsLock = lock;
        SpfUtils.putBoolean(mContext, MyConstants.IS_LOCK, lock);
    }

    public boolean isShowPwd() {
        return mIsShowPwd;
    }

    public void setShowPwd(boolean showPwd) {
        mIsShowPwd = showPwd;
        SpfUtils.putBoolean(mContext, MyConstants.IS_SHOW_PWD, showPwd);
    }

    public boolean isShowUpdateTime() {
        return mIsShowUpdateTime;
    }

    public void setShowUpdateTime(boolean showUpdateTime) {
        mIsShowUpdateTime = showUpdateTime;
        SpfUtils.putBoolean(mContext, MyConstants.IS_SHOW_UPDATE_TIME, showUpdateTime);
    }

    public boolean isFinishGuide() {
        return mIsFinishGuide;
    }

    public void setFinishGuide(boolean finishGuide) {
        mIsFinishGuide = finishGuide;
        SpfUtils.putBoolean(mContext, MyConstants.IS_FINISH_GUIDE, finishGuide);
    }

    public boolean isEnableFloatBall() {
        return mIsEnableFloatBall;
    }

    public void setEnableFloatBall(boolean enableFloatBall){
        mIsEnableFloatBall = enableFloatBall;
        SpfUtils.putBoolean(mContext, MyConstants.ENABLE_FLOAT_BALL, enableFloatBall);
    }
}
