package com.gersion.superlock.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseFragment;
import com.gersion.superlock.utils.AnimatorUtils;
import com.gersion.superlock.utils.ClipBoardUtils;
import com.gersion.superlock.utils.PasswordUtils;
import com.gersion.superlock.view.Croller;

/**
 * Created by a3266 on 2017/6/11.
 */

public class PasswordCreaterFragment extends BaseFragment implements View.OnClickListener {
    //是否是启动程序后第一次点击开始按钮的标志位
    boolean isFirst = true;
    private TextView mTvPassword;
    private ImageView mIvCapital;
    private ImageView mIvLower;
    private ImageView mIvNumber;
    private boolean mIsCapital = false;
    private boolean mIsLower = false;
    private boolean mIsNumber = false;
    private boolean mIsChar = false;
    private int mLength = 6;
    private ImageView mIvChars;
    private ImageView mIvStart;
    private MyHandler handler;
    private Croller mCroller;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_password_creater;
    }

    @Override
    protected void initView() {
        handler = new MyHandler();
        mTvPassword = (TextView) findView(R.id.activity_main_key);
        mIvCapital = (ImageView) findView(R.id.activity_main_btn_capital);
        mIvLower = (ImageView) findView(R.id.activity_main_btn_lower);
        mIvNumber = (ImageView) findView(R.id.activity_main_btn_number);
        mIvChars = (ImageView) findView(R.id.activity_main_btn_char);
        mIvStart = (ImageView) findView(R.id.activity_main_start);
        mCroller = findView(R.id.croller_pwd_length);
    }

    @Override
    protected void initData(Bundle bundle) {
        Typeface fontFace = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/source.ttf");
        mTvPassword.setTextSize(20);
        mTvPassword.setTypeface(fontFace);
        mIsLower = true;
    }

    @Override
    protected void initListener() {
        mIvCapital.setOnClickListener(this);
        mIvLower.setOnClickListener(this);
        mIvNumber.setOnClickListener(this);
        mIvChars.setOnClickListener(this);
        mIvStart.setOnClickListener(this);
        mTvPassword.setOnClickListener(this);
        mIvLower.setSelected(true);
        mCroller.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                int num = getTypeNum();
                //如果进度条的数字小于开启了的按钮的个数，就让进度条的值等于开启了的按钮的个数
                if (progress < num) {
                    mCroller.setProgress(num);
                    progress = num;
                }
                mLength = progress;
            }
        });
    }

    /**
     * 播放动画
     *
     * @author Gers
     * @time 2016/8/10 16:28
     */
    private void stopAnimator() {
        handler.stop();
        mIvStart.setEnabled(false);
        final ObjectAnimator animator = ObjectAnimator.ofFloat(mTvPassword, "translationY", 300f);
        animator.setDuration(1500);
        animator.setInterpolator(new BounceInterpolator());

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIvStart.setEnabled(true);
            }
        });

        shake(mTvPassword).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator.start();
            }
        });

    }

    private ObjectAnimator shake(View view) {
        ObjectAnimator animator = AnimatorUtils.nope(view);
        animator.setRepeatCount(0);
        animator.start();
        return animator;
    }

    /**
     * 播放动画
     *
     * @author Gers
     * @time 2016/8/10 16:28
     */
    private void playAnimator() {
        mIvStart.setEnabled(false);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvPassword, "translationY", 0);
        animator.setDuration(1000);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                handler.start();
                mIvStart.setEnabled(true);
            }
        });
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_btn_capital:
                mIsCapital = !mIsCapital;
                mIvCapital.setSelected(!mIvCapital.isSelected());
                setProgress(mIvCapital);
                checkButton();
                break;
            case R.id.activity_main_btn_lower:
                mIsLower = !mIsLower;
                mIvLower.setSelected(!mIvLower.isSelected());
                setProgress(mIvLower);
                checkButton();
                break;
            case R.id.activity_main_btn_number:
                mIsNumber = !mIsNumber;
                mIvNumber.setSelected(!mIvNumber.isSelected());
                setProgress(mIvNumber);
                checkButton();
                break;
            case R.id.activity_main_btn_char:
                mIsChar = !mIsChar;
                mIvChars.setSelected(!mIvChars.isSelected());
                setProgress(mIvChars);
                checkButton();
                break;
            case R.id.activity_main_start:
                //如果没有选择任何类型，就直接返回，不再执行下面的内容
                if (!(mIsCapital || mIsLower || mIsNumber || mIsChar)) {
                    Toast.makeText(getActivity(), "请至少选择一项密码包含的元素", Toast.LENGTH_SHORT).show();
                    return;
                }
                mIvStart.setSelected(!mIvStart.isSelected());
                if (isFirst) {
                    isFirst = false;
                    handler.start();
                    return;
                } else {
                    if (mIvStart.isSelected()) {
                        playAnimator();
                    } else {
                        stopAnimator();
                    }
                }
                break;
            case R.id.activity_main_key:
                //将生成的密码复制到剪贴板
                ClipBoardUtils.copy(getActivity(), mTvPassword.getText().toString().trim());
                Toast.makeText(getActivity(), "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    //如果开始按钮是选中的状态，所有的选择类型的按钮都为非选择状态，就停止，并将开始按钮置为非选择状态
    public void checkButton() {

        if (!(mIsCapital || mIsLower || mIsNumber || mIsChar) && mIvStart.isSelected()) {
            mIvStart.setSelected(false);
            stopAnimator();
        }
    }

    //当某个按钮被点击时，如果当前进度条上的数字小于当前被选择的条目的数量，就让进度条加1
    private void setProgress(View v) {
        if (v.isSelected() && (mCroller.getProgress() < getTypeNum())) {
            mCroller.setProgress(mCroller.getProgress() + 1);
        }
    }

    //获取所有的已经选择了的按钮的个数
    private int getTypeNum() {
        int count = 0;
        if (mIsNumber) {
            count++;
        }
        if (mIsCapital) {
            count++;
        }
        if (mIsChar) {
            count++;
        }
        if (mIsLower) {
            count++;
        }
        return count;
    }

    //生成新的密码
    private String getPassword() {

        return PasswordUtils.getNewPassword(mIsCapital, mIsLower, mIsNumber, mIsChar, mLength);
    }

    class MyHandler extends Handler implements Runnable {

        @Override
        public void run() {
            mTvPassword.setText(getPassword());
            postDelayed(this, 100);
        }

        public void start() {
            postDelayed(this, 100);
        }

        public void stop() {
            removeCallbacks(this);
        }

    }
}
