package com.gersion.superlock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseLifeActivity;
import com.gersion.superlock.lockadapter.LockAdapter;
import com.gersion.superlock.lockadapter.LockAdapterFactory;
import com.gersion.superlock.lockadapter.LockCallback;
import com.gersion.superlock.lockadapter.PinAdapter;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sdsmdg.tastytoast.TastyToast;

public class LockActivity extends BaseLifeActivity{

    private FrameLayout mFlContainer;
    private View currentView;
    private LockAdapter mLockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImmersionBar.with(this).init();
        initData();
        initEvent();
        mFlContainer = (FrameLayout) findViewById(R.id.fl_container);
        initLockAdapter();
    }
    private void initLockAdapter() {
        initLockAdapter(-1);
    }
    private void initLockAdapter(int lockType) {
        if (lockType==-1){
            mLockAdapter = LockAdapterFactory.initLock();
        }else {
            mLockAdapter = LockAdapterFactory.initLock(lockType);
        }
        View view = mLockAdapter.init(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        mFlContainer.addView(view);
        currentView=view;
        mLockAdapter.setLockCallback(mLockCallback);
        mLockAdapter.onStart();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // 初始化监听事件
    private void initEvent() {
    }

    LockCallback mLockCallback = new LockCallback() {
        @Override
        public void onSuccess() {
            finish();
        }

        @Override
        public void onError(String msg) {
            ToastUtils.showTasty(LockActivity.this, msg, TastyToast.ERROR);
        }

        @Override
        public void onChangLockType(boolean isFingerLock) {
            mFlContainer.removeView(currentView);
            initLockAdapter(MyConstants.LockType.TYPE_PIN);
            PinAdapter lockAdapter = (PinAdapter) mLockAdapter;
            lockAdapter.showFingerLockNotice();
        }
    };
    // 初始化数据
    private void initData() {

    }

    // 登录


    // 按返回键直接进入手机桌面
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(intent);
        finish();
    }

}
