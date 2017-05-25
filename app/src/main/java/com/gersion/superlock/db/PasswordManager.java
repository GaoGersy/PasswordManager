package com.gersion.superlock.db;

import android.content.Context;
import android.text.TextUtils;

import com.gersion.superlock.bean.PasswordBean;
import com.gersion.superlock.utils.Aes;
import com.gersion.superlock.utils.Md5Utils;
import com.gersion.superlock.utils.SpfUtils;
import com.gersion.superlock.utils.TimeUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public final class PasswordManager {
    public static String mCreateDate;
    public static String mPasswordKey;
    private Realm mRealm;

    private PasswordManager() {
    }

    public static PasswordManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Realm getRealm() {
        return mRealm;
    }

    public String getEncyptPassword(String password){
        return Aes.encrypt(password,mPasswordKey);
    }
    /**
     * @param appContext
     * @param dbVersion
     */
    public void init(Context appContext, long dbVersion) {
        byte[] key = getKey(appContext);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("password.realm")
                .encryptionKey(key)
                .schemaVersion(dbVersion)
                .deleteRealmIfMigrationNeeded()
                .build();

        mRealm = Realm.getInstance(realmConfiguration);
    }

    /*
    * ~~ 时间：2017/5/25 21:30 ~~
    * 生成加密Key
    **/
    private byte[] getKey(Context appContext) {
        byte[] key = new byte[64];
        mCreateDate = SpfUtils.getString(appContext, "createDate");
        if (TextUtils.isEmpty(mCreateDate)) {
            mCreateDate = TimeUtils.getCurrentTimeInString();
            SpfUtils.putString(appContext, "createDate", mCreateDate);
        }
        mPasswordKey = Md5Utils.encodeWithTimes(mCreateDate, 66);
        String password = Md5Utils.encodeWithTimes(mCreateDate, 88);
        char[] chars = password.toCharArray();
        int length = chars.length;
        for (int i = length - 1; i > 0; i--) {
            key[i] = (byte) chars[i];
        }
        return key;
    }

    /**
     * 退出应用的时候调用
     */
    public void destroy() {
        if (mRealm != null) {
            if (!mRealm.isClosed()) {
                mRealm.close();
            }
            mRealm = null;
        }
    }

    public PasswordBean addPassword(String password) {
        PasswordBean bean = new PasswordBean();
        bean.setPassword(password);
        return addPassword(bean, 1);
    }

    /**
     * @param bean 要添加的对象
     * @return 返回realm中的对象
     */
    public PasswordBean addPassword(final PasswordBean bean, final long id) {
        mRealm.executeTransaction(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        bean.setId(id);
                        realm.insert(bean);
                    }
                });

        return bean;
    }

    public PasswordBean updatePassword(String password) {
        mRealm.beginTransaction();
        PasswordBean passwordBean = load();
        passwordBean.setPassword(password);
        PasswordBean dbPasswordBean = mRealm.copyToRealmOrUpdate(passwordBean);
        mRealm.commitTransaction();

        return dbPasswordBean;
    }

    public String getPassword(){
        return load().getPassword();
    }

    private PasswordBean load() {
        return mRealm.where(PasswordBean.class).findFirst();
    }

    public void delete(PasswordBean bean) {
        if (bean.getId() < 0) {
            throw new IllegalArgumentException("非法参数: PasswordBean的id不正确");
        }

        //managed, 直接删除
        if (bean.isValid()) {
            mRealm.beginTransaction();
            bean.deleteFromRealm();
            mRealm.commitTransaction();
            return;
        }

        //unmanaged: 先查询, 再删除
        deleteById(bean.getId());
    }

    public void deleteById(long id) {
        PasswordBean dbPasswordBean = mRealm.where(PasswordBean.class)
                .equalTo("id", id)
                .findFirst();

        mRealm.beginTransaction();
        dbPasswordBean.deleteFromRealm();
        mRealm.commitTransaction();
    }

    public interface OnUpdateCallback {
        PasswordBean onUpdate();
    }

    private static class InstanceHolder {
        private static final PasswordManager INSTANCE = new PasswordManager();
    }
}