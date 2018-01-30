package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.fragment.HomeFragment;
import com.gersion.superlock.service.FloatBallService;
import com.gersion.superlock.view.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    //    @BindView(R.id.titleView)
//    TitleView mTitleView;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_menu)
    TextView mTvMenu;
    @BindView(R.id.pwd_create)
    ItemView mPwdCreate;
    @BindView(R.id.setting)
    ItemView mSetting;
    @BindView(R.id.container)
    View mContainer;
    @BindView(R.id.menu_container)
    LinearLayout mMenuContainer;
    //    @BindView(R.id.mViewPager)
//    ViewPager mViewPager;
//    @BindView(R.id.viewpagertab)
//    SmartTabLayout mViewPagerTab;
//    @BindView(R.id.alphaIndicator)
//    AlphaTabsIndicator mAlphaIndicator;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
//        if (ConfigManager.getInstance().isEnableFloatBall()) {
//            addBall();
//        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
//        MainPagerAdapter mainAdapter = new MainPagerAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(mainAdapter);
//        mViewPager.addOnPageChangeListener(mainAdapter);
//        mAlphaIndicator.setViewPager(mViewPager);
//        mTitleView
//                .setSearchVisiable(true)
//                .setBackVisiable(false)
//                .setTitleText("密码管家");
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, new HomeFragment());
        fragmentTransaction.commit();
//        mViewPager.setOffscreenPageLimit(2);
//        mViewPagerTab.setViewPager(mViewPager);
    }

    private void openMenu() {
        mMenuContainer.setVisibility(View.VISIBLE);
        mMenuContainer.animate()
                .translationY(0)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mContainer.setVisibility(View.VISIBLE);
                    }
                })
                .start();
    }

    private void closeMenu() {
        mMenuContainer.animate()
                .translationY(-mMenuContainer.getHeight())
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        mContainer.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeMenu();
        return true;
    }

    private void addBall() {
        Intent intent = new Intent(MainActivity.this, FloatBallService.class);
        Bundle data = new Bundle();
        data.putInt("type", FloatBallService.TYPE_ADD);
        intent.putExtras(data);
        startService(intent);
    }

    @Override
    protected void initListener() {
//        mTitleView.setOnBackListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        mPwdCreate.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(PwdCreateActivity.class);
                closeMenu();
            }
        });

        mSetting.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(SettingActivity.class);
                closeMenu();
            }
        });
//
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalSearchActivity.start(MainActivity.this);
            }
        });

        mTvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toActivity(MenuActivity.class);
//                switchMenuState ();
                openMenu();
            }
        });

        mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
//
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    mTitleView.setAddVisiable(true);
//                    mTitleView.setSearchVisiable(true);
//                } else {
//                    mTitleView.setAddVisiable(false);
//                    mTitleView.setSearchVisiable(false);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

}
