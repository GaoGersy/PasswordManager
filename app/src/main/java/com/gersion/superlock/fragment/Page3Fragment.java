package com.gersion.superlock.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.activity.RegesterActivity;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.PasswordUtils;
import com.gersion.superlock.utils.SpfUtils;

import butterknife.Bind;
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
public class Page3Fragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.tv_complete_content)
    TextView mViewGuideVpTv;
    @Bind(R.id.tv_complete)
    TextView mTvComplete;
    @Bind(R.id.activity_main)
    FrameLayout mActivityMain;
    @Bind(R.id.container)
    LinearLayout mContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_guide_vp_end, null);
        ButterKnife.bind(this, view);
        initView();

        initData();

        initEvent();
        return view;

    }

    //初始化控件
    private void initView() {


    }


    public void doAnimator(View view) {
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        set.setDuration(1500);
        set.setInterpolator(new AnticipateInterpolator());
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(getActivity(), RegesterActivity.class));
                getActivity().finish();

                saveRandomKey();
            }
        });
    }

    private void saveRandomKey() {
        String test = SpfUtils.getString(getActivity(), MyConstants.TEST, null);
        if (TextUtils.isEmpty(test)){
            String randomKey = PasswordUtils.getNewPassword(false,true,true,false,10);
            SpfUtils.putString(getActivity(), MyConstants.TEST,randomKey);
        }

    }

    //初始化数据
    private void initData() {

    }

    //初始化监听事件
    private void initEvent() {
        mTvComplete.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        doAnimator(mContainer);

    }
}
