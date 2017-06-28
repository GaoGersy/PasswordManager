package com.gersion.floattools;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.gersion.floattools.view.CoverView;
import com.gersion.floattools.view.FloatBallView;

/**
 * Created by wangxiandeng on 2016/11/25.
 */

public class FloatWindowManager {
    private static FloatBallView mBallView;

    private static WindowManager mWindowManager;
    private static CoverView mCoverView;


    public static void addBallView(Context context) {
        if (mBallView == null) {
            WindowManager windowManager = getWindowManager(context);
            int screenWidth = windowManager.getDefaultDisplay().getWidth();
            int screenHeight = windowManager.getDefaultDisplay().getHeight();
            mBallView = new FloatBallView(context);
            LayoutParams params = new LayoutParams();
            params.x = screenWidth;
            params.y = screenHeight / 2;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.type = LayoutParams.TYPE_PHONE;
            params.format = PixelFormat.RGBA_8888;
            params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | LayoutParams.FLAG_NOT_FOCUSABLE;
            mBallView.setLayoutParams(params);
            windowManager.addView(mBallView, params);
        }
    }

    public static void addCoverView(Context context) {
        if (mCoverView == null) {
            Log.d("aa","mCoverView");
            WindowManager windowManager = getWindowManager(context);
            int screenWidth = windowManager.getDefaultDisplay().getWidth();
            int screenHeight = windowManager.getDefaultDisplay().getHeight();
            mCoverView = new CoverView(context);
            LayoutParams params = new LayoutParams();
            params.x = screenWidth;
            params.y = screenHeight / 2;
            params.width = LayoutParams.MATCH_PARENT;
            params.height = LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.type = LayoutParams.TYPE_PHONE;
            params.format = PixelFormat.RGBA_8888;
            params.flags = LayoutParams.FLAG_NOT_TOUCHABLE| LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    | LayoutParams.FLAG_NOT_FOCUSABLE| LayoutParams.FLAG_FULLSCREEN;
            mCoverView.setLayoutParams(params);
            windowManager.addView(mCoverView,params);
        }
    }

    public static void removeBallView(Context context) {
        if (mBallView != null&&mCoverView!=null) {
            WindowManager windowManager =  getWindowManager(context);
            windowManager.removeView(mBallView);
            windowManager.removeView(mCoverView);
            mBallView = null;
            mCoverView = null;
        }
    }

    public static void removeCoverView(Context context) {
        if (mCoverView!=null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mCoverView);
            mCoverView = null;
        }
    }

    public static CoverView getCoverView(){
        return mCoverView;
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

}
