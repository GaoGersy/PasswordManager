package com.gersion.superlock.utils;

import android.content.Context;

import com.gersion.superlock.activity.SuperLockApplication;

import static com.gersion.superlock.utils.MyConstants.ENABLE_FLOAT_BALL;
import static com.gersion.superlock.utils.MyConstants.FINGER_PRINT;
import static com.gersion.superlock.utils.MyConstants.IS_AUTO_LOGIN;
import static com.gersion.superlock.utils.MyConstants.IS_CHANGE_PWD;
import static com.gersion.superlock.utils.MyConstants.IS_FINISH_GUIDE;
import static com.gersion.superlock.utils.MyConstants.IS_LOCK;
import static com.gersion.superlock.utils.MyConstants.IS_SHOW_PWD;
import static com.gersion.superlock.utils.MyConstants.IS_SHOW_UPDATE_TIME;
import static com.gersion.superlock.utils.MyConstants.LENGTH;

/**
 * Created by gersy on 2017/7/1.
 */

public class ConfigManager {
    private boolean mFingerPrint;
    private boolean mIsEnableFloatBall;
    private int mPwdLength;
    private boolean mIsChangePwd;
    private boolean mIsAutoLogin;
    private boolean mIsLock;
    private boolean mIsShowPwd;
    private boolean mIsShowUpdateTime;
    private boolean mIsFinishGuide;
    private Context mContext;

    private ConfigManager() {
        mContext = SuperLockApplication.getContext();
        mIsLock = getBoolean(IS_LOCK, true);
        mIsShowPwd = getBoolean(IS_SHOW_PWD, true);
        mIsAutoLogin = getBoolean(IS_AUTO_LOGIN);
        mIsShowUpdateTime = getBoolean(IS_SHOW_UPDATE_TIME);
        mIsChangePwd = getBoolean(IS_CHANGE_PWD);
        mIsFinishGuide = getBoolean(IS_FINISH_GUIDE);
        mIsEnableFloatBall = getBoolean(ENABLE_FLOAT_BALL);
        mFingerPrint = getBoolean(FINGER_PRINT);
        mPwdLength = SpfUtils.getInt(mContext, LENGTH, 0);
    }
    private boolean getBoolean(String key) {
        return SpfUtils.getBoolean(mContext, key,false);
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        return SpfUtils.getBoolean(mContext, key, defaultValue);
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
        SpfUtils.putInt(mContext, LENGTH, pwdLength);
    }

    public boolean isChangePwd() {
        return mIsChangePwd;
    }

    public void setChangePwd(boolean changePwd) {
        mIsChangePwd = changePwd;
        SpfUtils.putBoolean(mContext, IS_CHANGE_PWD, changePwd);
    }

    public boolean isAutoLogin() {
        return mIsAutoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        mIsAutoLogin = autoLogin;
        SpfUtils.putBoolean(mContext, IS_AUTO_LOGIN, autoLogin);
    }

    public boolean isLock() {
        return mIsLock;
    }

    public void setLock(boolean lock) {
        mIsLock = lock;
        SpfUtils.putBoolean(mContext, IS_LOCK, lock);
    }

    public boolean isShowPwd() {
        return mIsShowPwd;
    }

    public void setShowPwd(boolean showPwd) {
        mIsShowPwd = showPwd;
        SpfUtils.putBoolean(mContext, IS_SHOW_PWD, showPwd);
    }

    public boolean isShowUpdateTime() {
        return mIsShowUpdateTime;
    }

    public void setShowUpdateTime(boolean showUpdateTime) {
        mIsShowUpdateTime = showUpdateTime;
        SpfUtils.putBoolean(mContext, IS_SHOW_UPDATE_TIME, showUpdateTime);
    }

    public boolean isFinishGuide() {
        return mIsFinishGuide;
    }

    public void setFinishGuide(boolean finishGuide) {
        mIsFinishGuide = finishGuide;
        SpfUtils.putBoolean(mContext, IS_FINISH_GUIDE, finishGuide);
    }

    public boolean isFingerPrint() {
        return mFingerPrint;
    }

    public void setFingerPrint(boolean fingerPrint) {
        mFingerPrint = fingerPrint;
        SpfUtils.putBoolean(mContext, FINGER_PRINT, fingerPrint);
    }

    public boolean isEnableFloatBall() {
        return mIsEnableFloatBall;
    }

    public void setEnableFloatBall(boolean enableFloatBall){
        mIsEnableFloatBall = enableFloatBall;
        SpfUtils.putBoolean(mContext, ENABLE_FLOAT_BALL, enableFloatBall);
    }
}
