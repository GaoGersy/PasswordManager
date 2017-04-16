package com.gersion.superlock.event;

import com.gersion.superlock.bean.Keyer;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.event
 * @待完成
 * @创建时间 2016/9/16
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class DetailEvent {
    private Keyer mMsg;
    public DetailEvent(Keyer msg){
        mMsg = msg;
    }
    public Keyer getMsg(){
        return  mMsg;
    }
}
