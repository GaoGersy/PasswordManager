package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingmouren.paletteimageview.PaletteImageView;
import com.gersion.superlock.R;
import com.gersion.superlock.adapter.HistoryDetialAdapter;
import com.gersion.superlock.animator.EasyTransition;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.view.TitleView;
import com.gersion.superlock.view.smartRecycleView.SmartRecycleView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.v_top_card)
    View mVTopCard;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_del)
    ImageView mIvDel;
    @BindView(R.id.smartRecycleView)
    SmartRecycleView mSmartRecycleView;
    @BindView(R.id.layout_about)
    LinearLayout mLayoutAbout;
    @BindView(R.id.piv_icon)
    PaletteImageView mPivIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_password)
    TextView mTvPassword;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.titleView)
    TitleView mTitleView;
    private LinearLayout layoutAbout;
    private boolean finishEnter;
    private long mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initSmartRecycler();
        initData();
        long transitionDuration = 500;

        finishEnter = false;
        EasyTransition.enter(
                this,
                transitionDuration,
                new DecelerateInterpolator(),
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        finishEnter = true;
                        initOtherViews();
                    }
                });
        mTitleView.setTitleText("密码详情");
        mTitleView.setSearchVisiable(false);
        mTitleView.setAddVisiable(false);
    }

    private void initSmartRecycler() {
        HistoryDetialAdapter adapter = new HistoryDetialAdapter(null);
        mSmartRecycleView
                .setAutoRefresh(false)
                .setAdapter(adapter)
                .loadMoreEnable(false)
                .refreshEnable(false)
                .setLayoutManger(SmartRecycleView.LayoutManagerType.LINEAR_LAYOUT);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mId = intent.getLongExtra("id", -1);
        if (mId != -1) {
            DbBean dbBean = DbManager.getInstance().queryById(mId);
            updateViews(dbBean);
        }
    }

    @Override
    protected void initListener() {
        mIvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbManager.getInstance().deleteById(mId);
                showToast("删除成功");
                finish();
            }
        });
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTitleView.setOnAddListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(AddPasswordActivity.class);
            }
        });
    }

    private void updateViews(DbBean dbBean) {
        mTvTitle.setText(dbBean.getAddress());
        mTvName.setText("用户名：" + dbBean.getName());
        mTvPassword.setText("密  码：" + dbBean.getPwd());
//        mSmartRecycleView.handleData(dbBean.getUpdateHistorys());
    }

    public static Intent getDetailIntent(Activity activity, long id) {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra("id", id);
        return intent;
    }

    private void initOtherViews() {
        layoutAbout = (LinearLayout) findViewById(R.id.layout_about);
        layoutAbout.setVisibility(View.VISIBLE);
        layoutAbout.setAlpha(0);
        layoutAbout.setTranslationY(-30);
        layoutAbout.animate()
                .setDuration(300)
                .alpha(1)
                .translationY(0);

        mIvDel.setVisibility(View.VISIBLE);
        mIvDel.setScaleX(0);
        mIvDel.setScaleY(0);
        mIvDel.animate()
                .setDuration(200)
                .scaleX(1)
                .scaleY(1);
    }

    @Override
    public void onBackPressed() {
        if (finishEnter) {
            finishEnter = false;
            startBackAnim();
        }
    }

    private void startBackAnim() {
        mIvDel.animate()
                .setDuration(200)
                .scaleX(0)
                .scaleY(0);

        layoutAbout.animate()
                .setDuration(300)
                .alpha(0)
                .translationY(-30)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // transition exit after our anim
                        EasyTransition.exit(DetailActivity.this, 500, new DecelerateInterpolator());
                    }
                });
    }

}
