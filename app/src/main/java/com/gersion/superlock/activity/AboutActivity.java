package com.gersion.superlock.activity;

import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.view.TitleView;

public class AboutActivity extends BaseActivity {

    private TitleView mTitleView;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mTitleView.setAddVisiable(false);
        mTitleView.setSearchVisiable(false);
        mTitleView.setTitleText("关于");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
