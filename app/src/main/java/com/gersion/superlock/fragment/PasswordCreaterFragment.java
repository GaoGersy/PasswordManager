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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseFragment;
import com.gersion.superlock.utils.ClipBoardUtils;
import com.gersion.superlock.utils.PasswordUtils;

/**
 * Created by a3266 on 2017/6/11.
 */

public class PasswordCreaterFragment extends BaseFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    //是否是启动程序后第一次点击开始按钮的标志位
    boolean isFirst = true;
    private TextView key;
    private ImageView capital;
    private ImageView lower;
    private ImageView number;
    private boolean isCapital = false;
    private boolean isLower = false;
    private boolean isNumber = false;
    private boolean isChar = false;
    private int length = 6;
    private ImageView chars;
    private ImageView start;
    private MyHandler handler;
    private SeekBar seekBar;
    private TextView size;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_password_creater;
    }

    @Override
    protected void initView() {
        handler = new MyHandler();
        key = (TextView) findView(R.id.activity_main_key);
        capital = (ImageView) findView(R.id.activity_main_btn_capital);
        lower = (ImageView) findView(R.id.activity_main_btn_lower);
        number = (ImageView) findView(R.id.activity_main_btn_number);
        chars = (ImageView) findView(R.id.activity_main_btn_char);
        start = (ImageView) findView(R.id.activity_main_start);
        seekBar = (SeekBar) findView(R.id.activity_main_btn_seekBar);
        size = (TextView) findView(R.id.activity_main_size);
    }

    @Override
    protected void initData(Bundle bundle) {
//设置字体
        Typeface fontFace = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/source.ttf");
        key.setTextSize(20);
        key.setTypeface(fontFace);

        seekBar.setMax(20);
        seekBar.setProgress(6);
        size.setText("密码位数：" + 6 + "位");
    }

    @Override
    protected void initListener() {
        capital.setOnClickListener(this);
        lower.setOnClickListener(this);
        number.setOnClickListener(this);
        chars.setOnClickListener(this);
        start.setOnClickListener(this);
        key.setOnClickListener(this);
        lower.setSelected(true);
        isLower = true;
        seekBar.setOnSeekBarChangeListener(this);
    }

    /**
     * 播放动画
     *
     * @author Gers
     * @time 2016/8/10 16:28
     */
    private void stopAnimator() {
        handler.stop();
        final ObjectAnimator animator = ObjectAnimator.ofFloat(key, "translationY", 500f);
        animator.setDuration(1500);
        animator.setInterpolator(new BounceInterpolator());

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                start.setEnabled(true);
            }
        });
        //        animator.start();
//        Animation animation = AnimationUtils.loadAnimation(
//                this, R.anim.shake);
//        key.startAnimation(animation);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                start.setEnabled(false);
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                animator.start();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

    }

    /**
     * 播放动画
     *
     * @author Gers
     * @time 2016/8/10 16:28
     */
    private void playAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(key, "translationY", 0);
        animator.setDuration(1000);
        animator.setInterpolator(new OvershootInterpolator());

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                start.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                handler.start();
                start.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_btn_capital:
                isCapital = !isCapital;
                capital.setSelected(!capital.isSelected());
                setProgress(capital);
                checkButton();
                break;
            case R.id.activity_main_btn_lower:
                isLower = !isLower;
                lower.setSelected(!lower.isSelected());
                setProgress(lower);
                checkButton();
                break;
            case R.id.activity_main_btn_number:
                isNumber = !isNumber;
                number.setSelected(!number.isSelected());
                setProgress(number);
                checkButton();
                break;
            case R.id.activity_main_btn_char:
                isChar = !isChar;
                chars.setSelected(!chars.isSelected());
                setProgress(chars);
                checkButton();
                break;
            case R.id.activity_main_start:
                //如果没有选择任何类型，就直接返回，不再执行下面的内容
                if (!(isCapital || isLower || isNumber || isChar)) {
                    Toast.makeText(getActivity(), "请至少选择一项密码包含的元素", Toast.LENGTH_SHORT).show();
                    return;
                }
                start.setSelected(!start.isSelected());
                //如果是第一次点击，就只开始生成密码，并将标志位置为false;
                if (isFirst) {
                    isFirst = false;
                    handler.start();
                    return;
                }
                //根据按钮的不同准确准确状态进行相应的动画
                if (start.isSelected()) {

                    playAnimator();
                } else {

                    stopAnimator();
                }

                break;
            case R.id.activity_main_key:
                //将生成的密码复制到剪贴板
                ClipBoardUtils.copy(getActivity(), key.getText().toString().trim());
                Toast.makeText(getActivity(), "已复制到剪贴板", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    //如果开始按钮是选中的状态，所有的选择类型的按钮都为非选择状态，就停止，并将开始按钮置为非选择状态
    public void checkButton() {

        if (!(isCapital || isLower || isNumber || isChar) && start.isSelected()) {
            start.setSelected(false);
            stopAnimator();
        }
    }

    //当某个按钮被点击时，如果当前进度条上的数字小于当前被选择的条目的数量，就让进度条加1
    private void setProgress(View v) {
        if (v.isSelected() && (seekBar.getProgress() < getTypeNum())) {
            seekBar.setProgress(seekBar.getProgress() + 1);
        }
    }

    //获取所有的已经选择了的按钮的个数
    private int getTypeNum() {
        int count = 0;
        if (isNumber) {
            count++;
        }
        if (isCapital) {
            count++;
        }
        if (isChar) {
            count++;
        }
        if (isLower) {
            count++;
        }
        return count;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //设置进度条的最小值为1
        if (progress < 1) {
            seekBar.setProgress(1);
            progress = 1;
        }
        int num = getTypeNum();
        //如果进度条的数字小于开启了的按钮的个数，就让进度条的值等于开启了的按钮的个数
        if (progress < num) {
            seekBar.setProgress(num);
            progress = num;
        }
        length = progress;
        size.setText("密码位数：" + progress + "位");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    //生成新的密码
    private String getPassword() {

        return PasswordUtils.getNewPassword(isCapital, isLower, isNumber, isChar, length);
    }

    class MyHandler extends Handler implements Runnable {

        @Override
        public void run() {
            //将key的内容设置成当前生成的新密码显示到界面上
            key.setText(getPassword());
            //递归每隔100毫秒重复执行run()里面的内容
            postDelayed(this, 100);
        }

        public void start() {
            //开始执行run()方法
            postDelayed(this, 100);


        }

        public void stop() {
            //移除当前执行的对象，也就是停止了run（）方法的重复执行
            removeCallbacks(this);
        }

    }
}
