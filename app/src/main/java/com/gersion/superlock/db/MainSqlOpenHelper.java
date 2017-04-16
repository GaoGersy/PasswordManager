package com.gersion.superlock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainSqlOpenHelper extends SQLiteOpenHelper {
	public MainSqlOpenHelper(Context context) {
		super(context, "chiu.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table tata(number integer primary key autoincrement ,piapi varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
