package com.gersion.superlock.bean;

import io.realm.RealmObject;

/**
 * Created by a3266 on 2017/5/21.
 */

public class UpdateBean extends RealmObject {
//    @PrimaryKey
//    private long id;
    private long updateTime;
    private String password;

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
