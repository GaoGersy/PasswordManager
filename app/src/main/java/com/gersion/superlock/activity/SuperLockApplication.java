package com.gersion.superlock.activity;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.gersion.superlock.R;
import com.gersion.superlock.db.DbManager;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import net.sqlcipher.database.SQLiteDatabase;

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
    public void onCreate() {
        //初始化上下文
        mContext = getApplicationContext();
        Realm.init(this);
        DbManager.getInstance().init(this,1);
        SQLiteDatabase.loadLibs(this);
        CrashReport.initCrashReport(getApplicationContext(), "83a16f6a72", false);
        CalligraphyConfig.
                initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/NotoSansHans.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
        //初始化主线程的一个handler
        mHandler = new Handler();
        super.onCreate();
        Logger
                .init("Gersy")                 // defaulticon PRETTYLOGGER or use just init()
                .methodCount(2)                 // defaulticon 2
//                .hideThreadInfo()               // defaulticon shown
                .logLevel(LogLevel.FULL)        // defaulticon LogLevel.FULL
                .methodOffset(0);
    }
}
