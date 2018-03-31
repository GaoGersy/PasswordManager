package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.PasswordDetailAdapter;
import com.gersion.superlock.adapter.PasswordDetailTitleAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.db.BaseDbManager;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.view.TitleView;
import com.orhanobut.logger.Logger;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasswordDetailActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView mTitleView;
    @BindView(R.id.title_discreteScrollView)
    DiscreteScrollView mTitleDiscreteScrollView;
    @BindView(R.id.detail_discreteScrollView)
    DiscreteScrollView mDetailDiscreteScrollView;
    @BindView(R.id.tv_name)
    TextView mTvName;
    private PasswordDetailTitleAdapter mAdapter;
    private List<ItemBean> mList;
    private PasswordDetailAdapter mDetailAdapter;
    private boolean mIsTitleScroll;//是否是滑动的标题
    private List<ItemBean> mDataList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTitleView.setTitleText("密码详情")
                .setAddVisiable(false)
                .setBackVisiable(true)
                .setSearchVisiable(false);
        mTitleDiscreteScrollView.setOrientation(Orientation.HORIZONTAL);
        mAdapter = new PasswordDetailTitleAdapter();
        mAdapter.registerMultiBean(PasswordData.class, R.layout.item_password_title);
        mTitleDiscreteScrollView.setAdapter(mAdapter);
        mTitleDiscreteScrollView.setItemTransitionTimeMillis(100);
        mTitleDiscreteScrollView.setOffscreenItems(0);
        mTitleDiscreteScrollView.setItemTransformer(new ScaleTransformer
                .Builder()
                .setMinScale(0.7f)
                .build());
        mTitleDiscreteScrollView.setSlideOnFling(true);
        mTitleDiscreteScrollView.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                mIsTitleScroll = true;
            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                if (mIsTitleScroll) {
                    mDetailDiscreteScrollView.smoothScrollToPosition(adapterPosition);
                }
            }

            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
            }
        });
//        mList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ItemBean itemBean = new ItemBean();
//            itemBean.setName("haha" + i);
//            itemBean.setAddress("xixi" + i);
//            mList.add(itemBean);
//        }
//        mAdapter.setItems(mList);

        mDetailDiscreteScrollView.setOrientation(Orientation.HORIZONTAL);
        mDetailAdapter = new PasswordDetailAdapter();
        mDetailAdapter.registerMultiBean(PasswordData.class, R.layout.item_password_detail);
        mDetailDiscreteScrollView.setAdapter(mDetailAdapter);
        mDetailDiscreteScrollView.setItemTransitionTimeMillis(100);
        mDetailDiscreteScrollView.setOffscreenItems(0);
        mDetailDiscreteScrollView.setItemTransformer(new ScaleTransformer.Builder().setMaxScale(1).setMinScale(1).build());
//        mDetailAdapter.setItems(mList);

        mDetailDiscreteScrollView.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                mIsTitleScroll = false;
            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                if (!mIsTitleScroll) {
                    mTitleDiscreteScrollView.smoothScrollToPosition(adapterPosition);
                }
            }

            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
            }
        });

    }

    private void toRight(final View view) {
        view.animate()
                .translationX(view.getWidth())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        view.setVisibility(View.GONE);
                        toRightSecond(view);
                    }
                })
                .start();
    }

    private void toRightSecond(final View view) {
        view.setX(-view.getWidth());
        view.setVisibility(View.VISIBLE);
        view.animate()
                .translationX(0)
                .setDuration(300)
                .setInterpolator(new AnticipateOvershootInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }
                })
                .start();
    }

    private void toLeft(final View view) {
        view.animate()
                .translationX(-view.getWidth())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        view.setVisibility(View.GONE);
                        toLeftSecond(view);
                    }
                })
                .start();
    }

    private void toLeftSecond(final View view) {
        view.setX(view.getWidth());
//        view.setVisibility(View.VISIBLE);
        view.animate()
                .translationX(0)
                .setDuration(300)
                .setInterpolator(new AnticipateOvershootInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                })
                .start();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        long id = bundle.getLong("id");
        Logger.e(id+"");
        List<PasswordData> passwordBeans = DbManager.getInstance().queryAll();
        int position = 0;
        if (passwordBeans != null && passwordBeans.size() > 0) {
            mDataList = new ArrayList();
            for (int i = 0; i < passwordBeans.size(); i++) {
                PasswordData passwordBean = passwordBeans.get(i);
                ItemBean itemBean = ItemBean.DbBean2ItemBean(passwordBean);
                mDataList.add(itemBean);
                Logger.e(passwordBean.getId()+"");
                if (id == passwordBean.getId()) {
                    position = i;
                }
            }
        }
        mAdapter.setItems(passwordBeans);
        mDetailAdapter.setItems(passwordBeans);
        Logger.e(position + "");
        mTitleDiscreteScrollView.scrollToPosition(position);
        mDetailDiscreteScrollView.scrollToPosition(position);
    }

    @Override
    protected void initListener() {
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DbManager.getInstance().registerDataChangeListener(dataChangeListener);
    }

    DbManager.OnDataChangeCallback<PasswordData> dataChangeListener = new BaseDbManager.OnDataChangeCallback<PasswordData>() {
        @Override
        public void onDataChange(List<PasswordData> list) {
//            mDataList.clear();
//            mDataList.addAll(list);
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
