package com.gersion.superlock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.app.SuperLockApplication;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.utils.ClipBoardUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by gersy on 2018/4/21.
 */

public class DetaiItemView extends RelativeLayout {

    private View mView;
    private Context mContext;
    private TextView mTvName;
    private TextView mTvValue;
    private ImageView mIvCopy;

    public DetaiItemView(Context context) {
        this(context,null);
    }

    public DetaiItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.view_detail_show, this);
        mContext = context;
        mTvName = mView.findViewById(R.id.tv_name);
        mTvValue = mView.findViewById(R.id.tv_value);
        mIvCopy = mView.findViewById(R.id.iv_copy);

        mIvCopy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = mTvValue.getText().toString().trim();
                ClipBoardUtils.copy(SuperLockApplication.getContext(),value);
                ToastUtils.showTasty((BaseActivity)mContext,"已复制到剪贴板", TastyToast.SUCCESS);
            }
        });
    }

    public void setItemName(String name){
        mTvName.setText(name);
    }

    public void setItemValue(String value){
        mTvValue.setText(value);
    }
}
