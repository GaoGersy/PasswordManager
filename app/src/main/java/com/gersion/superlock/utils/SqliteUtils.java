package com.gersion.superlock.utils;

import android.content.Context;

import com.gersion.superlock.dao.SqlPassword;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.utils
 * @待完成
 * @创建时间 2016/9/27
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class SqliteUtils {
    //获取数据文件的file对象
    public static File getSqlPath(Context context, String name){
        File databaseFile = context.getDatabasePath(name+".db");
        return databaseFile;
    }

    public static void createSql(Context context,String name, String pwd){
        File sqlPath = getSqlPath(context, name);
        if (sqlPath.exists()){
            sqlPath.delete();
        }
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(sqlPath,"09][asdw@#"+pwd+"22oioiuwe", null);
        database.execSQL(SqlPassword.Table.CREATE_TABLE);
        database.close();
    }

}
