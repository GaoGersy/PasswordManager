package com.gersion.superlock.dao;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.dao
 * @待完成
 * @创建时间 2016/9/27
 * @功能描述 数据库中的内容
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public interface SqlPassword {
    String TABLE_NAME="info";
    String DB_NAME = "tata";
    interface Table{
        String ID = "_id";
        String NAME = "name";
        String NUMBER = "NUMBER";
        String PWD = "pwd";
        String LOCATION = "location";
        String DATE = "date";
        String UPDATE_DATE = "date";
        String NOTES = "notes";
        public static final String CREATE_TABLE = "create table "+TABLE_NAME+"(" +
                ID+" integer primary key autoincrement ," +
                NAME+" text," +
                PWD+"  text," +
                LOCATION+" text," +
                DATE+" text" +
                UPDATE_DATE+" text,"+
                NUMBER+" text,"+
                NOTES+" text)";
    }
}
