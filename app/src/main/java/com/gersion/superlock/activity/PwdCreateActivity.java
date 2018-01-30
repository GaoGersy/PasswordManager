package com.gersion.superlock.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.fragment.PasswordCreaterFragment;
import com.gersion.superlock.view.TitleView;


public class PwdCreateActivity extends BaseActivity {

    private TitleView mTitleView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pwd_create;
    }

    @Override
    protected void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, new PasswordCreaterFragment());
        fragmentTransaction.commit();
    }

    @Override
    protected void initData() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setAddVisiable(false)
                .setSearchVisiable(false)
                .setTitleText("密码生成器");
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
