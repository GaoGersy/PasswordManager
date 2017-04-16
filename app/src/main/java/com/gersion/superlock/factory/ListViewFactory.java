package com.gersion.superlock.factory;

import android.graphics.Color;
import android.widget.ListView;

import com.gersion.superlock.utils.UIUtils;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.factory
 * @待完成
 * @创建时间 2016/8/26
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class ListViewFactory {
    public static ListView create(){
        ListView listView = new ListView(UIUtils.getContext());
        listView.setDividerHeight(0);
        listView.setCacheColorHint(Color.TRANSPARENT);
        return listView;
    }
}
