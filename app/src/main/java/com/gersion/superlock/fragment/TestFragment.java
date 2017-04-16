package com.gersion.superlock.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.gersion.superlock.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.fragment
 * @待完成
 * @创建时间 2016/9/16
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class TestFragment extends Fragment {
    @Bind(R.id.vp_item)
    ViewPager mVpItem;
    private View mView;
    private ArrayList<String> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.view_viewpager, null);
        ButterKnife.bind(this, mView);
        initView();

        initData();

        initEvent();
        return mView;

    }

    //初始化控件
    private void initView() {


    }

    //初始化数据
    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mList.add(i + "");
        }
        mVpItem.setAdapter(new MyAdapter());
        mVpItem.setPageTransformer(true,new CubeOutTransformer());
    }

    //初始化监听事件
    private void initEvent() {

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String s = mList.get(position);
            TextView textView = new TextView(getActivity());
            textView.setText(s+"kjahsdkfal");
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
