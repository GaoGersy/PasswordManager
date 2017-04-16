package com.gersion.superlock.factory;

import android.content.Context;

import com.gersion.superlock.base.BaseDetailControl;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.control.DetailFaceControl;
import com.gersion.superlock.control.DetailItemControl;
import com.gersion.superlock.control.DetailNotesControl;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.factory
 * @待完成
 * @创建时间 2016/9/29
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class DetailControlFactory {
    public static BaseDetailControl createControl(int positon, Context context, Keyer keyer){
        BaseDetailControl control=null;
        switch (positon){
            case 0:
                control = new DetailFaceControl(context,keyer);
                break;
            case 1:
                control = new DetailItemControl(context,keyer);
                break;
            case 2:
                control = new DetailNotesControl(context,keyer);
                break;
        }
        return control;
    }
}
