package com.gersion.superlock.listener;

/**
 * Created by aa326 on 2018/1/28.
 */

public interface ResultCallback {
    void onResultSuccess(String result);

    void onResultFailed(String result);
}
