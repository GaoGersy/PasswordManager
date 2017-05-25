package com.gersion.superlock.bean;

import com.gersion.superlock.db.PasswordManager;
import com.gersion.superlock.utils.Aes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by a3266 on 2017/5/20.
 */

public class PasswordBean extends RealmObject{
    @PrimaryKey
    private long id;
    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    private String password;

    public String getPassword() {
        return Aes.decrypt(password, PasswordManager.mPasswordKey);
    }

    public void setPassword(String password) {
        this.password = Aes.encrypt(password, PasswordManager.mPasswordKey);
    }
}
