package com.gersion.superlock.factory;


import android.support.v4.app.Fragment;

import com.gersion.superlock.fragment.DetailNotesFragment;
import com.gersion.superlock.fragment.DetailFaceFragment;
import com.gersion.superlock.fragment.DetailsShowFragment;
import com.gersion.superlock.fragment.Page1Fragment;
import com.gersion.superlock.fragment.Page2Fragment;
import com.gersion.superlock.fragment.Page3Fragment;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.factory
 * @待完成
 * @创建时间 2016/8/26
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class FragmentFactory {
    public static Fragment createGuideFragment(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
               fragment = new Page1Fragment();
            break;
            case 1:
                fragment = new Page2Fragment();
            break;
            case 2:
                fragment = new Page3Fragment();
            break;
        }
        return fragment;
    }

    public static Fragment createDetailFragment(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new DetailFaceFragment();
                break;
            case 1:
                fragment = new DetailsShowFragment();
                break;
            case 2:
                fragment = new DetailNotesFragment();
                break;
        }
        return fragment;
    }
}
