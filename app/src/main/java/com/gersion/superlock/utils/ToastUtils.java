package com.gersion.superlock.utils;

import android.app.Activity;
import android.widget.Toast;

import com.gersion.toastlibrary.TastyToast;

/**
 * ClassName:Utils <br/>
 * Function: 工具类 <br/>
 * Date: 2016年7月10日 下午7:55:04 <br/>
 *
 * @author Ger
 */
public class ToastUtils {
    private static Toast toast;
    private static TastyToast tastyToast;
    //当前的Toast类型，用于判断是否需要要重新加载布局
    private static int currentType ;

    public static void show(final Activity context, final String text) {
        context.runOnUiThread(new Runnable() {
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {
                    toast.setText(text);
                }
                toast.show();
            }
        });
    }

    /**
     * @param context Activity类型的对象
     * @param text 要显示的字符
     * @param type Toast类型
     */
    public static void showTasty(final Activity context, final String text, final int type) {

        context.runOnUiThread(new Runnable() {
            public void run() {
                if (tastyToast == null) {
                    currentType = type;
                    tastyToast = new TastyToast();
                    tastyToast.makeText(context, text, TastyToast.LENGTH_LONG,
                            type);
                } else {
                    if (type != currentType) {
                        currentType = type;
                        tastyToast.makeText(context, text, TastyToast.LENGTH_LONG,
                                type);
                    }
                    tastyToast.setText(text);
                }
                tastyToast.show();
            }
        });
    }
}
