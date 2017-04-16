package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.dao.PasswordDao;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.CheckedEditTextView;
import com.gersion.toastlibrary.TastyToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AddPasswordActivity_1 extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.cetv_location)
    CheckedEditTextView mCetvLocation;
    @Bind(R.id.cetv_name)
    CheckedEditTextView mCetvName;
    @Bind(R.id.cetv_password)
    CheckedEditTextView mCetvPassword;
    @Bind(R.id.activity_add_password)
    LinearLayout mActivityAddPassword;
    @Bind(R.id.tv_commit)
    TextView mTvCommit;
    private float mCetvLocationY;
    private float mCetvNameY;
    private float mCetvPasswordY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password_1);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    //初始化控件
    private void initView() {
        mCetvPassword.setTextType(CheckedEditTextView.TextType.PASSWORD);
        mTvCommit.setAlpha(0);
    }

    //初始化数据
    private void initData() {

        mCetvLocationY = mCetvLocation.getY();
        mCetvNameY = mCetvName.getY();
        mCetvPasswordY = mCetvName.getY();

        mCetvPassword.setTextType(CheckedEditTextView.TextType.PASSWORD);
        performAnimator(mCetvLocation, 800, 1000);
        performAnimator(mCetvPassword, 900, 900);
        performAnimator(mCetvName, 800, 800);
//        mActivityAddPassword.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
////                mCetvLocation.setVisibility(View.VISIBLE);
////                mCetvName.setVisibility(View.VISIBLE);
////                mCetvPassword.setVisibility(View.VISIBLE);
//
//
//            }
//        });


    }

    private void performAnimator(final View view, int y, int dely) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -200f, y, 0);
        animator.setDuration(1500);
        animator.setStartDelay(dely);
        animator.setInterpolator(new OvershootInterpolator());
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                commitAnimator();
            }
        });
    }

    private void commitAnimator(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvCommit,"alpha",100f);
        animator.setDuration(2000);
        animator.start();
    }

    //初始化监听事件
    private void initEvent() {
        mTvCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_commit:
                int pwdStatus = mCetvPassword.getStatus();
                int nameStatus = mCetvName.getStatus();
                int locationStatus = mCetvLocation.getStatus();
                String pwd = mCetvPassword.getText().toString().trim();
                final String name = mCetvName.getText().toString().trim();
                String location = mCetvLocation.getText().toString().trim();
                if (!(TextUtils.isEmpty(pwd)||TextUtils.isEmpty(name)||TextUtils.isEmpty(location))){
                    final PasswordDao dao = new PasswordDao(this);
                    Observable.just(dao.add(location, name, pwd,"",""))
                            .observeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (aBoolean){
                                        ToastUtils.showTasty(AddPasswordActivity_1.this,"添加成功",TastyToast.SUCCESS);
                                        Keyer keyer = dao.queryWithName(name);
                                        mCetvLocation.setText("");
                                        mCetvName.setText("");
                                        mCetvPassword.setText("");
                                    }
                                }
                            });
                }

                break;
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
////        EventBus.getDefault().postSticky(new MessageEvent("ADD"));
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent event) {
//    }
}
