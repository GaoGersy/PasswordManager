package com.gersion.superlock.utils;

import com.gersion.superlock.listener.ResultCallback;

/**
 * Created by aa326 on 2018/1/28.
 */

public class RecoveryHelper {
    private static RecoveryHelper INSTANCE = new RecoveryHelper();
    private ResultCallback mCallback;

    private RecoveryHelper(){}

    public static RecoveryHelper getInstance(){
        return INSTANCE;
    }

    public void getDataFromBackup() {
        if (mCallback==null){
            throw new RuntimeException("ResultCallback 不能为空");
        }
        byte[] bytes = SDCardUtils.readFileFromSDCard(MyConstants.BACKUP_PATH, MyConstants.BACKUP_FILE_NAME + "." + MyConstants.FILE_TYPE);
        if (bytes==null){
            mCallback.onResultFailed("还没有任何备份数据");
            return;
        }
        String s = new String(bytes);
        String decrypt = Aes.decryptWithSuperPassword(s);
        mCallback.onResultSuccess(decrypt);
    }

    public void setOnResultCallback(ResultCallback callback){
        mCallback = callback;
    }
}
