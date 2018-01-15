package com.gersion.superlock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.MainPagerAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.service.FloatBallService;
import com.gersion.superlock.utils.ConfigManager;
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
        if (ConfigManager.getInstance().isEnableFloatBall()){
            addBall();
        }
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
        mViewPager.setOffscreenPageLimit(2);
    }

    private void addBall(){
        Intent intent = new Intent(MainActivity.this, FloatBallService.class);
        Bundle data = new Bundle();
        data.putInt("type", FloatBallService.TYPE_ADD);
        intent.putExtras(data);
        startService(intent);
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
                GlobalSearchActivity.start(MainActivity.this);
            }
        });

        mTitleView.setOnAddListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(AddPasswordActivity.class);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    mTitleView.setAddVisiable(true);
                    mTitleView.setSearchVisiable(true);
                }else{
                    mTitleView.setAddVisiable(false);
                    mTitleView.setSearchVisiable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
