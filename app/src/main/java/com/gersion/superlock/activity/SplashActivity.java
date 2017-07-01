package com.gersion.superlock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        initData();

        initEvent();

    }

    //初始化控件
    private void initView() {


    }

    //初始化数据
    private void initData() {
        Intent intent;
        if (ConfigManager.getInstance().isFinishGuide()){
            intent = new Intent(this,MainActivity.class);
        }else{
            intent = new Intent(this,GuideActivity.class);
        }
        startActivity(intent);
        finish();
    }

    //初始化监听事件
    private void initEvent() {


    }
}
