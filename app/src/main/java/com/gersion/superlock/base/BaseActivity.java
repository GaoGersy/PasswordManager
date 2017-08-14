package com.gersion.superlock.base;

import android.content.Intent;
import android.os.Bundle;

import com.gersion.superlock.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by a3266 on 2017/6/11.
 */

public abstract class BaseActivity extends BaseLifeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(setLayoutId());
        initView();
        initData();
//        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    public void toActivity(Class<?> toClsActivity) {
        toActivity(toClsActivity, null);
    }

    public void toActivity(Class<?> toClsActivity, Bundle bundle) {
        Intent intent = new Intent(this, toClsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void toActivityForResult(Class<?> toClsActivity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, toClsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void toActivityForResult(Class<?> toClsActivity, int requestCode) {
        Intent intent = new Intent(this, toClsActivity);
        startActivityForResult(intent, requestCode);
    }

    public void toActivityForResult(Class<?> toClsActivity, String name, String content, int requestCode) {
        Intent intent = new Intent(this, toClsActivity);
        if (content != null) {
            intent.putExtra(name, content);
        }
        startActivityForResult(intent, requestCode);
    }

    public void showToast(String text){
        showToast(text, TastyToast.SUCCESS);
    }

    public void showToast(String text,int type){
        ToastUtils.showTasty(this,text,type);
    }

    protected abstract int setLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();
}
