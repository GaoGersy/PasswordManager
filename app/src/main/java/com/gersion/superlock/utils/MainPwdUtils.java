//package com.gersion.superlock.utils;
//
//import java.io.File;
//
//import com.gersion.superlock.dao.MainKeyDao;
//import com.gersion.superlock.dao.SuperKeyDao;
//
//import android.content.Context;
//
///**
// * Created by Gers on 2016/8/5.
// */
//
//public class MainPwdUtils {
//    /**
//     * 密存储了其中的一个数据
//     *
//     * @param piapi
//     *            待解密的数据
//     * @param masterPassword
//     *            密钥
//     * @return
//     */
//    public static String decryptData(String piapi, String masterPassword) {
//        try {
//            String decryptingCode = AESUtils.decrypt(masterPassword, piapi);
//            return decryptingCode;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 加密其中的一个数据
//     *
//     * @param piapi
//     *            待加密的数据
//     * @param masterPassword
//     *            密钥
//     * @return
//     */
//    public static String encryptData(String piapi, String masterPassword) {
//        try {
//            String encryptingCode = AESUtils.encrypt(masterPassword, piapi);
//            return encryptingCode;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static boolean isExist(Context context, String name) {
//        String path = context.getFilesDir().getPath();
//        File file = new File(path.substring(0, path.lastIndexOf("/")) + "/shared_prefs/" + name + ".xml");
//        if (file.exists()) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 存储主密码
//     * @param context
//     * @param pwd
//     * @return
//     */
//    public static boolean saveMainkey(Context context, String pwd) {
//        String value = encryptData(pwd, pwd);
//        String key = encryptData(pwd, value);
//        LogUtils.e("pwd="+pwd);
//        boolean saveConfig = Utils.saveConfig(context, "checksConfig", "news", key);
//        Utils.saveConfig(context, "checksConfig", "olds", value);
//        return saveConfig;
//    }
//
//    /**
//     * 存储用户数据的加密密钥
//     * @param context
//     * @return
//     */
//    public static boolean saveSuperKey(Context context){
//        String pwd = PasswordUtils.getNewPassword(true, true, true, true, 30);
//        String value = encryptData(pwd , pwd);
//        String key = encryptData(pwd, value);
//        boolean saveConfig = Utils.saveConfig(context, "giftConfig", "news", key);
//        Utils.saveConfig(context, "giftConfig", "olds", value);
//        return saveConfig;
//    }
//
//    /**
//     * 存储用户数据的加密密钥
//     * @param context
//     * @return
//     */
//    public static String getSuperKey(Context context) {
//        String key = Utils.getConfig(context, "giftConfig", "news", true);
//        String value = Utils.getConfig(context, "giftConfig", "olds", true);
//        String decryptData = decryptData(key, value);
//        return decryptData;
//    }
//
//    /**
//     * 获取主密码
//     * @param context
//     * @return
//     */
//    public static String getMainKey(Context context) {
//        String key = Utils.getConfig(context, "checksConfig", "news", true);
//        String value = Utils.getConfig(context, "checksConfig", "olds", true);
//        String decryptData = decryptData(key, value);
//        return decryptData;
//    }
//
//    /**
//     * 修改主密码
//     *
//     * @param context
//     */
//    public static boolean changMainPwd(Context context, String pwd) {
//        String mainKey = getMainKey(context);
//        SuperKeyDao skd = new SuperKeyDao(context);
//        String query = skd.query();
//        boolean saveMainkey = saveMainkey(context, pwd);
//        if (saveMainkey) {
//            MainKeyDao mkd = new MainKeyDao(context);
//            boolean add = mkd.add(pwd);
//            if (add) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 初始化主密码
//     *
//     * @param context
//     */
//    public static boolean setMainPwd(Context context, String pwd) {
//        boolean saveMainkey = saveMainkey(context, pwd);
//        boolean saveSuperKey = saveSuperKey(context);
//        if (saveMainkey) {
//            MainKeyDao mkd = new MainKeyDao(context);
//            boolean add = mkd.add(pwd);
//            if (add) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//}
