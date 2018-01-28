package com.gersion.superlock.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseAlertDialog;
import com.gersion.superlock.listener.OnDialogClickListener;


/**
 * Created by gersy on 2017/6/21.
 */

public class PermissionDialog extends BaseAlertDialog {
    private OnDialogClickListener mListener;
    private TextView mTv_content;
    private TextView mTv_ok;
    private TextView mTv_no;

    public PermissionDialog(Context context, OnDialogClickListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_permission;
    }

    @Override
    protected void initView() {
        mTv_content = (TextView) findViewById(R.id.tv_content);
        mTv_ok = (TextView) findViewById(R.id.tv_ok);
        mTv_no = (TextView) findViewById(R.id.tv_no);
    }

    public void setContent(String content){
        mTv_content.setText(content);
    }

    @Override
    protected void initListener() {
        mTv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.OnConfirmClick();
                }
                dismiss();
            }
        });

        mTv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.OnCancelClick();
                }
                dismiss();
            }
        });
    }
}
