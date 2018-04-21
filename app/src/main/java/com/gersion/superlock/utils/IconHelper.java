package com.gersion.superlock.utils;


import android.content.Context;

import com.gersion.superlock.app.SuperLockApplication;
import com.gersion.superlock.bean.IconBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gersy on 2018/4/1.
 */

public class IconHelper {
    private static final String ICONS = "alipay,apple,baidu,bili,csdn,default_icon,douban,github,guoke,jd,jianshu,linux,mail,momo,qq,sina,taobao,tudou,wangyi,wangyiyunyinyue,weixin,weizhi,windows,xiami,xiaomi,xunlei,yingxiang,youku,zakker,zhihu";

    public static List<IconBean> getIconResourceId() {
        String[] split = ICONS.split(",");
        ArrayList<IconBean> iconResourceIds = new ArrayList<>();
        for (String s : split) {
            int resource = getResource(s);
            IconBean bean = new IconBean();
            bean.setResourceId(resource);
            iconResourceIds.add(bean);
        }
        return iconResourceIds;
    }

    private static int getResource(String imageName) {
        Context ctx = SuperLockApplication.getContext();
        int resId = ctx.getResources().getIdentifier(imageName, "mipmap", ctx.getPackageName());
        return resId;
    }
}
