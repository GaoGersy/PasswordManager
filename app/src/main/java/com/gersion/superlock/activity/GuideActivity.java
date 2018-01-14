package com.gersion.superlock.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.GuideFragmentAdapter;
import com.nineoldandroids.view.ViewHelper;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import java.util.List;

public class GuideActivity extends AppCompatActivity implements OnPageChangeListener {

    private ViewPager mVp;
    private List<View> mList;
    private LinearLayout mPaine;
    private LinearLayout mContainer;
    private int mCurrentPosition;
    private ImageView mIvPre;
    private ImageView mIvNext;
    private ImageView mIvGo;
    private View mTv;
    private GuideFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        initEvent();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // 初始化控件
    private void initView() {
        mVp = (ViewPager) findViewById(R.id.activity_splash_vp);
        mContainer = (LinearLayout) findViewById(R.id.activity_splash_step);
        mIvPre = (ImageView) findViewById(R.id.activity_splash_pre);
        mIvNext = (ImageView) findViewById(R.id.activity_splash_next);
        mIvGo = (ImageView) findViewById(R.id.activity_splash_go);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (ConfigManager.getInstance().isFinishGuide()) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    // 初始化数据
    private void initData() {
        //初始化adapter
        initPagerAdapter();
        //初始化指示点的数据
        initContainer();
    }

    private void initContainer() {
        for (int i = 0; i < 3; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.selector_splash_step);
            int size = getResources().getDimensionPixelSize(R.dimen.step_size);
            LayoutParams parmas =
                    new LayoutParams(size, size);
            if (i != 0) {
                parmas.leftMargin = size;
            } else {
                iv.setSelected(true);
                mCurrentPosition = i;
            }
            mContainer.addView(iv, parmas);
        }
    }

    private void initPagerAdapter() {
//        mList = new ArrayList<View>();
//        View view1 = View.inflate(this, R.layout.view_guide_vp_1, null);
//        mTv = view1.findViewById(R.id.view_guide_vp_tv);
//        mList.add(view1);
//
//        View view2 = View.inflate(this, R.layout.view_guide_vp_2, null);
//        mList.add(view2);
//
//        View view3 = View.inflate(this, R.layout.view_guide_vp_end, null);
////        View view3 = new PageOneController().getRootView(this);
//        mList.add(view3);
//
//        GuidePagerAdapter adapter = new GuidePagerAdapter(this, mList);

        mAdapter = new GuideFragmentAdapter(getSupportFragmentManager());
        mVp.setAdapter(mAdapter);
    }


    // 初始化事件监听
    private void initEvent() {

        mVp.setPageTransformer(true, new CrossfadePageTransformer());
        mVp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {

        if (mCurrentPosition < position) {

            mContainer.getChildAt(position).setSelected(true);
        } else {
            mContainer.getChildAt(mCurrentPosition).setSelected(false);
        }
        mCurrentPosition = position;
    }
    // ViewPagger切换缩放动画

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View container = page.findViewById(R.id.activity_main);

            View welBg = page.findViewById(R.id.view_guide_tv_bg);
            View welcome = page.findViewById(R.id.tv_start_content);
            View green = page.findViewById(R.id.iv_dot_green);
            View yellow = page.findViewById(R.id.iv_dot_yellow);
            View purple = page.findViewById(R.id.iv_dot_purple);
            View completeContent = page.findViewById(R.id.tv_complete_content);
            View middle = page.findViewById(R.id.tv_middle_content);
            View complete = page.findViewById(R.id.tv_complete);

            if (0 <= position && position < 1) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }
            if (-1 < position && position < 0) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }

            if (position <= -1.0f || position >= 1.0f) {
            } else if (position == 0.0f) {
            } else {
                if (container != null) {
                    ViewHelper.setAlpha(container, 1.0f - Math.abs(position));

                }

                if (welcome != null) {
                    ViewHelper.setAlpha(welcome, 1.0f - Math.abs(position));
                    ViewHelper.setTranslationX(welcome, (float) (pageWidth / 1.2 * position));
                }

                if (welBg != null) {
                    ViewHelper.setTranslationX(welBg, (float) (pageWidth / 1.6 * position));
                }
                if (green != null) {
                    ViewHelper.setTranslationX(green, (float) (pageWidth / 1.4 * position));
                }

                if (yellow != null) {
                    ViewHelper.setTranslationX(yellow, (float) (pageWidth / 1.6 * position));
                }

                if (purple != null) {
                    ViewHelper.setTranslationX(purple, (float) (pageWidth / 1.8 * position));
                }

                if (middle != null) {
                    ViewHelper.setTranslationX(middle, (float) (pageWidth / 2.2 * position));
                }
                if (completeContent != null) {
                    ViewHelper.setTranslationX(completeContent, (float) (pageWidth / 2.2 * position));
                }

                // parallax effect
                if (complete != null) {
                    ViewHelper.setTranslationX(complete, (float) (pageWidth / 1.2 * position));
                }

            }
        }
    }
}
