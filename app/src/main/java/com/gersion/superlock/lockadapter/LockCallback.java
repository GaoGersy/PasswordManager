package com.gersion.superlock.lockadapter;

/**
 * Created by aa326 on 2018/1/9.
 */

public interface LockCallback {
    void onSuccess();

    void onError(String msg);

    void onChangLockType();
}
