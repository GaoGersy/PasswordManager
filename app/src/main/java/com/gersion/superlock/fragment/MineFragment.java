package com.gersion.superlock.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.activity.SettingActivity;
import com.gersion.superlock.base.BaseFragment;
import com.gersion.superlock.view.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a3266 on 2017/6/11.
 */

public class MineFragment extends BaseFragment {
    private ImageView mIcon;
    private TextView mName;
    private ImageView mCode2d;
    private ItemView mCollection;
    private ItemView mSetting;
    private ItemView mAbout;
    private ItemView mShare;
    private ItemView mExit;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mIcon = findView(R.id.icon);
        mName = findView(R.id.name);
        mCode2d = findView(R.id.code2d);
        mCollection = findView(R.id.collection);
        mSetting = findView(R.id.setting);
        mAbout = findView(R.id.about);
        mShare = findView(R.id.share);
        mExit = findView(R.id.exit);
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initListener() {
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(SettingActivity.class);
            }
        });
    }

}
