package com.gersion.superlock.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.SearchAdapter;
import com.gersion.superlock.animator.EasyTransition;
import com.gersion.superlock.animator.EasyTransitionOptions;
import com.gersion.superlock.base.BaseLifeActivity;
import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.listener.OnItemClickListener;
import com.gersion.superlock.utils.MatchUtils;
import com.gersion.superlock.view.SearchView;
import com.gersion.superlock.view.smartRecycleView.SmartRecycleView;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class GlobalSearchActivity extends BaseLifeActivity {

    private SearchView mSearchView;
    private SmartRecycleView mSmartRecycleView;
    private List<DbBean> mDataList = new ArrayList<>();
    private List<DbBean> mPasswordBeans;
    private SearchAdapter mSearchAdapter;

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, GlobalSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_global_search);
        initView();
        initData();
        initListener();
    }

    protected void initView() {
        mSearchAdapter = new SearchAdapter(mDataList);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSmartRecycleView = (SmartRecycleView) findViewById(R.id.smartRecycleView);
        mSmartRecycleView.setAutoRefresh(false)
                .refreshEnable(false)
                .loadMoreEnable(false)
                .setLayoutManger(SmartRecycleView.LayoutManagerType.LINEAR_LAYOUT)
                .setAdapter(mSearchAdapter);
    }

    protected void initData() {
        mPasswordBeans = DbManager.getInstance().load();
    }

    protected void initListener() {
        mSearchView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSearchView.setOnQueryChangeListener(new SearchView.OnQueryChangeListener() {
            @Override
            public void onTextSubmit(String query) {

            }

            @Override
            public void onTextChange(String query) {
                if (query.length()==0){
                    mSmartRecycleView.setVisibility(View.GONE);
                }else {
                    mSmartRecycleView.setVisibility(View.VISIBLE);
                    onSearch(query);
                }
            }
        });

        mSearchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startTrainsition(view,mDataList.get(position));
            }
        });
    }

    private void startTrainsition(View view, DbBean bean){
        Intent intent = DetailActivity.getDetailIntent(this, bean.getId());

        EasyTransitionOptions options =
                EasyTransitionOptions.makeTransitionOptions(
                        this,
                        view.findViewById(R.id.piv_icon),
                        view.findViewById(R.id.tv_title));
//                        findViewById(R.id.v_top_card));

        EasyTransition.startActivity(intent, options);
    }

    private void onSearch(String query) {
        mDataList.clear();
        for (DbBean passwordBean : mPasswordBeans) {
            if (MatchUtils.isMatch(passwordBean.getAddress(), query) ||
                    MatchUtils.isMatch(passwordBean.getName(), query) ||
                    MatchUtils.isMatch(passwordBean.getPwd(), query)) {
                mDataList.add(passwordBean);
            }
        }
        mSmartRecycleView.handleData(mDataList);
    }

}
