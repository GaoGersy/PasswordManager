package com.gersion.superlock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gersion.superlock.bean.DaoMaster;
import com.gersion.superlock.bean.PasswordDataDao;

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper  {
    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据迁移模块
        MigrationHelper.migrate(db,PasswordDataDao.class);
    }
}