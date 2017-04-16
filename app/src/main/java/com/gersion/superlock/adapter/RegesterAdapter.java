package com.gersion.superlock.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * ClassName: NewsCenterBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年8月9日 下午7:36:01 <br/>
 *
 * @作者 Gers
 * @版本
 * @包名 com.example.smartbeijing.bean
 * @待完成 TODO
 * @创建时间 2016年8月9日
 * @描述 TODO
 * @更新人 $Author$
 * @更新时间 $Date$
 * @更新版本 $Rev$
 */
public class RegesterAdapter extends PagerAdapter {

    private ArrayList<View> mList;

    public RegesterAdapter(ArrayList<View> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;

    }

    /**
     * DESC :  <br/>
     *
     * @see PagerAdapter#destroyItem(ViewGroup, int, Object)
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * DESC :  <br/>
     *
     * @see PagerAdapter#instantiateItem(ViewGroup, int)
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mList.get(position);
        container.addView(view);
        return view;

    }


}
  