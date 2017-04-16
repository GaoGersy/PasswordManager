package com.gersion.superlock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gersion.superlock.factory.FragmentFactory;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.adapter
 * @待完成
 * @创建时间 2016/8/26
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class GuideFragmentAdapter extends FragmentStatePagerAdapter {

    public GuideFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createGuideFragment(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
