package com.gersion.superlock.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.LockTypeDetailAdapter;
import com.gersion.superlock.app.SuperLockApplication;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.TitleView;
import com.sdsmdg.tastytoast.TastyToast;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.Orientation;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectLockTypeActivity extends BaseActivity {
    public static final int CODE = 100;
    @BindView(R.id.titleView)
    TitleView mTitleView;
    @BindView(R.id.detail_discreteScrollView)
    DiscreteScrollView mDetailDiscreteScrollView;
    @BindView(R.id.pin)
    TextView mPin;
    @BindView(R.id.pattern)
    TextView mPattern;
    @BindView(R.id.finger_print)
    TextView mFingerPrint;
    private TextView mTvPin;
    private TextView mTvPattern;
    private TextView mTvFingerPrint;
    private ConfigManager mConfigManager;
    private int currentMode;
    private LockTypeDetailAdapter mDetailAdapter;
    private ArrayList<String> mList;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_lock_type;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mTvPin = (TextView) findViewById(R.id.tv_pin);
        mTvPattern = (TextView) findViewById(R.id.tv_pattern);
        mTvFingerPrint = (TextView) findViewById(R.id.tv_finger_print);
        mConfigManager = ConfigManager.getInstance();
        int lockType = mConfigManager.getLockType();
        mTvPin.setSelected(lockType == MyConstants.LockType.TYPE_PIN);
        mTvPattern.setSelected(lockType == MyConstants.LockType.TYPE_PATTERN);
        mTvFingerPrint.setSelected(lockType == MyConstants.LockType.TYPE_FINGER_PRINT);

        mTitleView.setTitleText("密码设置")
                .setAddVisiable(false)
                .setSearchVisiable(false);
        boolean fingerprintEnable = SuperLockApplication.mFingerprintIdentify.isFingerprintEnable();
        changeStatus(lockType);
        mList = new ArrayList<>();
        mList.add("密码解锁");
        mList.add("图案解锁");
        if (fingerprintEnable) {
            mFingerPrint.setVisibility(View.VISIBLE);
            mList.add("指纹解锁");
        }

        mDetailDiscreteScrollView.setOrientation(Orientation.HORIZONTAL);
        mDetailAdapter = new LockTypeDetailAdapter();
        mDetailAdapter.registerMultiBean(String.class, R.layout.item_lock_type_detail);
        mDetailDiscreteScrollView.setAdapter(mDetailAdapter);
        mDetailDiscreteScrollView.setItemTransitionTimeMillis(100);
        mDetailDiscreteScrollView.setOffscreenItems(0);
        mDetailDiscreteScrollView.setItemTransformer(new ScaleTransformer.Builder().setMaxScale(1).setMinScale(1).build());
        mDetailAdapter.setItems(mList);
        mDetailDiscreteScrollView.scrollToPosition(lockType);
        mDetailDiscreteScrollView.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {
                changeStatus(adapterPosition);
            }

            @Override
            public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {
            }
        });
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

        mTvPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patternString = mConfigManager.getPatternString();
                currentMode = mConfigManager.getLockType();
                if (patternString == null) {
                    mConfigManager.setChangePwd(false);
                    changeLockType(MyConstants.LockType.TYPE_PATTERN, false);
                    toActivityForResult(RegisterActivity.class, CODE);
                } else {
                    changeLockType(MyConstants.LockType.TYPE_PATTERN, true);
                }
            }
        });
        mTvPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLockType(MyConstants.LockType.TYPE_PIN, true);
            }
        });
        mTvFingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLockType(MyConstants.LockType.TYPE_FINGER_PRINT, true);
            }
        });

        mPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(0);
                mDetailDiscreteScrollView.smoothScrollToPosition(0);
            }
        });
        mPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(1);
                mDetailDiscreteScrollView.smoothScrollToPosition(1);
            }
        });
        mFingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(2);
                mDetailDiscreteScrollView.smoothScrollToPosition(2);
            }
        });
    }

    private void changeStatus(int position) {
        mPin.setSelected(position==0?true:false);
        mPattern.setSelected(position==1?true:false);
        mFingerPrint.setSelected(position==2?true:false);
    }

    private void changeLockType(int lockType, boolean showMsg) {
        mTvPin.setSelected(lockType == MyConstants.LockType.TYPE_PIN);
        mTvPattern.setSelected(lockType == MyConstants.LockType.TYPE_PATTERN);
        mTvFingerPrint.setSelected(lockType == MyConstants.LockType.TYPE_FINGER_PRINT);
        mConfigManager.setLockType(lockType);
        if (showMsg) {
            String msg = null;
            switch (lockType) {
                case MyConstants.LockType.TYPE_PIN:
                    msg = "已修改为密码解锁";
                    break;
                case MyConstants.LockType.TYPE_PATTERN:
                    msg = "已修改为图案解锁";
                    break;
                case MyConstants.LockType.TYPE_FINGER_PRINT:
                    msg = "已修改为指纹解锁";

                    break;
            }
            ToastUtils.showTasty(this, msg, TastyToast.SUCCESS);
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == CODE) {
//                changeLockType(MyConstants.LockType.TYPE_PATTERN, true);
//            }
//        } else {
//            changeLockType(currentMode, false);
//            ToastUtils.showTasty(this, "设置图案解锁失败", TastyToast.ERROR);
//        }
//    }
}
