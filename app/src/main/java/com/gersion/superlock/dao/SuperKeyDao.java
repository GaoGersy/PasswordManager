package com.gersion.superlock.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gersion.superlock.db.SuperSqlOpenHelper;
import com.gersion.superlock.utils.AESUtils;
import com.gersion.superlock.utils.LogUtils;
import com.gersion.superlock.utils.Md5Utils;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;


public class SuperKeyDao {
    private Context context;
    private SuperSqlOpenHelper helper;
    //将存储的日期作为key加密用户存储的密码
    private String masterPassword ;

    public SuperKeyDao(Context context) {
        this.context = context;
        helper = new SuperSqlOpenHelper(context);
        getSuperKey(context);
    }

    public void getSuperKey(Context context) {
        masterPassword = Md5Utils.encodeTimes(MyConstants.ADD_SALT + SpfUtils.getString(context, MyConstants.FIRST_TIME, "") + MyConstants.ADD_SALT);
        LogUtils.e(masterPassword);
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
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String str = encryptData(piapi);
        LogUtils.e(str);
        values.put("piapi", str);
        long rowId = db.insert("tata", null, values);
        db.close();
        return rowId == -1 ? false : true;
    }

    public boolean update() {
        if (masterPassword == null) {
            return false;
        }
        del();
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("piapi", masterPassword);
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
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String str = encryptData(piapi);
        values.put("piapi", str);
        int update = db.update("tata", values, "piapi=?", new String[]{str});
        db.close();
        return update > 0 ? true : false;
    }

    public String query() {

        if (masterPassword == null) {
            return null;
        }
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor query = db.query("tata", new String[]{"piapi"}, null, null, null, null, null);
        String piapi = null;
        if (query.moveToFirst()) {
            piapi = decryptData(query.getString(0));
        }
        query.close();
        db.close();
        return piapi;

		/*String piapi;
		try {
			SQLiteDatabase db = helper.getReadableDatabase();
			Cursor query = db.query("tata", new String[] { "piapi" }, null, null, null, null, null);
			piapi = null;
			if (query.moveToFirst()) {
				piapi = decryptData(query.getString(0));
			}
			query.close();
			db.close();
			return piapi;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;*/

    }

    // ���ܴ洢�����е�һ������
    public String decryptData(String piapi) {
        try {
            String decryptingCode = AESUtils.decrypt(masterPassword, piapi);
            return decryptingCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // �������е�һ������
    public String encryptData(String piapi) {
        try {
            String encryptingCode = AESUtils.encrypt(masterPassword, piapi);
            return encryptingCode;
        } catch (Exception e) {
            LogUtils.e("不行呼呼");
            e.printStackTrace();
        }
        return null;
    }

}
