package com.gersion.superlock.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gers on 2016/8/7.
 */

public class Utils {
    // 保存设置
    public static boolean saveConfig(Context context, String fileName, String key, boolean value) {
        try {
            SharedPreferences spf = context.getSharedPreferences(fileName, context.MODE_APPEND);
            SharedPreferences.Editor edit = spf.edit();
            edit.putBoolean(key, value);
            edit.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean getConfig(Context context, String fileName, String key) {
        try {
            SharedPreferences spf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
            boolean value = spf.getBoolean(key, false);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 保存设置
    public static boolean saveConfig(Context context, String fileName, String key, String value) {
        try {
            SharedPreferences spf = context.getSharedPreferences(fileName, context.MODE_APPEND);
            SharedPreferences.Editor edit = spf.edit();
            edit.putString(key, value);
            edit.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getConfig(Context context, String fileName, String key, boolean b) {
        try {
            SharedPreferences spf = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
            String value = spf.getString(key, null);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
