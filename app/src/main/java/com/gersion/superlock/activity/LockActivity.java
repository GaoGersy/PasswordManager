package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseLifeActivity;
import com.gersion.superlock.db.PasswordManager;
import com.gersion.superlock.utils.AnimatorUtils;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SPManager;
import com.gersion.superlock.utils.SpfUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sdsmdg.tastytoast.TastyToast;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockActivity extends BaseLifeActivity implements View.OnClickListener {

    public static String IS_FIRST_TIME = "is_first_time";
    @BindView(R.id.activity_login_pwd)
    EditText mLoginPwd;
    @BindView(R.id.layout_username)
    TextInputLayout mLayoutUsername;
    @BindView(R.id.activity_login_go)
    ImageView mLoginGo;
    @BindView(R.id.iv_finger)
    ImageView mIvFinger;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_password)
    Button mBtnPassword;
    @BindView(R.id.activity_main)
    FrameLayout mActivityMain;
    @BindView(R.id.fl_pwd_container)
    FrameLayout mFlPwdContainer;
    @BindView(R.id.rl_finger_container)
    RelativeLayout mRlFingerContainer;
    @BindView(R.id.tv_notice)
    TextView mTvNotice;
    private boolean isfirstTime;
    private boolean mIsAutoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();
//        initFingerPrint();
        initData();
        initEvent();
    }

    BaseFingerprint.FingerprintIdentifyListener mFingerprintIdentifyListener = new BaseFingerprint.FingerprintIdentifyListener() {
        @Override
        public void onSucceed() {
            mIvFinger.setImageResource(R.mipmap.success);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 300);
        }

        @Override
        public void onNotMatch(int availableTimes) {
            mTvNotice.setText("指纹不匹配，还可以尝试 " + availableTimes + " 次");
            shake();
        }

        @Override
        public void onFailed() {
            mTvNotice.setText("指纹解锁已禁用，请 " + 15 + " 秒后重试");
            SPManager.setLockedTime(SystemClock.currentThreadTimeMillis());
            mIvFinger.setImageResource(R.mipmap.alert);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIvFinger.setImageResource(R.mipmap.finger);
                    mTvNotice.setText("请轻触指纹感应器验证指纹");
                    SuperLockApplication.mFingerprintIdentify.startIdentify(5, mFingerprintIdentifyListener);
                }
            },15000);
        }
    };

    private void initFingerPrint() {
        if (SuperLockApplication.mFingerprintIdentify.isFingerprintEnable()) {
            SuperLockApplication.mFingerprintIdentify.startIdentify(5, mFingerprintIdentifyListener);
        } else {
            mRlFingerContainer.setVisibility(View.GONE);
            mFlPwdContainer.setVisibility(View.VISIBLE);
        }
    }

    private void shake(){
        ObjectAnimator animator = AnimatorUtils.tada(mIvFinger,5);
        animator.setRepeatCount(0);
        animator.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        mIsAutoLogin = SpfUtils.getBoolean(this, MyConstants.IS_AUTO_LOGIN, false);
        initFingerPrint();
    }

    // 初始化监听事件
    private void initEvent() {
        mLoginGo.setOnClickListener(this);
        //当手机输入法按下确认键的时候执行登录
        mLoginPwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0) {
                    login();
                }
                return false;
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBtnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch2Pwd();
            }
        });
        final int length = SpfUtils.getInt(LockActivity.this, MyConstants.LENGTH, 0);

        mLoginPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mIsAutoLogin) {
                    String pwd = mLoginPwd.getText().toString().trim();
                    if (pwd.length() == length) {
                        login();
                    }
                }
            }
        });
    }

    private void switch2Pwd() {
        mRlFingerContainer.animate()
                .alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRlFingerContainer.setVisibility(View.GONE);
                        mFlPwdContainer.setVisibility(View.VISIBLE);
                        mLoginPwd.requestFocus();
                    }
                }).setDuration(500).start();
    }

    // 初始化数据
    private void initData() {

    }

    // 登录
    private void login() {
        String password = PasswordManager.getInstance().getPassword();
        String inputPwd = mLoginPwd.getText().toString().trim();
        if (TextUtils.equals(password, inputPwd)) {
//            ToastUtils.showTasty(LockActivity.this,"登录成功", TastyToast.SUCCESS);
            finish();
        } else {
            ToastUtils.showTasty(this, "密码错误", TastyToast.ERROR);
            mLoginPwd.setText("");
        }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_go:
                login();
                break;
        }
    }
}
