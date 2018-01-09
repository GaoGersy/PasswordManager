package com.gersion.superlock.lockadapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.gersion.superlock.R;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.MyConstants;

import java.util.List;

/**
 * Created by aa326 on 2018/1/9.
 */

public class PatternLockAdapter implements LockAdapter {

    private PatternLockView mPatternLockView;
    private LockCallback mLockCallback;
    private ConfigManager mConfigManager;
    private String patternString;
    private TextView mTvNotice;
    private int mLockMode = -1;

    public PatternLockAdapter(int lockMode){
        mLockMode = lockMode;
    }

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            mTvNotice.setText("绘制解锁图案");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            String result = PatternLockUtils.patternToString(mPatternLockView, pattern);
            handleResult(result);
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    private void handleResult(String result) {
        switch (mLockMode){
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

    private void onLock(String result) {
        if (result.equals(mConfigManager.getPatternString())) {
            mLockCallback.onSuccess();
        } else {
            mLockCallback.onError("解锁失败");
            mTvNotice.setText("解锁失败");
        }
    }

    int step = 0;
    private void onReset(String result) {
        if (step ==0) {
            String password = mConfigManager.getPatternString();
            if (TextUtils.equals(password, result)) {
                mPatternLockView.clearPattern();
                mTvNotice.setText("绘制新的解锁图案");
                patternString = null;
                step++;
            }else {
                mTvNotice.setText("图案不正确");
            }
        }else {
            onInit(result);
        }
    }


    private void onInit(String result) {
        if (patternString != null) {
            if (patternString.equals(result)) {
                mLockCallback.onSuccess();
                mConfigManager.setPatternString(result);
            } else {
                mLockCallback.onError("两次图案不一致");
            }
        } else {
            patternString = result;
            mPatternLockView.clearPattern();
            mTvNotice.setText("重复上一次图案");
        }
    }

    public View init(Context context) {
        mConfigManager = ConfigManager.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.view_pattern, null);
        mPatternLockView = (PatternLockView) view.findViewById(R.id.patter_lock_view);
        mTvNotice = (TextView) view.findViewById(R.id.tv_notice);
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(context, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(context, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(context, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(context, R.color.colorAccent));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(true);
        mPatternLockView.setInputEnabled(true);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
        return view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void setLockCallback(LockCallback lockCallback) {
        mLockCallback = lockCallback;
    }
}
