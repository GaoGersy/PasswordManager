package com.gersion.superlock.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Window;

import com.gersion.superlock.R;

/**
 * Created by aa326 on 2018/1/30.
 */

public class MenuDialog {

    private final Context mContext;
    private final AlertDialog mDialog;
    private final Window mWindow;

    public MenuDialog(Context context) {
        mContext = context;
        mDialog = new AlertDialog.Builder(context,R.style.AnimBottom).create();
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
        mWindow = mDialog.getWindow();
        mWindow.setBackgroundDrawableResource(R.drawable.transpant);
        mWindow.setContentView(R.layout.dialog_menu);
        initListener();
    }

    protected void initListener() {

    }
}
