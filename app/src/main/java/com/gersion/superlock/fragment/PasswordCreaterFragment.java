package com.gersion.superlock.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseFragment;
import com.gersion.superlock.utils.AnimatorUtils;
import com.gersion.superlock.utils.ClipBoardUtils;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.PasswordUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.Croller;
import com.gersion.superlock.view.TouchView;

/**
 * Created by a3266 on 2017/6/11.
 */

public class PasswordCreaterFragment extends BaseFragment implements View.OnClickListener {
    //是否是启动程序后第一次点击开始按钮的标志位
    boolean isFirst = true;
    private TextView mTvPassword;
    private boolean mIsCapital = false;
    private boolean mIsLower = false;
    private boolean mIsNumber = false;
    private boolean mIsChar = false;
    private int mLength = 6;
    private MyHandler handler;
    private Croller mCroller;
    private CheckBox mCbSmall;
    private CheckBox mCbBig;
    private CheckBox mCbNum;
    private CheckBox mCbSuper;
    private TouchView mTouchView;
    private TextView mTvConfirm;
    private boolean mIsSuperPassword;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_password_creater;
    }

    @Override
    protected void initView() {
        handler = new MyHandler();
        mTvPassword = findView(R.id.activity_main_key);
        mCbSmall = findView(R.id.cb_small);
        mCbBig = findView(R.id.cb_big);
        mCbNum = findView(R.id.cb_num);
        mCbSuper = findView(R.id.cb_super);
        mCroller = findView(R.id.croller_pwd_length);
        mTouchView = findView(R.id.touchView);
        mTvConfirm = findView(R.id.tv_confirm);

        mCbSmall.setSelected(true);
        mTouchView.setCanTouch(false);
        initSuperPassword();
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
        mCbSmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsLower = isChecked;
                setProgress(isChecked);
                checkButton();
            }
        });
        mCbBig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsCapital = isChecked;
                setProgress(isChecked);
                checkButton();
            }
        });
        mCbNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsNumber = isChecked;
                setProgress(isChecked);
                checkButton();
            }
        });
        mCbSuper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsChar = isChecked;
                setProgress(isChecked);
                checkButton();
            }
        });

        mTvPassword.setOnClickListener(this);
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

        mTouchView.setOnPressListener(new TouchView.OnPressListener() {
            @Override
            public void onPress() {
                onStartCreate();
            }

            @Override
            public void onRelease() {
                onStopCreate();
            }
        });

        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mTvPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.show(activity, "点击指纹按钮成一个密码吧");
                    return;
                }
                ConfigManager.getInstance().setSuperPassword(password);
                activity.setResult(Activity.RESULT_OK);
                activity.finish();
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
        mTouchView.setCanTouch(false);
        final ObjectAnimator animator = ObjectAnimator.ofFloat(mTvPassword, "translationY", 300f);
        animator.setDuration(1000);
        animator.setInterpolator(new BounceInterpolator());

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mTouchView.setCanTouch(true);
            }
        });

        shake(mTvPassword).addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator.start();
            }
        });

    }

    /**
     * 播放动画
     *
     * @author Gers
     * @time 2016/8/10 16:28
     */
//    private void playAnimator() {
//        mTouchView.setCanTouch(false);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvPassword, "translationY", 0);
//        animator.setDuration(300);
//        animator.setInterpolator(new OvershootInterpolator());
//        animator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mTouchView.setCanTouch(true);
//                handler.start();
//            }
//        });
//        animator.start();
//    }

    private ObjectAnimator shake(View view) {
        ObjectAnimator animator = AnimatorUtils.nope(view);
        animator.setRepeatCount(0);
        animator.start();
        return animator;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_key:
                //将生成的密码复制到剪贴板
                ClipBoardUtils.copy(getActivity(), mTvPassword.getText().toString().trim());
                Toast.makeText(getActivity(), "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void isSuperPassword(boolean isSuperPassword){
        mIsSuperPassword = isSuperPassword;
    }

    private void initSuperPassword() {
        if (mIsSuperPassword) {
            mTvConfirm.setVisibility(View.VISIBLE);
            mCroller.setProgress(20);
            mCbBig.setChecked(true);
            mCbSmall.setChecked(true);
            mCbNum.setChecked(true);
            mCbSuper.setChecked(true);
            mIsCapital = true;
            mIsChar = true;
            mIsLower = true;
            mIsNumber = true;
        }
    }

    private void onStartCreate() {
        if (!(mIsCapital || mIsLower || mIsNumber || mIsChar)) {
            Toast.makeText(getActivity(), "请至少选择一项密码包含的元素", Toast.LENGTH_SHORT).show();
            return;
        }
        handler.start();
//        if (isFirst) {
//            isFirst = false;
//            handler.start();
//            return;
//        } else {
//            playAnimator();
//        }
    }

    private void onStopCreate() {
//        stopAnimator();
        handler.stop();
    }

    //如果开始按钮是选中的状态，所有的选择类型的按钮都为非选择状态，就停止，并将开始按钮置为非选择状态
    public void checkButton() {
        if (!(mIsCapital || mIsLower || mIsNumber || mIsChar) && mTouchView.isSelected()) {
            mTouchView.setSelected(false);
            stopAnimator();
        }
    }

    //当某个按钮被点击时，如果当前进度条上的数字小于当前被选择的条目的数量，就让进度条加1
    private void setProgress(boolean selected) {
        if (selected && (mCroller.getProgress() < getTypeNum())) {
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

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(handler);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(handler);
    }
}
