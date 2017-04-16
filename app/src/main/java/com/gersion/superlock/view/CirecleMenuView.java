package com.gersion.superlock.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gersion.superlock.R;

/**
 * Created by Gers on 2016/8/7.
 */

public class CirecleMenuView extends RelativeLayout implements View.OnClickListener {

    private TextView menu_big;
    private TextView menu_small_1;
    private TextView menu_small_2;
    private TextView menu_small_3;
    private TextView menu_small_4;
    private TextView menu_small_5;
    private int mDistance;
    private TextView menu_cai;
    private RelativeLayout mContainer;

    public CirecleMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_circle_menu, this);

        initView();

        initData();

        initEvent();

    }

    //初始化控件
    private void initView() {

        menu_small_1 = (TextView) findViewById(R.id.view_circle_menu_small_1);

        menu_big = (TextView) findViewById(R.id.view_circle_menu_big);
        menu_small_2 = (TextView) findViewById(R.id.view_circle_menu_small_2);
        menu_small_3 = (TextView) findViewById(R.id.view_circle_menu_small_3);
        menu_small_4 = (TextView) findViewById(R.id.view_circle_menu_small_4);
        menu_small_5 = (TextView) findViewById(R.id.view_circle_menu_small_5);
        menu_cai = (TextView) findViewById(R.id.view_circle_menu_cai);
        mContainer = (RelativeLayout) findViewById(R.id.view_circle_menu_container);

    }

    //初始化数据
    private void initData() {
        mDistance = getResources().getDimensionPixelSize(R.dimen.dinstance);

    }

    //初始化事件监听
    private void initEvent() {
        menu_big.setOnClickListener(this);
        menu_small_1.setOnClickListener(this);
        menu_small_2.setOnClickListener(this);
        menu_small_3.setOnClickListener(this);
        menu_small_4.setOnClickListener(this);
        menu_small_5.setOnClickListener(this);
        menu_cai.setOnClickListener(this);

    }

    public void performCaiAnimator(){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(menu_cai,"scaleX",1.0f,1.5f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(menu_cai,"scaleY",1.0f,1.5f);
        ObjectAnimator animatorT = ObjectAnimator.ofFloat(menu_cai,"translationX",0,-300);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorT,animatorX,animatorY);
        set.start();
    }

    private void preformOpen(View view, int position) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -position);
        animator.setDuration(300);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setCurrentPlayTime(5);
        animator.start();
    }

    private void preformClose(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0);
        animator.setDuration(500);
        animator.setInterpolator(new AnticipateInterpolator());
        animator.start();
    }

    //X,Y方向移动的动画
    private void preformOpen(View view,int time,int x,int y){
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "translationY", y);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", x);
        ObjectAnimator rotationBig = ObjectAnimator.ofFloat(menu_big, "rotation", 0,90);
        ObjectAnimator rotationSmall = ObjectAnimator.ofFloat(view, "rotation", 0,720);
        rotationBig.setRepeatMode(ObjectAnimator.REVERSE);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
//        set.play(animatorX).after(animatorY);
//        set.playSequentially(animatorX,animatorY);
        set.playTogether(animatorX,animatorY,rotationBig,rotationSmall);
        set.setStartDelay(time);
        set.setInterpolator(new OvershootInterpolator());
        set.start();

    }

    private void preformOpen(View view,int time, float degree,int y){
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "rotation",degree);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", y);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
//        set.playSequentially(animatorX,animatorY);
        set.playTogether(animatorX,animatorY);
        set.setStartDelay(time);
        set.setInterpolator(new OvershootInterpolator());
        set.start();

    }

    private void preformClose(View view,int time){
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "translationY", 0);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", 0);
        ObjectAnimator rotationBig = ObjectAnimator.ofFloat(menu_big, "rotation", 0,90);
        ObjectAnimator rotationSmall = ObjectAnimator.ofFloat(view, "rotation", 0,720);
        rotationBig.setRepeatMode(ObjectAnimator.REVERSE);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animatorX,animatorY,rotationBig,rotationSmall);
        set.setStartDelay(time);
        set.setInterpolator(new AnticipateInterpolator());
        set.start();

    }

    //直线形开
    private void lineAnimatorOpen(){
        preformOpen(menu_small_1, 70);
        preformOpen(menu_small_2, 130);
        preformOpen(menu_small_3, 190);
        preformOpen(menu_small_4, 250);
        preformOpen(menu_small_5, 310);
    }

    //直线形关
    private  void lineAnimatorClose(){
        preformClose(menu_small_1);
        preformClose(menu_small_2);
        preformClose(menu_small_3);
        preformClose(menu_small_4);
        preformClose(menu_small_5);
    }

    //半圆形开
    private void arcAnimatorOpen(){
        preformOpen(menu_small_3,200,0,-mDistance*3);
        preformOpen(menu_small_2,300,mDistance*2,-mDistance*2);
        preformOpen(menu_small_1,400,mDistance*3,0);
        preformOpen(menu_small_4,100,-mDistance*2,-mDistance*2);
        preformOpen(menu_small_5,0,-mDistance*3,0);
    }
    //圆形开
    private void circleAnimatorOpen(){
        preformOpen(menu_small_3,200,0f,-90);
        preformOpen(menu_small_2,300,60f,-65);
        preformOpen(menu_small_1,400,90f,-10);
        preformOpen(menu_small_4,100,-60f,-65);
        preformOpen(menu_small_5,0,-90f,-10);
    }
    //动画关闭
    private void animatorClose(){
        preformClose(menu_small_1,0);
        preformClose(menu_small_2,100);
        preformClose(menu_small_3,200);
        preformClose(menu_small_4,300);
        preformClose(menu_small_5,400);
    }


    private boolean isOpen = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_circle_menu_big:
                if (isOpen) {
                    isOpen = !isOpen;
                    arcAnimatorOpen();
                } else {
                    isOpen = !isOpen;
                    animatorClose();
                }
                break;
            case R.id.view_circle_menu_small_1:
//                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.view_circle_menu_small_2:
                break;
            case R.id.view_circle_menu_small_3:
                break;
            case R.id.view_circle_menu_small_4:
                break;
            case R.id.view_circle_menu_small_5:
                break;
            case R.id.view_circle_menu_cai:
                performCaiAnimator();
                break;
        }
    }
}
