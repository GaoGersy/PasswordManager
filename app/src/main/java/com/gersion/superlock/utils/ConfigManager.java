package com.gersion.superlock.utils;

import android.content.Context;

import com.gersion.superlock.app.SuperLockApplication;

import java.io.File;

import static com.gersion.superlock.utils.MyConstants.APP_PASSWORD;
import static com.gersion.superlock.utils.MyConstants.CREATE_DB_DATE;
import static com.gersion.superlock.utils.MyConstants.CREATE_LOCK_DATE;
import static com.gersion.superlock.utils.MyConstants.ENABLE_FLOAT_BALL;
import static com.gersion.superlock.utils.MyConstants.FINGER_PRINT;
import static com.gersion.superlock.utils.MyConstants.IS_AUTO_LOGIN;
import static com.gersion.superlock.utils.MyConstants.IS_CHANGE_PWD;
import static com.gersion.superlock.utils.MyConstants.IS_FINISH_GUIDE;
import static com.gersion.superlock.utils.MyConstants.IS_LOCK;
import static com.gersion.superlock.utils.MyConstants.IS_SHOW_PWD;
import static com.gersion.superlock.utils.MyConstants.IS_SHOW_UPDATE_TIME;
import static com.gersion.superlock.utils.MyConstants.LENGTH;
import static com.gersion.superlock.utils.MyConstants.LOCK_TYPE;
import static com.gersion.superlock.utils.MyConstants.PATTERN_STRING;
import static com.gersion.superlock.utils.MyConstants.SUPER_PASSWORD_SETED;
import static com.gersion.superlock.utils.MyConstants.SUPER_PASSWORD;
import static com.gersion.superlock.utils.MyConstants.USER_NAME;

/**
 * Created by gersy on 2017/7/1.
 */

public class ConfigManager {
    private boolean mSuperPasswordSeted;
    private int mLockType;
    private String mAppPassword;
    private String mPatternString;
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
    private String mUserName;
    private String mSuperPassword;

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
        mUserName = SpfUtils.getString(mContext, USER_NAME);
        mSuperPassword = SpfUtils.getString(mContext, SUPER_PASSWORD);
        mAppPassword = SpfUtils.getString(mContext, APP_PASSWORD);
        mPatternString = SpfUtils.getString(mContext, PATTERN_STRING);
        mLockType = SpfUtils.getInt(mContext, LOCK_TYPE);
        mSuperPasswordSeted = SpfUtils.getBoolean(mContext, SUPER_PASSWORD_SETED);
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

    public String getCreateDbDate() {
        return SpfUtils.getString(mContext, CREATE_DB_DATE);
    }

    public void setCreateDbDate(String createDate) {
        SpfUtils.putString(mContext,CREATE_DB_DATE,createDate);
    }

    public String getCreateLockDate() {
        return  SpfUtils.getString(mContext, CREATE_LOCK_DATE);
    }

    public void setCreateLockDate(String createDate) {
        SpfUtils.putString(mContext,CREATE_LOCK_DATE,createDate);
    }

    public String getSuperPassword() {
        return mSuperPassword;
    }

    public void setSuperPassword(String superPassword) {
        mSuperPassword = superPassword;
        SpfUtils.putString(mContext,SUPER_PASSWORD,superPassword);
    }

    public File getSrcDbFile() {
        return null;
    }

    public File getDestDbFile() {
        return null;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
        SpfUtils.putString(mContext,USER_NAME,userName);
    }

    public String getPatternString() {
        return mPatternString;
    }

    public void setPatternString(String patternString) {
        mPatternString = patternString;
        SpfUtils.putString(mContext,PATTERN_STRING,patternString);
    }

    public String getAppPassword() {
        return mAppPassword;
    }

    public void setAppPassword(String appPassword) {
        mAppPassword = Md5Utils.encodeWithTimes(appPassword, 2);
        SpfUtils.putString(mContext,APP_PASSWORD,mAppPassword);
    }

    public int getLockType() {
        return mLockType;
    }

    public void setLockType(int lockType) {
        mLockType = lockType;
        SpfUtils.putInt(mContext,LOCK_TYPE,lockType);
    }

    public boolean isSuperPasswordSeted() {
        return mSuperPasswordSeted;
    }

    public void setSuperPasswordSeted(boolean superPasswordSeted) {
        mSuperPasswordSeted = superPasswordSeted;
        SpfUtils.putBoolean(mContext, SUPER_PASSWORD_SETED,superPasswordSeted);
    }
}
