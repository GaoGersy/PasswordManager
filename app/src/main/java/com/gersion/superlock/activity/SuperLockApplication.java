package com.gersion.superlock.activity;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Handler;
import android.view.WindowManager;

import com.gersion.superlock.R;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.db.PasswordManager;
import com.gersion.superlock.utils.RudenessScreenHelper;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import io.realm.Realm;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock
 * @待完成
 * @创建时间 2016/9/25
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class SuperLockApplication extends Application {
    //上下文
    public static Context mContext;

    //主线程的handler
    public static Handler mHandler;
    public static FingerprintIdentify mFingerprintIdentify;
    public final static float DESIGN_WIDTH = 750; //绘制页面时参照的设计图宽度

    /**
     * 得到上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 得到主线程handler对象
     *
     * @return
     */
    public static Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        resetDensity();
    }

    public void resetDensity(){
        Point size = new Point();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        getResources().getDisplayMetrics().xdpi = size.x/DESIGN_WIDTH*72f;
    }

    @Override
    public void onCreate() {
        //初始化上下文
        mContext = getApplicationContext();
//        resetDensity();
        new RudenessScreenHelper(this, 750).activate();
        Realm.init(this);
        PasswordManager.getInstance().init(this,1);
        DbManager.getInstance().init(this,1);
        CrashReport.initCrashReport(getApplicationContext(), "83a16f6a72", false);
        CalligraphyConfig.
                initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/NotoSansHans.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
        initLogger();
        mFingerprintIdentify = new FingerprintIdentify(getApplicationContext(), new BaseFingerprint.FingerprintIdentifyExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {
                Logger.d(exception.getMessage());
            }
        });

        //初始化主线程的一个handler
        mHandler = new Handler();
        super.onCreate();

    }

    private void initLogger() {
        Logger
                .init("Gersy")                 // defaulticon PRETTYLOGGER or use just init()
                .methodCount(2)                 // defaulticon 2
//                .hideThreadInfo()               // defaulticon shown
                .logLevel(LogLevel.FULL)        // defaulticon LogLevel.FULL
                .methodOffset(0);
    }
}
