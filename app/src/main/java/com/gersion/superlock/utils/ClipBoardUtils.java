package com.gersion.superlock.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by Gers on 2016/8/7.
 */

public class ClipBoardUtils {
    /**
     * 将字符串复制到剪贴板
     *
     * @param context
     * @param content
     */
    public static boolean copy(Context context, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content);
        return true;
    }
    //获取粘贴板数据
    public static String paste(Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString();
    }
}
