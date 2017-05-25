package com.gersion.superlock.db;

import android.content.Context;
import android.text.TextUtils;

import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.utils.Md5Utils;
import com.gersion.superlock.utils.SpfUtils;
import com.gersion.superlock.utils.TimeUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public final class DbManager {
    private Realm mRealm;
    private RealmConfiguration mRealmConfiguration;

    private DbManager() {
    }

    public static DbManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public void onStart(){
        mRealm = Realm.getInstance(mRealmConfiguration);
    }
    public Realm getRealm(){
        return mRealm;
    }

    /**
     * @param appContext
     * @param dbVersion
     */
    public void init(Context appContext, long dbVersion) {
        byte[] key = getKey(appContext);
        mRealmConfiguration = new RealmConfiguration.Builder()
                .name("Lock.realm")
                .encryptionKey(key)
                .schemaVersion(dbVersion)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    /*
    * ~~ 时间：2017/5/25 21:30 ~~
    * 生成加密Key
    **/
    private byte[] getKey(Context appContext) {
        byte[] key = new byte[64];
        String createDate = PasswordManager.mCreateDate;
        if (TextUtils.isEmpty(createDate)){
            createDate = TimeUtils.getCurrentTimeInString();
            SpfUtils.putString(appContext,"createDate",createDate);
        }
        String password = Md5Utils.encodeTimes(createDate);
        char[] chars = password.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length; i++) {
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
        }
    }

    /**
     * @param bean 要添加的对象
     * @return 返回realm中的对象
     */
    public DbBean add(DbBean bean) {
        return add(bean,generateNewPrimaryKey());
    }

    /**
     * @param bean 要添加的对象
     * @return 返回realm中的对象
     */
    public DbBean add(final DbBean bean, final long id) {
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

    //获取最大的PrimaryKey并加一
    private long generateNewPrimaryKey() {
        long primaryKey = 0;
        //必须排序, 否则last可能不是PrimaryKey最大的数据. findAll()查询出来的数据是乱序的
        RealmResults<DbBean> results = mRealm.where(DbBean.class).findAllSorted("id", Sort.ASCENDING);
        if (results != null && results.size() > 0) {
            DbBean last = results.last(); //根据id顺序排序后, last()取得的对象就是PrimaryKey的值最大的数据
            primaryKey = last.getId() + 1;
        }
        return primaryKey;
    }

    /**
     * @param bean movie的id, 必须有realm中的对象的id (@PrimaryKey标志的字段)
     * @return 返回更改后的realm中的对象
     */
    public DbBean update(DbBean bean) {
        mRealm.beginTransaction();
        //如果RealmObject对象没有primaryKey, 会报错: java.lang.IllegalArgumentException: A RealmObject with no @PrimaryKey cannot be updated: class com.stone.hostproject.db.model.PasswordBean
        DbBean dbPasswordBean = mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();

        return dbPasswordBean;
    }

    public DbBean update(OnUpdateCallback callback){
        mRealm.beginTransaction();
        //如果RealmObject对象没有primaryKey, 会报错: java.lang.IllegalArgumentException: A RealmObject with no @PrimaryKey cannot be updated: class com.stone.hostproject.db.model.PasswordBean
        DbBean dbPasswordBean = mRealm.copyToRealmOrUpdate(callback.onUpdate());
        mRealm.commitTransaction();

        return dbPasswordBean;
    }

    public DbBean updateById(long id, OnUpdateByIdCallback callback) {
        //复制到realm中
        mRealm.beginTransaction();
        DbBean dbPasswordBean = mRealm.where(DbBean.class)
                .equalTo("id", id)
                .findFirst();
        mRealm.copyToRealmOrUpdate(callback.onUpdate(dbPasswordBean));
        mRealm.commitTransaction();

        return dbPasswordBean;
    }

    public DbBean updateById(long id, DbBean newPasswordBean) {
        //复制到realm中
        mRealm.beginTransaction();
        DbBean dbPasswordBean = mRealm.where(DbBean.class)
                .equalTo("id", id)
                .findFirst();
        dbPasswordBean.copyParams(newPasswordBean);
        mRealm.copyToRealmOrUpdate(dbPasswordBean);
        mRealm.commitTransaction();

        return dbPasswordBean;
    }

    public void delete(DbBean bean) {
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
        //查询
        DbBean dbPasswordBean = mRealm.where(DbBean.class)
                .equalTo("id", id)
                .findFirst();

        //删除
        mRealm.beginTransaction();
        dbPasswordBean.deleteFromRealm();
        mRealm.commitTransaction();
    }

    public void swap(int oldIndex,int newIndex){
        Logger.d("swap");
        mRealm.beginTransaction();
        DbBean oldPasswordBean = mRealm.where(DbBean.class)
                .equalTo("index", oldIndex)
                .findFirst();
        DbBean newPasswordBean = mRealm.where(DbBean.class)
                .equalTo("index", newIndex)
                .findFirst();
        oldPasswordBean.setIndex(newIndex);
        newPasswordBean.setIndex(oldIndex);
        mRealm.copyToRealmOrUpdate(oldPasswordBean);
        mRealm.copyToRealmOrUpdate(newPasswordBean);
        mRealm.commitTransaction();
    }

    public List<DbBean> load() {
        //异步查询
//        mRealm.where(PasswordBean.class).
//                findAllAsync()
//                .asObservable()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(results -> {
//                });

        //条件查询
//        RealmResults<PasswordBean> results1 = mRealm.where(PasswordBean.class)
//                .greaterThan("year", 2000)
//                .findAllSorted("year", Sort.DESCENDING); //Sort by year, in descending order

        //同步查询所有数据(根据id倒序排序, 最后添加的在ListView的顶部)
        return mRealm.where(DbBean.class).findAllSorted("index", Sort.ASCENDING);
    }

    private static class InstanceHolder {
        private static final DbManager INSTANCE = new DbManager();
    }

    public interface OnUpdateCallback{
        DbBean onUpdate();
    }

    public interface OnUpdateByIdCallback{
        DbBean onUpdate(DbBean bean);
    }
}