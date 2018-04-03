package com.gersion.superlock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.IconListAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.IconBean;
import com.gersion.superlock.utils.IconHelper;
import com.gersion.superlock.view.TitleView;

import java.util.List;

public class SelectIconActivity extends BaseActivity {
    private TitleView mTitleView;
    private RecyclerView mRecyclerView;
    private IconListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_icon_select;
    }

    @Override
    protected void initView() {
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mTitleView.setTitleText("选择图标")
                .setAddVisiable(false)
                .setRightTextVisiable(true)
                .setSearchVisiable(false);

        mAdapter = new IconListAdapter();
        mAdapter.registerMultiBean(IconBean.class, R.layout.layout_icon_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        List<IconBean> iconResourceId = IconHelper.getIconResourceId();
        mAdapter.setItems(iconResourceId);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            int resourceId = bundle.getInt("resourceId", -1);
            if (resourceId!=-1){
                for (IconBean bean : iconResourceId) {
                    if (bean.getResourceId()==resourceId){
                        bean.setSelected(true);
                        break;
                    }
                }
            }

        }
    }

    @Override
    protected void initListener() {
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTitleView.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IconBean iconBean = mAdapter.getIconBean();
                if (iconBean!=null) {
                    Intent intent = new Intent();
                    intent.putExtra("resourceId", iconBean.getResourceId());
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    showToast("没有选择任何图标");
                }
            }
        });
    }
}
