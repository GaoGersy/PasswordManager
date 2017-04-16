package com.gersion.superlock.dao;

import android.content.ContentValues;
import android.content.Context;

import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.utils.Aes;
import com.gersion.superlock.utils.TimeUtils;
import com.orhanobut.logger.Logger;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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
public class PasswordDao {
    private Context mContext;
    private PasswordOpenHelper mHelper;
    private String password ;

    public PasswordDao(Context context) {
        this(context,null);
    }

    public PasswordDao(Context context,String originalPwd){
        mContext = context;
        mHelper = new PasswordOpenHelper(mContext);
        MainKeyDao mainKeyDao = new MainKeyDao(context);
        if (originalPwd==null){
            password = "09][asdw@#"+mainKeyDao.query()+"22oioiuwe";
        }else{
            password = "09][asdw@#"+originalPwd+"22oioiuwe";
        }

    }

    public boolean add(String location, String name, String pwd, String notes, String totalCount){
        SQLiteDatabase db = mHelper.getWritableDatabase(password);
        ContentValues values = new ContentValues();
        values.put(SqlPassword.Table.NAME,name);
        values.put(SqlPassword.Table.PWD,pwd);
        values.put(SqlPassword.Table.LOCATION,location);
        values.put(SqlPassword.Table.NUMBER,totalCount);
        if (notes!=null){
            values.put(SqlPassword.Table.NOTES,notes);
        }
        values.put(SqlPassword.Table.DATE, TimeUtils.getCurrentTimeInString());
        long insert = db.insert(SqlPassword.TABLE_NAME, null, values);
        db.close();
        return insert!=-1;
    }

    public boolean add(Keyer keyer){
        SQLiteDatabase db = mHelper.getWritableDatabase(password);
        ContentValues values = new ContentValues();
        values.put(SqlPassword.Table.NAME,keyer.name);
        values.put(SqlPassword.Table.PWD,keyer.pwd);
        values.put(SqlPassword.Table.LOCATION,keyer.address);
        if (keyer.createTime ==null){
            values.put(SqlPassword.Table.DATE, TimeUtils.getCurrentTimeInString());
        }else{
            values.put(SqlPassword.Table.DATE, keyer.createTime);
        }
        long insert = db.insert(SqlPassword.TABLE_NAME, null, values);
        db.close();
        return insert!=-1;
    }

    public boolean addAll(List<Keyer> data){
        if (data == null){
            return false;
        }
        boolean add = false;
        for (int i = 0; i < data.size(); i++) {
            Logger.d(i);
            add = add(data.get(i));
        }
        return add;
    }

    //关闭Helper
    public void destory(){
        mHelper.close();
    }

    public List<Keyer> query(){
        SQLiteDatabase db = mHelper.getReadableDatabase(password);
        List<Keyer> data = new ArrayList<>();
        String[] columns = new String[]{
                SqlPassword.Table.LOCATION,
                SqlPassword.Table.NAME,
                SqlPassword.Table.PWD,
                SqlPassword.Table.DATE,
                SqlPassword.Table.NOTES,
                SqlPassword.Table.UPDATE_DATE,
                SqlPassword.Table.NUMBER
        };
        Cursor cursor = db.query(SqlPassword.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String location = cursor.getString(0);
                String name = cursor.getString(1);
                String pwd = cursor.getString(2);
                String date = cursor.getString(3);
                String notes = cursor.getString(4);
                String updateTime = cursor.getString(5);
                String number = cursor.getString(6);
                Keyer keyer = new Keyer();
                keyer.address = location;
                keyer.name = name;
                keyer.pwd = pwd;
                keyer.createTime = date;
                keyer.notes = notes;
                keyer.updateTime = updateTime;
                keyer.number = number;
                data.add(keyer);
            }
        }
        cursor.close();
        db.close();
        return data;
    }

    //抽象的查询某个条目，不知道传入的数据到底是哪一种，用于被其它方法调用
    private Keyer query(String selection,String criteria){
        SQLiteDatabase db = mHelper.getReadableDatabase(password);
        String[] args = new String[]{criteria};
        Keyer keyer = new Keyer();
        String[] columns = new String[]{
                SqlPassword.Table.LOCATION,
                SqlPassword.Table.NAME,
                SqlPassword.Table.PWD,
                SqlPassword.Table.DATE,
                SqlPassword.Table.NOTES,
                SqlPassword.Table.UPDATE_DATE,
                SqlPassword.Table.NUMBER
        };
        Cursor cursor = db.query(SqlPassword.TABLE_NAME, columns, selection+"=?", args, null, null, null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String location = cursor.getString(0);
                String name = cursor.getString(1);
                String pwd = cursor.getString(2);
                String date = cursor.getString(3);
                String notes = cursor.getString(4);
                String updateTime = cursor.getString(5);
                String number = cursor.getString(6);
                keyer.address = location;
                keyer.name = name;
                keyer.pwd = pwd;
                keyer.createTime = date;
                keyer.notes = notes;
                keyer.updateTime = updateTime;
                keyer.number = number;
            }
        }
        cursor.close();
        db.close();
        return keyer;
    }

    public Keyer queryWithName(String name){
        return query(SqlPassword.Table.NAME,name);
    }

    public Keyer queryWithLocation(String location){
        return query(SqlPassword.Table.LOCATION,location);
    }

    public Keyer queryWithDate(String date){
        return query(SqlPassword.Table.DATE,date);
    }

    //抽象的查询某个条目，不知道传入的数据到底是哪一种，用于被其它方法调用
    public boolean update(String number,Keyer keyer){
        SQLiteDatabase db = mHelper.getWritableDatabase(password);
        ContentValues columns = new ContentValues();
        columns.put(SqlPassword.Table.NAME,keyer.name);
        columns.put(SqlPassword.Table.PWD,keyer.pwd);
        columns.put(SqlPassword.Table.LOCATION,keyer.address);
        columns.put(SqlPassword.Table.UPDATE_DATE,TimeUtils.getCurrentTimeInString());
        int update = db.update(SqlPassword.TABLE_NAME, columns, "number=?", new String[]{number});
        db.close();
        return update>0;
    }

    //删除单个
    public boolean delete(String id){
        SQLiteDatabase db = mHelper.getWritableDatabase(password);
        int delete = db.delete(SqlPassword.TABLE_NAME, "number=?", new String[]{id});
        db.close();
        return delete>0;
    }

    //删除全部
    public boolean deleteAll(List<Keyer> data){
        if (data==null){
            return false;
        }
        boolean isDelete = false;
        for (int i = 0; i < data.size(); i++) {
            isDelete = delete(data.get(i).number + "");
        }
        return isDelete;
    }

    private Keyer encrypt(Keyer keyer){
        keyer.address=Aes.encrypt(keyer.address,password);
        keyer.pwd=Aes.encrypt(keyer.pwd,password);
        keyer.createTime=Aes.encrypt(keyer.createTime,password);
        keyer.name=Aes.encrypt(keyer.name,password);
        if (keyer.updateTime!=null){
            keyer.updateTime = Aes.encrypt(keyer.updateTime,password);
        }
        return keyer;
    }

    private Keyer decrypt(Keyer keyer){
        keyer.address=Aes.decrypt(keyer.address,password);
        keyer.pwd=Aes.decrypt(keyer.pwd,password);
        keyer.createTime=Aes.decrypt(keyer.createTime,password);
        keyer.name=Aes.decrypt(keyer.name,password);
        if (keyer.updateTime!=null){
            keyer.updateTime = Aes.decrypt(keyer.updateTime,password);
        }
        return keyer;
    }

}
