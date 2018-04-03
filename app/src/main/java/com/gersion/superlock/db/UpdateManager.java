package com.gersion.superlock.db;

import com.gersion.superlock.app.SuperLockApplication;
import com.gersion.superlock.bean.DaoSession;
import com.gersion.superlock.bean.UpdateData;
import com.gersion.superlock.bean.UpdateDataDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;

public final class UpdateManager extends BaseDbManager<UpdateData>{

    private static UpdateManager INSTANCE = new UpdateManager();

    public static UpdateManager getInstance(){
        return INSTANCE;
    }

    @Override
    protected void swithDaoSession(DaoSession daoSession) {
        SuperLockApplication.getInstance().setDaoSession(daoSession);
    }

    @Override
    protected AbstractDao getDao() {
        DaoSession daoSession = SuperLockApplication.getInstance().getDaoSession();
        UpdateDataDao updateDataDao = daoSession.getUpdateDataDao();
        return updateDataDao;
    }

    @Override
    protected Query<UpdateData> getQueryByKey(Object key) {
        Query<UpdateData> query = getDao().queryBuilder().where(UpdateDataDao.Properties.Index.eq(key)).build();
        return query;
    }

    @Override
    protected UpdateData changeIndex(UpdateData oldResult, Object key) {
        oldResult.setIndex((Long) key);
        return oldResult;
    }

    @Override
    protected Query<UpdateData> getQuery() {
        Query<UpdateData> query = getDao().queryBuilder().orderDesc(UpdateDataDao.Properties.Index).build();
        return query;
    }

    @Override
    protected Query<UpdateData> getQueryById(long id) {
        Query<UpdateData> query = getDao().queryBuilder().where(UpdateDataDao.Properties.Id.eq(id)).build();
        return query;
    }
}