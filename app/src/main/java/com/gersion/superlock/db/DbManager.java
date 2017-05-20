package com.gersion.superlock.db;

import android.content.Context;

import com.gersion.superlock.bean.PasswordBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public final class DbManager {
    private Realm mRealm;

    private DbManager() {
    }

    public static DbManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * @param appContext
     * @param dbVersion
     */
    public void init(Context appContext, long dbVersion) {
        byte[] key = new byte[64];
//        new SecureRandom().nextBytes(key);
        key[10] =100;
        key[13] =99;
        key[45] =10;
        key[12] =88;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("app.realm")
                .encryptionKey(key)
                .schemaVersion(dbVersion)
                .deleteRealmIfMigrationNeeded()
                .build();

        mRealm = Realm.getInstance(realmConfiguration);
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

    /**
     * @param bean 要添加的对象
     * @return 返回realm中的对象
     */
    public PasswordBean add(final PasswordBean bean) {
        mRealm.executeTransaction(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //必须设置新的PrimaryKey
                        bean.setId(generateNewPrimaryKey());
                        realm.insert(bean);
                    }
                });

        return bean;
    }

    //获取最大的PrimaryKey并加一
    private long generateNewPrimaryKey() {
        long primaryKey = 0;
        //必须排序, 否则last可能不是PrimaryKey最大的数据. findAll()查询出来的数据是乱序的
        RealmResults<PasswordBean> results = mRealm.where(PasswordBean.class).findAllSorted("id", Sort.ASCENDING);
        if (results != null && results.size() > 0) {
            PasswordBean last = results.last(); //根据id顺序排序后, last()取得的对象就是PrimaryKey的值最大的数据
            primaryKey = last.getId() + 1;
        }
        return primaryKey;
    }

    /**
     * @param movie movie的id, 必须有realm中的对象的id (@PrimaryKey标志的字段)
     * @return 返回更改后的realm中的对象
     */
    public PasswordBean update(PasswordBean movie) {
        mRealm.beginTransaction();
        //如果RealmObject对象没有primaryKey, 会报错: java.lang.IllegalArgumentException: A RealmObject with no @PrimaryKey cannot be updated: class com.stone.hostproject.db.model.PasswordBean
        PasswordBean dbPasswordBean = mRealm.copyToRealmOrUpdate(movie);
        mRealm.commitTransaction();

        return dbPasswordBean;
    }

    public PasswordBean updateById(long id, PasswordBean newPasswordBean) {
        //复制到realm中
        mRealm.beginTransaction();
        PasswordBean dbPasswordBean = mRealm.where(PasswordBean.class)
                .equalTo("id", id)
                .findFirst();
        dbPasswordBean.copyParams(newPasswordBean);
        mRealm.copyToRealmOrUpdate(dbPasswordBean);
        mRealm.commitTransaction();

        return dbPasswordBean;
    }

    public void delete(PasswordBean movie) {
        if (movie.getId() < 0) {
            throw new IllegalArgumentException("非法参数: PasswordBean的id不正确");
        }

        //managed, 直接删除
        if (movie.isValid()) {
            mRealm.beginTransaction();
            movie.deleteFromRealm();
            mRealm.commitTransaction();
            return;
        }

        //unmanaged: 先查询, 再删除
        deleteById(movie.getId());
    }

    public void deleteById(long id) {
        //查询
        PasswordBean dbPasswordBean = mRealm.where(PasswordBean.class)
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
        PasswordBean oldPasswordBean = mRealm.where(PasswordBean.class)
                .equalTo("index", oldIndex)
                .findFirst();
        PasswordBean newPasswordBean = mRealm.where(PasswordBean.class)
                .equalTo("index", newIndex)
                .findFirst();
        oldPasswordBean.setIndex(newIndex);
        newPasswordBean.setIndex(oldIndex);
        mRealm.copyToRealmOrUpdate(oldPasswordBean);
        mRealm.copyToRealmOrUpdate(newPasswordBean);
        mRealm.commitTransaction();
    }

    public List<PasswordBean> load() {
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
        return mRealm.where(PasswordBean.class).findAllSorted("index", Sort.ASCENDING);
    }

    private static class InstanceHolder {
        private static final DbManager INSTANCE = new DbManager();
    }
}