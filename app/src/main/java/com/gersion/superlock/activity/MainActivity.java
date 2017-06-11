package com.gersion.superlock.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.MainPagerAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.view.TitleView;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView mTitleView;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.alphaIndicator)
    AlphaTabsIndicator mAlphaIndicator;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        MainPagerAdapter mainAdapter = new MainPagerAdapter(getSupportFragmentManager(), mAlphaIndicator, mTitleView);
        mViewPager.setAdapter(mainAdapter);
        mViewPager.addOnPageChangeListener(mainAdapter);
        mAlphaIndicator.setViewPager(mViewPager);
        mTitleView.setAddVisiable(true)
                .setSearchVisiable(true)
                .setBackVisiable(false)
                .setTitleText("密码列表");
    }

    @Override
    protected void initListener() {
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTitleView.setOnSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mTitleView.setOnAddListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(AddPasswordActivity.class);
            }
        });
    }

}
