package com.gersion.superlock.service;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.view.FloatBallView;
import com.gersion.superlock.view.SelectListView;
import com.orhanobut.logger.Logger;

/**
 * Created by wangxiandeng on 2016/11/25.
 */

public class FloatWindowManager {
    private static FloatBallView mBallView;

    private static WindowManager mWindowManager;
    private static SelectListView mSelectListView;
    private static LayoutParams mSelectListViewParams;
    private static LayoutParams mFloatBallparams;
    private static boolean mIsAddSelectListView;


    public static void addBallView(Context context) {
        if (mBallView == null) {
            WindowManager windowManager = getWindowManager(context);
            int screenWidth = windowManager.getDefaultDisplay().getWidth();
            int screenHeight = windowManager.getDefaultDisplay().getHeight();
            initFloatBallParams(context, screenWidth, screenHeight);
            mBallView.setLayoutParams(mFloatBallparams);
            windowManager.addView(mBallView, mFloatBallparams);
        }
    }

    private static void initFloatBallParams(Context context, int screenWidth, int screenHeight) {
        mBallView = new FloatBallView(context);
//        mFloatBallparams = new LayoutParams();
        int flags = 0;
        int type = 0;
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h = WindowManager.LayoutParams.MATCH_PARENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //解决Android 7.1.1起不能再用Toast的问题（先解决crash）
            if (Build.VERSION.SDK_INT > 24) {
                type = WindowManager.LayoutParams.TYPE_PHONE;
            } else {
                type = WindowManager.LayoutParams.TYPE_TOAST;
            }
        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        mFloatBallparams = new WindowManager.LayoutParams(w, h, type, flags, PixelFormat.TRANSLUCENT);
        mFloatBallparams.x = screenWidth;
        mFloatBallparams.y = screenHeight / 3 * 2;
        mFloatBallparams.width = LayoutParams.WRAP_CONTENT;
        mFloatBallparams.height = LayoutParams.WRAP_CONTENT;
        mFloatBallparams.gravity = Gravity.LEFT | Gravity.TOP;
        mFloatBallparams.type = LayoutParams.TYPE_PHONE;
        mFloatBallparams.format = PixelFormat.RGBA_8888;
        mFloatBallparams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_NOT_FOCUSABLE;


    }

    public static void addSelectListView(Context context) {
        if (mSelectListView == null) {
            mSelectListView = new SelectListView(context);

            mSelectListView.setOnItemSelectedListener(new SelectListView.OnItemSelectedListener() {
                @Override
                public void onItemSeleted(DbBean bean) {
                    mBallView.setPwdData(bean);
                }
            });

            initSelectListViewParams();
            mSelectListView.setLayoutParams(mSelectListViewParams);
            getWindowManager(context).addView(mSelectListView, mSelectListViewParams);
            mIsAddSelectListView = true;
        }
    }

    private static void initSelectListViewParams() {
//        mSelectListViewParams = new LayoutParams();
//            params.x = screenWidth;
//            params.y = screenHeight / 2;
        int flags = 0;
        int type = 0;
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h = WindowManager.LayoutParams.MATCH_PARENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //解决Android 7.1.1起不能再用Toast的问题（先解决crash）
            if (Build.VERSION.SDK_INT > 24) {
                type = WindowManager.LayoutParams.TYPE_PHONE;
            } else {
                type = WindowManager.LayoutParams.TYPE_TOAST;
            }
        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        mSelectListViewParams = new WindowManager.LayoutParams(w, h, type, flags, PixelFormat.TRANSLUCENT);
        mSelectListViewParams.width = LayoutParams.WRAP_CONTENT;
        mSelectListViewParams.height = LayoutParams.WRAP_CONTENT;
        mSelectListViewParams.gravity = Gravity.CENTER | Gravity.TOP;
        mSelectListViewParams.type = LayoutParams.TYPE_PHONE;
        mSelectListViewParams.format = PixelFormat.RGBA_8888;
        mSelectListViewParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
    }

    public static void removeBallView(Context context) {
        if (mBallView != null && mBallView.getParent() != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeViewImmediate(mBallView);
        }
        removeSelectListView(context);
    }

    public static void removeSelectListView(Context context) {
        if (mSelectListView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mSelectListView);
//            mSelectListView.removed();
            mSelectListView.setOnItemSelectedListener(null);
            mIsAddSelectListView = false;
            mSelectListView=null;
        }
    }

    public static void changeSelectListViewStatus(Context context) {
        if (mIsAddSelectListView) {
            removeSelectListView(context);
        } else {
            addSelectListView(context);
        }
    }

    public static SelectListView getSelectListView() {
        return mSelectListView;
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

}
