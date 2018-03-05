package com.gersion.superlock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseLifeActivity;
import com.gersion.superlock.lockadapter.FingerPrintAdapter;
import com.gersion.superlock.lockadapter.LockAdapter;
import com.gersion.superlock.lockadapter.LockAdapterFactory;
import com.gersion.superlock.lockadapter.LockCallback;
import com.gersion.superlock.lockadapter.PinAdapter;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sdsmdg.tastytoast.TastyToast;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;

public class LockActivity extends BaseLifeActivity{

    private FrameLayout mFlContainer;
    private View currentView;
    private LockAdapter mLockAdapter;
    private FingerprintIdentify mFingerprintIdentify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImmersionBar.with(this).init();
        initData();
        initEvent();
        mFlContainer = (FrameLayout) findViewById(R.id.fl_container);
        initLockAdapter();

//        mFingerprintIdentify = new FingerprintIdentify(getApplicationContext(), new BaseFingerprint.FingerprintIdentifyExceptionListener() {
//            @Override
//            public void onCatchException(Throwable exception) {
//                Logger.e("\nException：" + exception.getLocalizedMessage());
//            }
//        });
//        start();
    }
    private void initLockAdapter() {
        initLockAdapter(-1);
    }
    private void initLockAdapter(int lockType) {
        if (lockType==-1){
            mLockAdapter = LockAdapterFactory.initLock();
        }else {
            mLockAdapter = LockAdapterFactory.initLock(lockType);
            fingerDestory();
        }
        View view = mLockAdapter.init(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        mFlContainer.addView(view);
        currentView=view;
        mLockAdapter.setLockCallback(mLockCallback);
//        mLockAdapter.onStart();
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
            if (isFingerLock) {
                lockAdapter.showFingerLockNotice();
            }
        }
    };
    // 初始化数据
    private void initData() {

    }

    // 按返回键直接进入手机桌面
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fingerDestory();
    }

    private void fingerDestory() {
        if (mLockAdapter instanceof FingerPrintAdapter){
            ((FingerPrintAdapter) mLockAdapter).onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fingerDestory();
    }

//    public void start() {
//        mFingerprintIdentify.startIdentify(4, new BaseFingerprint.FingerprintIdentifyListener() {
//            @Override
//            public void onSucceed() {
//                Logger.e("成功");
//            }
//
//            @Override
//            public void onNotMatch(int availableTimes) {
//                Logger.e(availableTimes+"次");
//            }
//
//            @Override
//            public void onFailed(boolean isDeviceLocked) {
//                Logger.e("是否禁用"+isDeviceLocked);
//                if (!isDeviceLocked){
//                    mFlContainer.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                           start();
//                        }
//                    },500);
//                }
//            }
//
//            @Override
//            public void onStartFailedByDeviceLocked() {
//                Logger.e("开启失败");
//            }
//        });
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mFingerprintIdentify.cancelIdentify();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mFingerprintIdentify.cancelIdentify();
//    }
}
