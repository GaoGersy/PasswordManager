package com.gersion.superlock.base;

import android.content.Context;
import android.view.View;

import com.gersion.superlock.bean.Keyer;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.base
 * @待完成
 * @创建时间 2016/9/29
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public abstract class BaseDetailControl {
    public Context mContext;
    public Keyer mKeyer;
    public View mView;


    public BaseDetailControl(Context context, Keyer keyer) {
        mContext = context;
        mKeyer = keyer;
    }

    public View getRootView() {

        mView = initView();

        initView();

//        initData();
//
//        initEvent();
        return mView;

    }

    public abstract View initView();
}
