package com.gersion.superlock.bean;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;

/**
 * Created by a3266 on 2017/5/20.
 */

public class BaseBean implements RealmModel{
    @PrimaryKey
    private long id;
    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void copyParams(BaseBean baseBean){}
}
