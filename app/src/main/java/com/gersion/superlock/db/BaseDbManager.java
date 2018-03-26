package com.gersion.superlock.db;

import android.content.Context;

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
        onDbSwitch();
    }

    public void onDbSwitch() {
        for (OnDataChangeCallback onDataChangeListener : mOnDataChangeCallbacks) {
            if (onDataChangeListener != null) {
                List<T> list = queryAll();
                onDataChangeListener.onDbSwitch(list);
            }
        }
    }

    public void onDataAdd(T bean) {
        for (OnDataChangeCallback onDataChangeListener : mOnDataChangeCallbacks) {
            if (onDataChangeListener != null) {
                onDataChangeListener.onAdd(bean);
            }
        }
    }

    public void onDataDelete(T bean) {
        for (OnDataChangeCallback onDataChangeListener : mOnDataChangeCallbacks) {
            if (onDataChangeListener != null) {
                onDataChangeListener.onDelete(bean);
            }
        }
    }

    public void onDataDeleteAll() {
        for (OnDataChangeCallback onDataChangeListener : mOnDataChangeCallbacks) {
            if (onDataChangeListener != null) {
                onDataChangeListener.onDeleteAll();
            }
        }
    }

    public void onDataUpdate(T bean) {
        for (OnDataChangeCallback<T> onUpdateCallback : mOnDataChangeCallbacks) {
            if (onUpdateCallback != null) {
                onUpdateCallback.onUpdate(bean);
            }
        }
    }

    public boolean add(T bean) {
        long result = getDao().insert(bean);
        if (result > 0) {
            onDataAdd(bean);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(T bean) {
        try {
            getDao().update(bean);
            onDataUpdate(bean);
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
        Query<T> query = getQueryByKey(id);
        return query.unique();
    }

    public void delete(T bean) {
        getDao().delete(bean);
        onDataDelete(bean);
    }

    public void deleteAll() {
        getDao().deleteAll();
        onDataDeleteAll();
    }

    public void deleteById(long id) {
        getDao().deleteByKey(id);
        T unique = getQueryByKey(id).unique();
        onDataDelete(unique);
    }


    public void swap(long oldIndex, long newIndex) {
        Query<T> oldQuery = getQueryByKey(oldIndex);
        Query<T> newQuery = getQueryByKey(newIndex);
        T oldResult = oldQuery.unique();
        T newResult = newQuery.unique();
        T t1 = changeId(newResult, oldIndex);
        T t2 = changeId(oldResult, newIndex);
        getDao().update(t1);
        getDao().update(t2);
    }

    public List<T> load() {
        Query<T> notesQuery = getDao().queryBuilder().orderAsc(NoteDao.Properties.Text).build();
        return notesQuery.list();
    }

    public void registerDataChangeListener(OnDataChangeCallback<T> listener) {
        mOnDataChangeCallbacks.add(listener);
    }

    public void unregisterDataChangeListener(OnDataChangeCallback<T> listener) {
        mOnDataChangeCallbacks.remove(listener);
    }

    public interface OnDataChangeCallback<T> {
        void onUpdate(T bean);

        void onAdd(T bean);

        void onDelete(T bean);

        void onDeleteAll();

        void onDbSwitch(List<T> list);
    }

    protected abstract void swithDaoSession(DaoSession daoSession);

    protected abstract AbstractDao getDao();

    protected abstract Query<T> getQueryByKey(Object key);

    protected abstract T changeId(T oldResult, Object key);

    protected abstract Query<T> getQuery();

}