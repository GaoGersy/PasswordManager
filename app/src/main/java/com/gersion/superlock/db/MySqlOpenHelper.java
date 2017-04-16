package com.gersion.superlock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlOpenHelper extends SQLiteOpenHelper {

	public MySqlOpenHelper(Context context) {
		super(context, "chickchick.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table manguo(number integer primary key autoincrement ,address varchar(20),name varchar(20),pwd varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
