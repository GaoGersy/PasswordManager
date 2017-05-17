package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.dao.PasswordDao;
import com.gersion.superlock.fragment.HomeFragment;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.utils.UIUtils;
import com.gersion.toastlibrary.TastyToast;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AddPasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.cetv_location)
    EditText mCetvLocation;
    @BindView(R.id.cetv_name)
    EditText mCetvName;
    @BindView(R.id.cetv_password)
    EditText mCetvPassword;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.tv_noteKey)
    TextView mTvNoteKey;
    @BindView(R.id.cv_notes)
    CardView mCvNotes;
    @BindView(R.id.et_notes)
    TextInputEditText mEtNotes;
    @BindView(R.id.activity_add_password)
    FrameLayout mActivityAddPassword;
    @BindView(R.id.cv_info)
    CardView mCvInfo;
    @BindView(R.id.selector)
    TextView mSelector;
    private boolean isOpen = false;
    private String mTotalCount;
    private int mDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    //初始化控件
    private void initView() {
        mCetvLocation.requestFocus();
    }

    //初始化数据
    private void initData() {
        mDistance = UIUtils.dp2Px(40);
        Intent intent = getIntent();
        mTotalCount = intent.getStringExtra(HomeFragment.TOTAL_COUNT);
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

    private void commitAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvCommit, "alpha", 100f);
        animator.setDuration(2000);
        animator.start();
    }

    //初始化监听事件
    private void initEvent() {
        mTvCommit.setOnClickListener(this);
        mTvNoteKey.setOnClickListener(this);
        mSelector.setOnClickListener(this);
//        mEtNotes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, final boolean hasFocus) {
//                //添加布局改变的监听
//                mActivityAddPassword.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        //移除监听
//                        mActivityAddPassword.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        if (hasFocus){
//                            //移动列表到最后一行
//                            ViewCompat.animate(mCvInfo).translationY(-mTvCommit.getHeight()).setDuration(500).start();
//                        }else{
//                            ViewCompat.animate(mCvInfo).translationY(0).setDuration(500).start();
//                        }
//                    }
//                });
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                final String pwd = mCetvPassword.getText().toString().trim();
                final String name = mCetvName.getText().toString().trim();
                final String location = mCetvLocation.getText().toString().trim();
                final String notes = mEtNotes.getText().toString().trim();
                if (!(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(name) || TextUtils.isEmpty(location))) {
                    scaleAnimator();
                    final PasswordDao dao = new PasswordDao(this);
                    Observable.just("a")
                            .map(new Func1<String, Boolean>() {
                                @Override
                                public Boolean call(String s) {
                                    return dao.add(location, name, pwd, notes, mTotalCount);
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Boolean>() {
                                @Override
                                public void call(Boolean aBoolean) {
                                    if (aBoolean) {
                                        ToastUtils.showTasty(AddPasswordActivity.this, "添加成功", TastyToast.SUCCESS);
                                        mCetvLocation.setText("");
                                        mCetvName.setText("");
                                        mCetvPassword.setText("");
                                        mEtNotes.setText("");
                                    }
                                }
                            });
                } else {
                    shakeAnimator();
                }

                break;
            case R.id.tv_noteKey:
                if (isOpen) {
                    closeNotes();
                } else {
                    openNotes();
                }
                break;
            case R.id.selector:
                CharSequence[] words = {
                        "未知",
                        "腾讯",
                        "淘宝",
                        "百度",
                        "京东",
                        "微信",
                        "小米",
                        "美团",
                        "人人网",
                        "网易",
                        "新浪"
                };
                StyledDialog.buildMdSingleChoose(this, "选择密码位置", 0, words, new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        String text = charSequence.toString();
                        if(text.equals("未知")){
                            text = "";
                        }
                        mCetvLocation.setText(text);
                        mCetvLocation.setSelection(text.length());
                    }
                }).show();
                break;
        }
    }

    private void shakeAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvCommit, "translationX", 0, 15);
        animator.setRepeatCount(3);
        animator.setDuration(20);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
        ToastUtils.showTasty(this,"信息不能为空",TastyToast.WARNING);
    }


    private void scaleAnimator() {
//        ViewCompat.animate(holderView).scaleX(1).scaleY(1).setDuration(400).setInterpolator(new OvershootInterpolator(4)).start();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mTvCommit, "scaleX", 1.0f, 1.5f, 1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mTvCommit, "scaleY", 1.0f, 1.5f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new OvershootInterpolator(2));
        animatorSet.start();
    }


    private void openNotes() {
        isOpen = true;
        mTvNoteKey.setText("密码信息");
        ObjectAnimator animatorNotes = ObjectAnimator.ofFloat(mCvNotes, "translationY", mDistance);
        ObjectAnimator animatorInfo = ObjectAnimator.ofFloat(mCvInfo, "translationY",  -mCvInfo.getHeight()+mDistance);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorInfo,animatorNotes);
        set.setDuration(500);
        set.setInterpolator(new OvershootInterpolator());
        set.start();
        mEtNotes.requestFocus();
    }

    private void closeNotes() {
        isOpen = false;
        mTvNoteKey.setText("备注");
        ObjectAnimator animatorNotes = ObjectAnimator.ofFloat(mCvNotes, "translationY", 0);
        ObjectAnimator animatorInfo = ObjectAnimator.ofFloat(mCvInfo, "translationY",  0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorInfo,animatorNotes);
        set.setDuration(500);
        set.setInterpolator(new AnticipateInterpolator());
        set.start();
        mCetvPassword.requestFocus();
    }

}
