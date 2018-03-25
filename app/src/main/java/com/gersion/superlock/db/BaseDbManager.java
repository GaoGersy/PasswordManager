package com.gersion.superlock.db;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDbManager<T> {
    private List<OnDataChangeCallback<T>> mOnDataChangeCallbacks;

    public BaseDbManager() {
        mOnDataChangeCallbacks = new ArrayList<>();
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
        Query<T> query = getQueryById(id);
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
        T unique = getQueryById(id).unique();
        onDataDelete(unique);
    }


    public void swap(long oldIndex, long newIndex) {
        Query<T> oldQuery = getQueryById(oldIndex);
        Query<T> newQuery = getQueryById(newIndex);
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
    }

    protected abstract AbstractDao getDao();

    protected abstract Query<T> getQueryById(long id);

    protected abstract T changeId(T oldResult,long id);

    protected abstract Query<T> getQuery();

}