package com.gersion.superlock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gersion.superlock.R;


public class TitleView extends RelativeLayout {

    private ImageView mBack;
    private TextView mTitle;
    private ImageView mIvSearch;
    private TextView mRightText;
    private ImageView mIvAdd;

    public TitleView(Context context) {
        super(context);
        initView(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_title, this);
        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mIvAdd = (ImageView) findViewById(R.id.iv_add);
        mRightText = (TextView) findViewById(R.id.right_text);
        mIvAdd.setRotation(45);
    }

    public ImageView getBack() {
        return mBack;
    }

    public ImageView getRightImg() {
        return mIvSearch;
    }

    public TextView getTitle() {
        return mTitle;
    }

    public TextView getRightText() {
        return mRightText;
    }

    public void setOnBackListener(OnClickListener listener) {
        if (mBack != null) {
            mBack.setOnClickListener(listener);
        }
    }

    public void setOnSearchListener(OnClickListener listener) {
        if (mIvSearch != null) {
            mIvSearch.setOnClickListener(listener);
        }
    }

    public void setOnAddListener(OnClickListener listener) {
        if (mIvAdd != null) {
            mIvAdd.setOnClickListener(listener);
        }
    }

    public TitleView setRightTextVisiable(boolean visiable) {
        if (mRightText != null) {
            mRightText.setVisibility(visiable?VISIBLE:GONE);
        }
        return this;
    }

    public TitleView setTitleText(String text) {
        mTitle.setText(text);
        return this;
    }

    public TitleView setRightText(String text) {
        if (mRightText != null) {
            mRightText.setText(text);
        }
        return this;
    }

    public void setRightTextListener(OnClickListener listener) {
        if (mRightText != null) {
            mRightText.setOnClickListener(listener);
        }
    }

    public TitleView setSearchVisiable(boolean visiable) {
        if (mIvSearch != null) {
            mIvSearch.setVisibility(visiable?VISIBLE:GONE);
            return this;
        }
        return this;
    }

    public TitleView setAddVisiable(boolean visiable) {
        if (mIvAdd != null) {
            mIvAdd.setVisibility(visiable?VISIBLE:GONE);
        }
        return this;
    }

    public TitleView setBackVisiable(boolean visiable) {
        if (mBack != null) {
            mBack.setVisibility(visiable?VISIBLE:GONE);
        }
        return this;
    }
}
