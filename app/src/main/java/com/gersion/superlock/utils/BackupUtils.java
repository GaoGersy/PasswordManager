package com.gersion.superlock.utils;

import android.app.Activity;

import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;

/**
 * Created by gersy on 2017/7/10.
 */

public class BackupUtils {

    //备份数据库文件
    public static void backupDB(Activity activity){
        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
        if (!sdCardEnable) {
            ToastUtils.showTasty(activity, "没有SD卡，备份工作无法继续进行", TastyToast.WARNING);
            return ;
        }

        ConfigManager instance = ConfigManager.getInstance();
        File sqlPath = instance.getDestDbFile();
        if (!sqlPath.exists()) {
            ToastUtils.showTasty(activity, "还没有任何数据，不需要备份", TastyToast.WARNING);
            return ;
        }

        if (SDCardUtils.getSDCardAllSize() < sqlPath.length()) {
            ToastUtils.showTasty(activity, "SD卡剩余容量不足，无法备份", TastyToast.WARNING);
            return;
        }

        FileUtils.copyFile(instance.getSrcDbFile(),instance.getDestDbFile());
    }

    //恢复数据库文件
    public static void recoveryDB(){

    }
}
