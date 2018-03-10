package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.PasswordDetailTitleAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.view.TitleView;
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
    private PasswordDetailTitleAdapter mDetailAdapter;
    private boolean mIsTitleScroll;//是否是滑动的标题

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
//            itemPicker.addOnItemChangedListener();
        mAdapter = new PasswordDetailTitleAdapter();
        mAdapter.registerMultiBean(ItemBean.class, R.layout.item_password_title);
//            mAdapter.registerMultiLayout(R.layout.item_detail_del);
//            infiniteAdapter = InfiniteScrollAdapter.wrap(mAdapter);
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
//                mTvName.setText(mList.get(adapterPosition).getAddress());

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
//                int i = currentPosition - newPosition;
//                if (i > 0) {
//                    toRight(mTvName);
//                } else {
//                    toLeft(mTvName);
//                }

            }
        });
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.setName("haha" + i);
            itemBean.setAddress("xixi" + i);
            mList.add(itemBean);
        }
        mAdapter.setItems(mList);

        mDetailDiscreteScrollView.setOrientation(Orientation.HORIZONTAL);
//            itemPicker.addOnItemChangedListener();
        mDetailAdapter = new PasswordDetailTitleAdapter();
        mDetailAdapter.registerMultiBean(ItemBean.class, R.layout.item_password_detail);
//            mAdapter.registerMultiLayout(R.layout.item_detail_del);
//            infiniteAdapter = InfiniteScrollAdapter.wrap(mAdapter);
        mDetailDiscreteScrollView.setAdapter(mDetailAdapter);
        mDetailDiscreteScrollView.setItemTransitionTimeMillis(100);
        mDetailDiscreteScrollView.setOffscreenItems(0);
        mDetailDiscreteScrollView.setItemTransformer(new ScaleTransformer.Builder().setMaxScale(1).setMinScale(1).build());
        mDetailAdapter.setItems(mList);

        mDetailDiscreteScrollView.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
//                mTvName.setText(mList.get(adapterPosition).getAddress());
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
//                int i = currentPosition - newPosition;
//                if (i > 0) {
//                    toRight(mTvName);
//                } else {
//                    toLeft(mTvName);
//                }

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

    }

    @Override
    protected void initListener() {
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
