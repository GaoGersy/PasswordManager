package com.gersion.superlock.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gersion.superlock.db.MainSqlOpenHelper;
import com.gersion.superlock.utils.Md5Utils;
import com.gersion.superlock.utils.MyConstants;

public class MainKeyDao {
    private Context context;
    private MainSqlOpenHelper helper;
    private String mSalt;

    public MainKeyDao(Context context) {
        this.context = context;
        helper = new MainSqlOpenHelper(context);
        mSalt = MyConstants.ADD_SALT;
    }

    public int getCount() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("tata", null, null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public boolean add(String piapi) {
        del();

        String pwd = mSalt + piapi + mSalt;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String encodeTimes = Md5Utils.encodeTimes(pwd);
        values.put("piapi", encodeTimes);
        long rowId = db.insert("tata", null, values);
        db.close();
        return rowId == -1 ? false : true;
    }

    public boolean del() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = db.delete("tata", null, null);
        db.close();
        return delete > 0 ? true : false;
    }

    public boolean update(String piapi) {
        String pwd = mSalt + piapi + mSalt;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String encodeTimes = Md5Utils.encodeTimes(pwd);
        values.put("piapi", encodeTimes);
        int update = db.update("tata", values, "piapi=?", new String[]{encodeTimes});
        db.close();
        return update > 0 ? true : false;
    }

    public String query() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor query = db.query("tata", new String[]{"piapi"}, null, null, null, null, null);
        String piapi = null;
        if (query.moveToFirst()) {
            piapi = query.getString(0);
        }
        query.close();
        db.close();
        return piapi;
    }
}
