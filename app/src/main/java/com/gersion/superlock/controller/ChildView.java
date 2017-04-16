package com.gersion.superlock.controller;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseDetailControl;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.control.DetailItemControl;
import com.gersion.superlock.factory.DetailControlFactory;

import java.util.ArrayList;
import java.util.List;

public class ChildView implements ViewPager.OnPageChangeListener {

    public ViewPager viewPager;
    boolean isDrag = false;
    private List<View> list = new ArrayList<View>();
    private List<BaseDetailControl> controlList = new ArrayList<>();
    private Context mContext;
    private Keyer mKeyer;
    private MyPageAdapter mAdapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public void init(Context context, Keyer keyer) {
        mContext = context;
        mKeyer = keyer;

        for (int i = 0; i < 3; i++) {
//            if (i==1){
//                View rootView = mControl.getRootView();
//                list.add(rootView);
//            }else{
//                Button view = new Button(mContext);
//                view.setText("ViewPager中的TextView：");
//                view.setTextSize(20);
//                list.add(view);
//            }
            BaseDetailControl mControl = DetailControlFactory.createControl(i, mContext, mKeyer);
            controlList.add(mControl);

            list.add(mControl.getRootView());

        }
        viewPager = (ViewPager) View.inflate(mContext, R.layout.layout_viewpager, null);
        /**
         * 必须设置LayoutParams
         */

//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500);
//        viewPager.setLayoutParams(lp);

        mAdapter = new MyPageAdapter();
        viewPager.setAdapter(mAdapter);
        viewPager.setPageTransformer(true, new CubeOutTransformer());
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        if (position == 1) {
            DetailItemControl control = (DetailItemControl) controlList.get(position);
            control.playAnimator();
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        switch (state) {
//            case ViewPager.SCROLL_STATE_DRAGGING:
//                isDrag = true;
//                break;
//            case ViewPager.SCROLL_STATE_IDLE:
//                if (isDrag) {
//                    if (mCurrentPositon != mKeyer.position && mKeyer.isVisible == true) {
//                        mKeyer.position = mCurrentPositon;
//                    }
//                    isDrag = false;
//                }
//                break;
//            case ViewPager.SCROLL_STATE_SETTLING:
//
//                break;
//        }
    }


    class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = list.get(position);
            container.addView(view);
            return view;

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

}  