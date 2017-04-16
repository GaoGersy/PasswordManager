package com.gersion.superlock.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.db.MySqlOpenHelper;
import com.gersion.superlock.utils.AESUtils;
import com.gersion.superlock.utils.MainPwdUtils;


public class MyKeyDao {
	private Context context;
	private MySqlOpenHelper helper;
	private String masterPassword;

	public MyKeyDao(Context context) {
		this.context = context;
		helper = new MySqlOpenHelper(context);
		getSuperKey(context);
	}

	public void getSuperKey(Context context) {
		String mainKey = MainPwdUtils.getSuperKey(context);
		masterPassword = mainKey+mainKey+mainKey;
		/*SuperKeyDao skd = new SuperKeyDao(context);
		String query = skd.query();
		masterPassword = query;*/
	}

	public int getCount() {
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.query("manguo", null, null, null, null, null, null, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}

	public boolean add(String address, String name, String pwd) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		Keyer keyer = encryptData(address, name, pwd);
		values.put("address", keyer.address);
		values.put("name", keyer.name);
		values.put("pwd", keyer.pwd);
		long rowId = db.insert("manguo", null, values);
		db.close();
		return rowId == -1 ? false : true;
	}

	public boolean del(String address) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String encryptData = encryptData(address);
		int delete = db.delete("manguo", "address=?", new String[] { encryptData });
		db.close();
		return delete > 0 ? true : false;
	}

	public boolean update(String address, String name, String pwd) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		Keyer keyer = encryptData(address, name, pwd);
		values.put("address", keyer.address);
		values.put("name", keyer.name);
		values.put("pwd", keyer.pwd);
		int update = db.update("manguo", values, "address=?", new String[] { keyer.address });
		db.close();
		return update > 0 ? true : false;
	}

	public Keyer query(String address) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor query = db.query("manguo", new String[] { "address,name,pwd" }, "address=?", new String[] { address },
				null, null, null);
		Keyer keyer = null;
		while (query.moveToFirst()) {
			String name = query.getString(1);
			String pwd = query.getString(2);
			keyer = decryptData(address, name, pwd);
		}
		query.close();
		db.close();
		return keyer;
	}

	public Keyer query() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor query = db.query("manguo", new String[] { "address,name,pwd" }, null, null, null, null, null);
		Keyer keyer = null;
		if (query.moveToFirst()) {
			String address = query.getString(0);
			String name = query.getString(1);
			String pwd = query.getString(2);
			keyer = new Keyer();
			keyer = decryptData(address, name, pwd);
		}
		query.close();
		db.close();
		return keyer;
	}

	public Keyer query(int position) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor query = db.query("manguo", new String[] { "address,name,pwd" }, null, null, null, null, null);
		Keyer keyer = null;
		if (query.moveToPosition(position)) {
			String address = query.getString(0);
			String name = query.getString(1);
			String pwd = query.getString(2);
			keyer = decryptData(address, name, pwd);
		}
		query.close();
		db.close();
		return keyer;
	}

	// ����Ҫ�洢����������
	public Keyer encryptData(String address, String name, String pwd) {
		// byte[] encryptaddress = Aesbak.encrypt(address, password);
		// byte[] encryptName = Aesbak.encrypt(name, password);
		// byte[] encryptPwd = Aesbak.encrypt(pwd, password);
		// String parsePwd = Aesbak.parseByte2HexStr(encryptPwd);
		// String parseName = Aesbak.parseByte2HexStr(encryptName);
		// String parseAddress = Aesbak.parseByte2HexStr(encryptaddress);
		try {
			String encryptingCodeAddress = AESUtils.encrypt(masterPassword, address);
			String encryptingCodeName = AESUtils.encrypt(masterPassword, name);
			String encryptingCodePwd = AESUtils.encrypt(masterPassword, pwd);
			Keyer keyer = new Keyer();
			keyer.pwd = encryptingCodePwd;
			keyer.name = encryptingCodeName;
			keyer.address = encryptingCodeAddress;
			return keyer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ���ܴ洢�˵���������
	public Keyer decryptData(String address, String name, String pwd) {
		try {
			String decryptingCodeAddress = AESUtils.decrypt(masterPassword, address);
			String decryptingCodeName = AESUtils.decrypt(masterPassword, name);
			String decryptingCodePwd = AESUtils.decrypt(masterPassword, pwd);
			Keyer keyer = new Keyer();
			keyer.pwd = decryptingCodePwd;
			keyer.name = decryptingCodeName;
			keyer.address = decryptingCodeAddress;
			return keyer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			e.printStackTrace();
		}
		return null;
	}

}
