package com.gersion.superlock.utils;

import com.gersion.superlock.listener.ResultCallback;

/**
 * Created by aa326 on 2018/1/28.
 */

public class BackupHelper {

    private static BackupHelper INSTANCE = new BackupHelper();
    private ResultCallback mCallback;

    private BackupHelper(){}

    public static BackupHelper getInstance(){
        return INSTANCE;
    }

    private boolean checkDataJson(String dataJson) {
        if (mCallback==null){
            throw new RuntimeException("ResultCallback 不能为空");
        }
        if (dataJson==null||dataJson==""){
            mCallback.onResultFailed("还没有任何密码数据,不需要备份");
            return true;
        }
        return false;
    }

    public void backup2Local(String dataJson) {
        if(checkDataJson(dataJson)){
            return;
        }
        String encryptResult = Aes.encryptWithSuperPassword(dataJson);
        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
        if (!sdCardEnable) {
            mCallback.onResultFailed("没有SD卡，备份工作无法继续进行");
            return ;
        }

//        List<String> sdCardPaths = SDCardUtilss.getSDCardPaths(SuperLockApplication.mContext);
        try {
            boolean b = SDCardUtils.saveFileToSDCard(MyConstants.BACKUP_PATH, MyConstants.BACKUP_FILE_NAME + "."+ MyConstants.FILE_TYPE, encryptResult);
            if (b){
                mCallback.onResultSuccess("备份成功");
            }else {
                mCallback.onResultFailed("备份失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mCallback.onResultFailed("备份出现异常");
        }
    }

    public void backup2Email(String dataJson) {
        String backupEmailAddress = ConfigManager.getInstance().getBackupEmailAddress();
        if (backupEmailAddress==null){
            mCallback.onResultSuccess("请先设置要备份到的邮箱地址");
            return;
        }
        String title = "";
        String body = "";
        String path = "";
        sendMail(backupEmailAddress,title,body,path);
    }

    public void sendMail(final String toMail, final String title,
                         final String body, final String filePath){
        new Thread(new Runnable() {
            public void run() {
                EmailUtil emailUtil = new EmailUtil();
                try {

                    String account = "cmmailserver@canmou123.com";
                    String password = "CANmou123";
                    // String authorizedPwd = "vxoxkgtwrtxvoqz";
                    emailUtil.sendMail(toMail, account, "smtp.mxhichina.com",
                            account, password, title, body, filePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setOnResultCallback(ResultCallback callback){
        mCallback = callback;
    }
}
