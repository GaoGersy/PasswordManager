package com.gersion.superlock.activity;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.listener.ResultCallback;
import com.gersion.superlock.utils.BackupHelper;
import com.gersion.superlock.utils.GsonHelper;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.TitleView;

import java.util.ArrayList;
import java.util.List;

public class BackupDataActivity extends BaseActivity {

    private TitleView mTitleView;
    private RadioGroup mRgBackLocation;
    private DbManager mDbManager;
    private String mEncryptResult;
    private TextView mTvCommit;
    private int backupLocation = R.id.rb_local;
    private String mDataJson;
    private BackupHelper mBackupHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_backup_data;
    }

    @Override
    protected void initView() {
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mRgBackLocation = (RadioGroup) findViewById(R.id.rg_back_location);
        mTvCommit = (TextView) findViewById(R.id.tv_commit);

        mTitleView.setTitleText("备份密码数据")
                .setAddVisiable(false)
                .setSearchVisiable(false);

        mRgBackLocation.check(R.id.rb_local);
    }

    @Override
    protected void initData() {
        mDbManager = DbManager.getInstance();
        List<PasswordData> datas = mDbManager.queryAll();
        if (datas != null && datas.size() > 0) {
            List<Keyer> keyers = new ArrayList<>();
            for (PasswordData data : datas) {
                Keyer keyer = new Keyer(data);
                keyers.add(keyer);
            }
            mDataJson = GsonHelper.toJsonFromList(keyers);
        }
        mBackupHelper = BackupHelper.getInstance();
        mBackupHelper.setOnResultCallback(new ResultCallback() {
            @Override
            public void onResultSuccess(String result) {
                ToastUtils.show(BackupDataActivity.this,result);
            }

            @Override
            public void onResultFailed(String result) {
                ToastUtils.show(BackupDataActivity.this,result);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initListener() {
        mRgBackLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                backupLocation = checkedId;
            }
        });

        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mTvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupData();
            }
        });
    }

    private void backupData() {
        if (mEncryptResult==null){
            ToastUtils.show(this,"还没有任何密码数据,不需要备份");
            return;
        }
        switch (backupLocation) {
            case R.id.rb_local:
                BackupHelper.getInstance().backup2Local(mDataJson);
                break;
            case R.id.rb_mail:
                BackupHelper.getInstance().backup2Email(mDataJson);
                break;
        }
    }

}
