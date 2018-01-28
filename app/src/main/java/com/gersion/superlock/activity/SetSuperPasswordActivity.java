package com.gersion.superlock.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.TitleView;
import com.sdsmdg.tastytoast.TastyToast;

public class SetSuperPasswordActivity extends BaseActivity {

    private TextView mTvGetPassword;
    private EditText mEtPassword;
    private static final int CODE = 100;
    private TitleView mTitleView;
    private TextView mTvComplete;
    private ConfigManager mConfigManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_super_password;
    }

    @Override
    protected void initView() {
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mTvGetPassword = (TextView) findViewById(R.id.tv_get_password);
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mTvComplete = (TextView) findViewById(R.id.tv_complete);
        mTitleView.setTitleText("设置超级密码");
        mTitleView.setSearchVisiable(false);
        mTitleView.setAddVisiable(false);
        mConfigManager = ConfigManager.getInstance();
    }

    @Override
    protected void initData() {
        boolean superPassowrdSeted = mConfigManager.isSuperPasswordSeted();
        if (superPassowrdSeted) {
            toActivity(RegisterActivity.class);
            finish();
        }
    }

    @Override
    protected void initListener() {
        mTvGetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivityForResult(CreatePasswordActivity.class, CODE);
            }
        });

        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mEtPassword.getText().toString().trim();
                boolean ok = checkPwdIsOk(password);
                if (ok) {
                    mConfigManager.setSuperPassword(password);
                    mConfigManager.setSuperPasswordSeted(true);
                    toActivity(RegisterActivity.class);
                    finish();
                }
            }
        });
    }

    private boolean checkPwdIsOk(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showTasty(this, "请输入一个超级密码", TastyToast.ERROR);
            return false;
        }
        if (pwd.length() < 15) {
            ToastUtils.showTasty(this, "超级密码长度不能小于15位", TastyToast.ERROR);
            return false;
        }

        if (!pwd.matches(".*[a-zA-Z].*")) {
            ToastUtils.showTasty(this, "超级密码必须包含字母", TastyToast.ERROR);
            return false;
        }

        if (!pwd.matches(".*[0-9].*")) {
            ToastUtils.showTasty(this, "超级密码必须包含数字", TastyToast.ERROR);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CODE) {
                String superPassword = mConfigManager.getSuperPassword();
                mEtPassword.setText(superPassword);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        visibleCount++;
    }

    @Override
    public void onStop() {
        super.onStop();
        visibleCount--;
    }

}
