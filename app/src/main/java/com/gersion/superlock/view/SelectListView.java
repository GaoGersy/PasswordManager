package com.gersion.superlock.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.SelectListAdapter;
import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.db.PasswordManager;
import com.gersion.superlock.service.FloatWindowManager;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.view.smartRecycleView.SmartRecycleView;

import java.util.List;

/**
 * Created by gersy on 2017/7/1.
 */

public class SelectListView extends LinearLayout {

    private View mView;
    private Context mContext;
    private SmartRecycleView mSmartRecycleView;
    private OnItemSelectedListener mListener;
    private EditText mEtPwd;
    private LinearLayout mLlContainer;
    private String mPassword;
    private SelectListAdapter mAdapter;

    public SelectListView(Context context) {
        this(context, null);
    }

    public SelectListView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.view_select_list, this);
        mContext = context;
        mSmartRecycleView = (SmartRecycleView) mView.findViewById(R.id.smartRecycleView);
        mLlContainer = (LinearLayout) mView.findViewById(R.id.ll_container);
        mEtPwd = (EditText) mView.findViewById(R.id.et_pwd);
        initRecycleView();
        initData();
        initListener();
    }

    private void initRecycleView() {
        DbManager.getInstance().onStart();
        mAdapter = new SelectListAdapter(null);
        mSmartRecycleView
                .setAutoRefresh(false)
                .setAdapter(mAdapter)
                .loadMoreEnable(false)
                .refreshEnable(false)
                .setLayoutManger(SmartRecycleView.LayoutManagerType.LINEAR_LAYOUT);

        if (!ConfigManager.getInstance().isLock()){
            mLlContainer.setVisibility(GONE);
            mSmartRecycleView.setVisibility(VISIBLE);
        }
    }

    private void initData() {
        mPassword = PasswordManager.getInstance().getPassword();
        List<DbBean> passwordBeans = DbManager.getInstance().load();
        mSmartRecycleView.handleData(passwordBeans);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new SelectListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DbBean bean) {
                if (mListener != null) {
                    mListener.onItemSeleted(bean);
                }
                FloatWindowManager.changeSelectListViewStatus(getContext());
            }
        });

        mEtPwd.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = mEtPwd.getText().toString().trim();
                if (pwd.length() == ConfigManager.getInstance().getPwdLength()) {
                    login(pwd);
                }
            }
        });
    }

    private void login(String pwd) {
        if (TextUtils.equals(mPassword, pwd)) {
            mLlContainer.setVisibility(GONE);
            mSmartRecycleView.setVisibility(VISIBLE);
        }else{
            Toast.makeText(mContext,"密码错误",Toast.LENGTH_SHORT).show();
        }

    }

    public void removed(){
        mEtPwd.setText("");
        mLlContainer.setVisibility(VISIBLE);
        mSmartRecycleView.setVisibility(GONE);
    }

    public interface OnItemSelectedListener {
        void onItemSeleted(DbBean bean);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mListener = listener;
    }

}
