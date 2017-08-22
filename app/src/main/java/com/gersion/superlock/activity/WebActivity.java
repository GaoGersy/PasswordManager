package com.gersion.superlock.activity;

import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.view.RefreshWebView;
import com.gersion.superlock.view.TitleView;


public class WebActivity extends BaseActivity {

    private RefreshWebView mRefreshWebView;
    private TitleView mTitleView;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        mRefreshWebView = (RefreshWebView) findViewById(R.id.refreshWebView);
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mRefreshWebView.loadUrl("https://github.com/GaoGersy/PasswordManager");
        mTitleView.setSearchVisiable(false);
        mTitleView.setAddVisiable(false);
        mTitleView.setTitleText("项目地址");
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
