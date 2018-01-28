package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.fragment.HomeFragment;
import com.gersion.superlock.utils.AnimatorUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.TitleView;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.cetv_location)
    EditText mCetvLocation;
    @BindView(R.id.cetv_name)
    EditText mCetvName;
    @BindView(R.id.cetv_password)
    EditText mCetvPassword;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.et_notes)
    EditText mEtNotes;
    @BindView(R.id.selector)
    ImageView mSelector;
    @BindView(R.id.titleView)
    TitleView mTitleView;
    @BindView(R.id.activity_add_password)
    LinearLayout mActivityAddPassword;
    private int mTotalCount;

    CharSequence[] words = {
            "自定义",
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

    public static void startIntent(Activity activity, int totalCount) {
        Intent intent = new Intent(activity, AddPasswordActivity.class);
        intent.putExtra(HomeFragment.TOTAL_COUNT, totalCount);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_password;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleView.setTitleText("添加密码")
                .setSearchVisiable(false)
                .setAddVisiable(false);

        mCetvLocation.requestFocus();
    }

    @Override
    protected void initData() {
        mTotalCount = HomeFragment.mTotalCount;
        mTotalCount--;
    }

    @Override
    protected void initListener() {
        mTvCommit.setOnClickListener(this);
        mSelector.setOnClickListener(this);
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                addItem();

                break;
            case R.id.selector:
                StyledDialog.buildMdSingleChoose(this, "选择密码位置", 0, words, new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        String text = charSequence.toString();
                        if (text.equals("未知")) {
                            text = "";
                        }
                        mCetvLocation.setText(text);
                        mCetvLocation.setSelection(text.length());
                    }
                }).show();
                break;
        }
    }

    private void addItem() {
        String pwd = mCetvPassword.getText().toString().trim();
        String name = mCetvName.getText().toString().trim();
        String location = mCetvLocation.getText().toString().trim();
        String notes = mEtNotes.getText().toString().trim();
        if (!(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(name) || TextUtils.isEmpty(location))) {
            scaleAnimator();
            DbBean dbBean = new DbBean();
            dbBean.setAddress(location);
            dbBean.setCreateTime(System.currentTimeMillis());
            dbBean.setName(name);
            dbBean.setNotes(notes);
            dbBean.setPwd(pwd);
            mTotalCount++;
            dbBean.setIndex(mTotalCount);
            dbBean.setId(mTotalCount);
            DbManager.getInstance().add(dbBean);
            ToastUtils.showTasty(AddPasswordActivity.this, "添加成功", TastyToast.SUCCESS);
            finish();
//            mCetvLocation.setText("");
//            mCetvName.setText("");
//            mCetvPassword.setText("");
//            mEtNotes.setText("");
        } else {
            shakeAnimator();
        }
    }

    private void shakeAnimator() {
        ObjectAnimator animator = AnimatorUtils.nope(mTvCommit);
        animator.setRepeatCount(0);
        animator.start();
        ToastUtils.showTasty(this, "信息不能为空", TastyToast.WARNING);
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
}
