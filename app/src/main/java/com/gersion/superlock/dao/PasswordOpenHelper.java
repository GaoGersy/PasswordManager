package com.gersion.superlock.dao;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.dao
 * @待完成
 * @创建时间 2016/9/27
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class PasswordOpenHelper extends SQLiteOpenHelper {

    public PasswordOpenHelper(Context context) {
        super(context, SqlPassword.DB_NAME+".db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SqlPassword.Table.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
