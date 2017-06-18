  
package com.gersion.superlock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/** 
 * ClassName:SpfUtils <br/> 
 * Function: SharedPrefence 工具类 <br/> 
 * Date:     2016年7月11日 下午12:32:29 <br/> 
 * @author   Ger 
 * @version       
 */
public class SpfUtils {


    /**
     * DESC :设置默认值为false. <br/> 
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context,String key){
        return getBoolean(context, key,false);
    }
    /**
     * DESC :读取设置项的布尔值  . <br/> 
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context,String key,boolean defaultValue){
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        return spf.getBoolean(key, defaultValue);
    }
    /**
     * DESC :读取设置项的布尔值  . <br/> 
     * @param context
     * @param key
     * @return
     */
    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        Editor edit = spf.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }
    
    
    
    /**
     * DESC :返回默认值为null . <br/> 
     * @param context
     * @param key
     * @return
     */  
    public static String getString(Context context,String key){
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        return getString(context,key, null);
    }
    /**
     * DESC :读取设置项的 String . <br/> 
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context,String key,String defaultValue){
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        return spf.getString(key, defaultValue);
    }
    
    /**
     * DESC :保存一个String到配置文件  . <br/> 
     * @param context
     * @param key
     * @return
     */
    public static void putString(Context context,String key,String Value){
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        Editor edit = spf.edit();
        edit.putString(key, Value);
        edit.commit();
    }
    
    /**
     * DESC : 从配置文件读取int值  . <br/> 
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context,String key) {
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        return spf.getInt(key, 0);
    }
    
    /**
     * DESC : 从配置文件读取int值  . <br/> 
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context,String key,int value) {
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        return spf.getInt(key, value);
    }
    
    /**
     * DESC :保存一个int到配置文件  . <br/> 
     * @param context
     * @param key
     * @param value
     */
    public static void putInt(Context context,String key, int value){
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        Editor edit = spf.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static long getLong(Context context, String key, int value) {
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        return spf.getLong(key, value);
    }

    public static void putLong(Context context,String key, long value){
        SharedPreferences spf = context.getSharedPreferences(ConstantsUtils.SP_FILE, Context.MODE_PRIVATE);
        Editor edit = spf.edit();
        edit.putLong(key, value);
        edit.commit();
    }
    
}
  