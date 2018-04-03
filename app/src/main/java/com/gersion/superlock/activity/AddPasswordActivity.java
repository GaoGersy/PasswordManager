package com.gersion.superlock.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.ExtraItemAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.ExtraOptionBean;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.bean.UpdateData;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.db.UpdateManager;
import com.gersion.superlock.utils.AnimatorUtils;
import com.gersion.superlock.utils.GsonHelper;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.TitleView;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tv_add_option)
    TextView mTvAddOption;
    @BindView(R.id.extra_RecyclerView)
    SwipeMenuRecyclerView mExtraRecyclerView;
    @BindView(R.id.iv_select_icon)
    ImageView mIvSelectIcon;
    private boolean mIsEdit;

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
    private ItemBean mItemBean;
    private ExtraItemAdapter mExtraItemAdapter;

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
        mExtraRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mExtraRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mExtraRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExtraItemAdapter = new ExtraItemAdapter();
        mExtraItemAdapter.registerMultiBean(ExtraOptionBean.class, R.layout.view_extra);
        mExtraRecyclerView.setAdapter(mExtraItemAdapter);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mItemBean = (ItemBean) bundle.getSerializable("itemBean");
            if (mItemBean != null) {
                mTitleView.setTitleText("修改密码");
                mCetvLocation.setText(mItemBean.getAddress());
                mCetvName.setText(mItemBean.getName());
                mEtNotes.setText(mItemBean.getNotes());
                mCetvPassword.setText(mItemBean.getPwd());
                mIsEdit = true;
                String extraOptions = mItemBean.getExtraOptions();
                if (extraOptions != null) {
                    List<ExtraOptionBean> extraOptionBeans = GsonHelper.toList(extraOptions, ExtraOptionBean.class);
                    if (extraOptionBeans != null) {
                        mExtraItemAdapter.addAll(extraOptionBeans);
                    }
                }
            }
        }
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
        mTvAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExtraItem();
            }
        });
        mIvSelectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (mItemBean!=null) {
                    Integer icon = mItemBean.getIcon();
                    if (icon != null) {
                        bundle.putInt("resourceId", icon);
                    }
                }
                toActivityForResult(SelectIconActivity.class,bundle,100);
            }
        });
    }

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.extra_height);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = getResources().getDimensionPixelSize(R.dimen.extra_height);
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(AddPasswordActivity.this)
                        .setBackground(R.color.red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            mExtraItemAdapter.remove(adapterPosition);
        }
    };

    private void addExtraItem() {
        if (mExtraItemAdapter.getItems().size() >= 5) {
            ToastUtils.showTasty(this, "最多只能添加5条额外的数据", TastyToast.WARNING);
            return;
        }
        ExtraOptionBean extraOptionBean = new ExtraOptionBean();
        mExtraItemAdapter.add(extraOptionBean);
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
        final String pwd = mCetvPassword.getText().toString().trim();
        final String name = mCetvName.getText().toString().trim();
        final String location = mCetvLocation.getText().toString().trim();
        final String notes = mEtNotes.getText().toString().trim();
        if (!(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(name) || TextUtils.isEmpty(location))) {
            List<ExtraOptionBean> items = (List<ExtraOptionBean>) mExtraItemAdapter.getItems();
            boolean needWarn = false;
            final List<ExtraOptionBean> resultList = new ArrayList<>();
            for (ExtraOptionBean item : items) {
                if (TextUtils.isEmpty(item.getKey()) || TextUtils.isEmpty(item.getValue())) {
                    needWarn = true;
                } else {
                    resultList.add(item);
                }
            }
            String extraOptions = null;
            if (resultList.size() > 0) {
                extraOptions = GsonHelper.toJsonFromList(resultList);
            }
            if (needWarn) {
                final String finalExtraOptions = extraOptions;
                StyledDialog.buildIosAlert(this, "警告!", "标题和内容有任何一项没有填写将会丢弃！\r\n\r\n倔强的选择丢弃请点“确定”，填写请点“取消”", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        onAddPassword(pwd, name, location, notes, finalExtraOptions);
                    }

                    @Override
                    public void onSecond() {

                    }
                }).show();
            } else {
                onAddPassword(pwd, name, location, notes, extraOptions);
            }

        } else {
            shakeAnimator();
        }
    }

    private void onAddPassword(String pwd, String name, String location, String notes, String extraOptions) {
        scaleAnimator();
        PasswordData passwordData = new PasswordData();
        passwordData.setAddress(location);
        passwordData.setCreateTime(System.currentTimeMillis());
        passwordData.setName(name);
        passwordData.setNotes(notes);
        passwordData.setPwd(pwd);
        passwordData.setExtraOptions(extraOptions);
        passwordData.setIcon(mItemBean==null?null:mItemBean.getIcon());
        if (mIsEdit) {
            PasswordData oldPasswordData = DbManager.getInstance().queryById(mItemBean.getId());
            UpdateData updateData = UpdateData.toUpdateData(oldPasswordData);
            updateData.setUpdateTime(System.currentTimeMillis());
            long index = UpdateManager.getInstance().add(updateData);
            String updateHistoryIds = passwordData.getUpdateHistoryIds();
            if (updateHistoryIds!=null){
                updateHistoryIds=updateHistoryIds+","+index;
            }else {
                updateHistoryIds = index+"";
            }
            passwordData.setUpdateHistoryIds(updateHistoryIds);
            passwordData.setId(mItemBean.getId());
            passwordData.setId(mItemBean.getIndex());
            DbManager.getInstance().update(passwordData);

            ToastUtils.showTasty(AddPasswordActivity.this, "修改成功", TastyToast.SUCCESS);
        } else {
            DbManager.getInstance().add(passwordData);
            ToastUtils.showTasty(AddPasswordActivity.this, "添加成功", TastyToast.SUCCESS);
        }
        finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_OK){
            if (requestCode==100){
                int resourceId = data.getIntExtra("resourceId", -1);
                if (resourceId!=-1){
                    mIvSelectIcon.setImageResource(resourceId);
                    if (mItemBean==null){
                        mItemBean = new ItemBean();
                    }
                    mItemBean.setIcon(resourceId);
                }
            }
        }
    }
}
