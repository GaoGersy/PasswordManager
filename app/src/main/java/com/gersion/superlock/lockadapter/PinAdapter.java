package com.gersion.superlock.lockadapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.utils.AnimatorUtils;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.Md5Utils;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.view.codeview.CodeView;
import com.gersion.superlock.view.codeview.KeyboardView;

/**
 * Created by aa326 on 2018/1/9.
 */

public class PinAdapter implements LockAdapter {

    private LockCallback mLockCallback;
    private CodeView mCodeView;
    private String mPassword;
    private int mLockMode = -1;

    public PinAdapter(int lockMode){
        mLockMode = lockMode;
    }

    CodeView.Listener mLockListener = new CodeView.Listener() {
        @Override
        public void onValueChanged(String value) {

        }

        @Override
        public void onComplete(String value) {
            handleResult(value);
        }
    };


    private void handleResult(String result) {
        switch (mLockMode) {
            case MyConstants.LockMode.MODE_INIT:
                onInit(result);
                break;
            case MyConstants.LockMode.MODE_LOCK:
                onLock(result);
                break;
            case MyConstants.LockMode.MODE_RESET:
                onReset(result);
                break;
            default:
                break;
        }
    }

    int step =0;
    private void onReset(String result) {
        if (step ==0) {
            String password = ConfigManager.getInstance().getAppPassword();
            String pwd = Md5Utils.encodeWithTimes(result, 2);
            if (TextUtils.equals(password, pwd)) {
                mCodeView.clear();
                mTvNotice.setText("输入新的密码");
                step++;
            }else {
                mLockCallback.onError("密码错误");
                mTvNotice.setText("密码错误");
                shake();
            }
        }else {
            onInit(result);
        }
    }

    private void onLock(String result) {
        String password = ConfigManager.getInstance().getAppPassword();
        String pwd = Md5Utils.encodeWithTimes(result, 2);
        if (TextUtils.equals(password, pwd)) {
            mLockCallback.onSuccess();
        } else {
            mCodeView.clear();
            mLockCallback.onError("密码错误");
            mTvNotice.setText("密码错误");
            shake();
        }
    }

    private void onInit(String result) {
        if (mPassword != null) {
            if (mPassword.equals(result)) {
                mLockCallback.onSuccess();
                mConfigManager.setAppPassword(result);
            } else {
                mCodeView.clear();
                mPassword=null;
                mLockCallback.onError("两次密码不一致");
                mTvNotice.setText("两次密码不一致");
                shake();
            }
        } else {
            mPassword = result;
            mCodeView.clear();
            mTvNotice.setText("重复上一次密码");
        }
    }

    private ConfigManager mConfigManager;
    private TextView mTvNotice;

    public View init(Context context) {
        mConfigManager = ConfigManager.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.view_code, null);
        mTvNotice = (TextView) view.findViewById(R.id.tv_notice);
        final KeyboardView keyboardView = (KeyboardView) view.findViewById(R.id.password_input);
        mCodeView = (CodeView) view.findViewById(R.id.password_view);
        mCodeView.setShowType(CodeView.SHOW_TYPE_PASSWORD);
        mCodeView.setLength(6);
        keyboardView.setCodeView(mCodeView);
        mCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardView.show();
            }
        });
        mCodeView.setListener(mLockListener);
        return view;
    }

    @Override
    public void onStart() {

    }

    private void shake() {
        ObjectAnimator animator = AnimatorUtils.nope(mCodeView);
        animator.setRepeatCount(0);
        animator.start();
    }

    @Override
    public void setLockCallback(LockCallback lockCallback) {
        mLockCallback = lockCallback;
    }
}
