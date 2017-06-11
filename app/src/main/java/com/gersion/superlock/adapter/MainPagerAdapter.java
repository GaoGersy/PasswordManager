package com.gersion.superlock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.gersion.superlock.fragment.HomeFragment;
import com.gersion.superlock.fragment.MineFragment;
import com.gersion.superlock.fragment.PasswordCreaterFragment;
import com.gersion.superlock.view.TitleView;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a3266 on 2017/6/11.
 */

public class MainPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
    private List<Fragment> fragments = new ArrayList<>();
    private AlphaTabsIndicator mAlphaTabsIndicator;
    private TitleView mTitleView;
    private String[] mTitles = {"密码列表","密码生成器","我"};

    public MainPagerAdapter(FragmentManager fm, AlphaTabsIndicator alphaTabsIndicator, TitleView titleView) {
        super(fm);
        mAlphaTabsIndicator = alphaTabsIndicator;
        mTitleView = titleView;
        fragments.add(new HomeFragment());
        fragments.add(new PasswordCreaterFragment());
        fragments.add(new MineFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        if (0 == position) {
//            mAlphaTabsIndicator.getTabView(0).showNumber(alphaTabsIndicator.getTabView(0).getBadgeNumber() - 1);
//        } else if (2 == position) {
//            mAlphaTabsIndicator.getCurrentItemView().removeShow();
//        } else if (3 == position) {
//            mAlphaTabsIndicator.removeAllBadge();
//        }
        mTitleView.setTitleText(mTitles[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
