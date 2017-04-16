package com.gersion.superlock.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.utils.LogUtils;

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
public class Page2Fragment extends Fragment {


    TextView mViewGuideVpTv;
    FrameLayout mActivityMain;
    private View mView;
    private MyTask mTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.view_guide_vp_2, null);
        initView();
        initData();
        initEvent();
        return mView;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {
        mViewGuideVpTv = (TextView) mView.findViewById(R.id.tv_middle_content);
        mActivityMain = (FrameLayout) mView.findViewById(R.id.activity_main);
    }

    public void doAnimator(){
        mTask = new MyTask();
        mTask.start();

    }

    //初始化控件
    public void setAnimator() {

        if (mViewGuideVpTv != null){
            LogUtils.e("gogogoog");
            ObjectAnimator animator = ObjectAnimator.ofFloat(mViewGuideVpTv, "translationX", mActivityMain.getWidth(), 0);
            animator.setDuration(500);
            animator.setInterpolator(new AnticipateInterpolator());
            animator.start();
            mTask.stop();
        }

    }

    class MyTask extends Handler implements Runnable{

        @Override
        public void run() {
            setAnimator();
            postDelayed(this,200);
        }
        public void start(){
            postDelayed(this,200);
        }
        public void stop(){
            removeCallbacks(this);
        }
    }


    //初始化数据
    private void initData() {

    }

    //初始化监听事件
    private void initEvent() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
