package com.gersion.superlock.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.gersion.superlock.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ClassName: NewsCenterBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年8月9日 下午7:36:01 <br/>
 *
 * @作者 Gers
 * @版本
 * @包名 com.example.smartbeijing.bean
 * @待完成 TODO
 * @创建时间 2016年8月9日
 * @描述 TODO
 * @更新人 $Author$
 * @更新时间 $Date$
 * @更新版本 $Rev$
 */
public class RegesterFragment extends Fragment {

    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.go)
    ImageView mGo;
    @BindView(R.id.activity_main)
    FrameLayout mActivityMain;
    private SpringSystem mSpringSystem;
    private Spring mSpring;

    private final SpringChain mSpringChain = SpringChain.create();
    private View mView;

    /**
     * DESC :  <br/>
     *
     * @see Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.view_regester_vpaaa, null);
        ButterKnife.bind(this, mView);
        initView();

        initData();

        initEvent();

        return mView;

    }

    //初始化控件
    private void initView() {
        mSpringSystem = SpringSystem.create();
        mSpring = mSpringSystem.createSpring();
        double currentValue = mSpring.getCurrentValue();
        final float topy = (float) SpringUtil.mapValueFromRangeToRange(currentValue, 0, 1, 0, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mSpring.setEndValue(0);
                float value1 = (float) mSpring.getCurrentValue();

                float l1x = (float) SpringUtil.mapValueFromRangeToRange(value1, 0, 1, 0, 1000);
                mTv.setTranslationX(l1x);

            }
        }, 42 * 38 -100);


    }

    //初始化数据
    private void initData() {

    }

    //初始化监听事件
    private void initEvent() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
  