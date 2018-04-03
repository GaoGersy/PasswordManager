package com.gersion.superlock.db;

import android.content.Context;

import com.gersion.superlock.bean.DaoMaster;
import com.gersion.superlock.bean.DaoSession;
import com.gersion.superlock.bean.PasswordData;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDbManager<T> {
    private List<OnDataChangeCallback<T>> mOnDataChangeCallbacks;

    public BaseDbManager() {
        mOnDataChangeCallbacks = new ArrayList<>();
    }

    public void switchDb(Context context, String dbName) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), dbName);
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        swithDaoSession(daoSession);
        onDataChange();
    }

    public DaoSession upgradeDb(Context context, String dbName) {
        MySQLiteOpenHelper devOpenHelper = new
                MySQLiteOpenHelper(context, dbName, null);
        Database writableDb = devOpenHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(writableDb);
        devOpenHelper.onUpgrade(writableDb, 1, 2);
        DaoSession daoSession = daoMaster.newSession();
        swithDaoSession(daoSession);
        onDataChange();
        return daoSession;
    }

    public void onDataChange() {
        for (OnDataChangeCallback onDataChangeListener : mOnDataChangeCallbacks) {
            if (onDataChangeListener != null) {
                List<T> list = queryAll();
                onDataChangeListener.onDataChange(list);
            }
        }
    }

    public void addOrReplace(T bean) {
        long result = getDao().insertOrReplace(bean);
        if (result>0){
            if (bean instanceof PasswordData) {
                PasswordData passwordData = (PasswordData) queryById(result);
                passwordData.setIndex(result);
                getDao().update(passwordData);
            }
        }
    }

    public long add(T bean) {
        long result = getDao().insert(bean);
        if (result>0){
            if (bean instanceof PasswordData) {
                PasswordData passwordData = (PasswordData) queryById(result);
                passwordData.setIndex(result);
                getDao().update(passwordData);
                onDataChange();
            }
        }
        return result;
    }

    public boolean update(T bean) {
        try {
            getDao().update(bean);
            onDataChange();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public List<T> queryAll() {
        Query<T> query = getQuery();
        return query.list();
    }

    public T queryById(long id) {
        Query<T> query = getQueryById(id);
        return query.unique();
    }

    public T queryByKey(Object key) {
        Query<T> query = getQueryByKey(key);
        return query.unique();
    }

    public void delete(T bean) {
        getDao().delete(bean);
        onDataChange();
    }

    public void deleteAll() {
        getDao().deleteAll();
        onDataChange();
    }

    public void deleteById(long id) {
        getDao().deleteByKey(id);
        onDataChange();
    }


    public void swap(long oldIndex, long newIndex) {
        Query<T> oldQuery = getQueryByKey(oldIndex);
        Query<T> newQuery = getQueryByKey(newIndex);
        T oldResult = oldQuery.unique();
        T newResult = newQuery.unique();
        T t1 = changeIndex(newResult, oldIndex);
        T t2 = changeIndex(oldResult, newIndex);
        getDao().update(t1);
        getDao().update(t2);
    }

    public void registerDataChangeListener(OnDataChangeCallback<T> listener) {
        mOnDataChangeCallbacks.add(listener);
    }

    public void unregisterDataChangeListener(OnDataChangeCallback<T> listener) {
        mOnDataChangeCallbacks.remove(listener);
    }

    public interface OnDataChangeCallback<T> {

        void onDataChange(List<T> list);
    }

    protected abstract void swithDaoSession(DaoSession daoSession);

    protected abstract AbstractDao getDao();

    protected abstract Query<T> getQueryByKey(Object key);

    protected abstract T changeIndex(T oldResult, Object key);

    protected abstract Query<T> getQuery();

    protected abstract Query<T> getQueryById(long id);
}