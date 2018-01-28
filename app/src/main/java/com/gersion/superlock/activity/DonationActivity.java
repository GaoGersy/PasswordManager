package com.gersion.superlock.activity;

import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.view.TitleView;


public class DonationActivity extends BaseActivity {

    private TitleView mTitleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_donation;
    }

    @Override
    protected void initView() {
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mTitleView.setAddVisiable(false);
        mTitleView.setSearchVisiable(false);
        mTitleView.setTitleText("加一下好友吧");
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
