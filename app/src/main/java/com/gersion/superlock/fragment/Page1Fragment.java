package com.gersion.superlock.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gersion.superlock.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.fragment
 * @待完成
 * @创建时间 2016/8/26
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class Page1Fragment extends Fragment {

    @BindView(R.id.tv_start_content)
    TextView mViewGuideVpTv;
    @BindView(R.id.activity_main)
    FrameLayout mActivityMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_guide_vp_1, null);
        ButterKnife.bind(this, view);

        initView();
        initData();

        initEvent();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    //初始化控件
    private void initView() {


    }

    public void doAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mViewGuideVpTv, "translationX", mActivityMain.getWidth(), 0);
        animator.setDuration(500);
        animator.setInterpolator(new AnticipateInterpolator());
        animator.start();
    }

    //初始化数据
    private void initData() {

    }

    //初始化监听事件
    private void initEvent() {


    }

}
