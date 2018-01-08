package com.gersion.superlock.utils;

import android.content.Context;
import android.content.res.Resources;

import com.gersion.superlock.app.SuperLockApplication;


/**
 * 类    名:  UIUtils
 * 创 建 者:  伍碧林
 * 创建时间:  2016/8/20 10:59
 * 描    述：常见的一些和ui操作相关的方法
 */
public class UIUtils {
    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return SuperLockApplication.getContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中定义的字符串信息
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中定义的字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到Color.xml中定义的颜色信息
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * dp-->px
     *
     * @param dp
     * @return
     */
    public static int dp2Px(int dp) {
        //dp和px相互转换的公式
        //公式一:px/dp = density
        //公式二:px/(ppi/160) = dp
        /*
           480x800  ppi=240    1.5
           1280x720 ppi = 320   2
         */
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + .5f);
        return px;
    }

    public static int dp2Px(float dp) {
        //dp和px相互转换的公式
        //公式一:px/dp = density
        //公式二:px/(ppi/160) = dp
        /*
           480x800  ppi=240    1.5
           1280x720 ppi = 320   2
         */
        float density = getResources().getDisplayMetrics().density;
        int px = (int) (dp * density + .5f);
        return px;
    }

    /**
     * px-->do
     *
     * @param px
     * @return
     */
    public static int px2Dp(int px) {
        //dp和px相互转换的公式
        //公式一:px/dp = density
        //公式二:px/(ppi/160) = dp
        /*
           480x800  ppi=240    1.5
           1280x720 ppi = 320   2
         */
        float density = getResources().getDisplayMetrics().density;
        int dp = (int) (px / density + .5f);
        return dp;
    }
}
