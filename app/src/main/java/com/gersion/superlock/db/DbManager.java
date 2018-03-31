package com.gersion.superlock.db;

import com.gersion.superlock.app.SuperLockApplication;
import com.gersion.superlock.bean.DaoSession;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.bean.PasswordDataDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;

public final class DbManager extends BaseDbManager<PasswordData>{

    private static DbManager INSTANCE = new DbManager();

    public static DbManager getInstance(){
        return INSTANCE;
    }

    @Override
    protected void swithDaoSession(DaoSession daoSession) {
        SuperLockApplication.getInstance().setDaoSession(daoSession);
    }

    @Override
    protected AbstractDao getDao() {
        DaoSession daoSession = SuperLockApplication.getInstance().getDaoSession();
        PasswordDataDao passwordDataDao = daoSession.getPasswordDataDao();
        return passwordDataDao;
    }

    @Override
    protected Query<PasswordData> getQueryByKey(Object key) {
        Query<PasswordData> query = getDao().queryBuilder().where(PasswordDataDao.Properties.Index.eq(key)).build();
        return query;
    }

    @Override
    protected PasswordData changeIndex(PasswordData oldResult, Object key) {
        oldResult.setIndex((Long) key);
        return oldResult;
    }

    @Override
    protected Query<PasswordData> getQuery() {
        Query<PasswordData> query = getDao().queryBuilder().orderDesc(PasswordDataDao.Properties.Index).build();
        return query;
    }

    @Override
    protected Query<PasswordData> getQueryById(long id) {
        Query<PasswordData> query = getDao().queryBuilder().where(PasswordDataDao.Properties.Id.eq(id)).build();
        return query;
    }
}