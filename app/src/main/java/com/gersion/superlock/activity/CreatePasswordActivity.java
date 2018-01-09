package com.gersion.superlock.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.fragment.PasswordCreaterFragment;
import com.gersion.superlock.view.TitleView;

public class CreatePasswordActivity extends BaseActivity {

    private TitleView mTitleView;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_create_password;
    }

    @Override
    protected void initView() {
//        Fr flContainer = findViewById(R.id.fl_container);
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mTitleView.setTitleText("密码生成器");
        mTitleView.setAddVisiable(false);
        mTitleView.setSearchVisiable(false);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        PasswordCreaterFragment fragment = new PasswordCreaterFragment();
        fragment.isSuperPassword(true);
        fragmentTransaction.replace(R.id.fl_container, fragment);
        fragmentTransaction.commit();
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
