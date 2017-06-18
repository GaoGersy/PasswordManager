package com.gersion.superlock.utils;

import com.gersion.superlock.activity.SuperLockApplication;

/**
 * Created by a3266 on 2017/6/16.
 */

public class SPManager {
//    private Context mContext;
//    private static SPManager singleInstance;
//
//    private SPManager(Context context) {
//        mContext = context;
//    }
//
//    public static SPManager getInstance() {
//        if (singleInstance ==null){
//            synchronized (SPManager.class){
//                if (singleInstance==null){
//                    singleInstance = new SPManager(SuperLockApplication.getContext());
//                }
//            }
//        }
//        return singleInstance;
//    }

    /*
    * ~~ 时间：2017/6/16 21:35 ~~
    * 获取指纹解锁需要的总时间
    **/
    public static int getRemainTime(){
        return SpfUtils.getInt(SuperLockApplication.getContext(),"totalTime",0);
    }

    public static void setRemainTime(int time){
        SpfUtils.putInt(SuperLockApplication.getContext(),"totalTime",time);
    }

    /*
    * ~~ 时间：2017/6/16 21:35 ~~
    * 获取指纹锁定的时间
    **/
    public static long getLockedTime(){
        return SpfUtils.getLong(SuperLockApplication.getContext(),"lockedTime",0);
    }

    public static void setLockedTime(long time){
        SpfUtils.putLong(SuperLockApplication.getContext(),"lockedTime",time);
    }
}
