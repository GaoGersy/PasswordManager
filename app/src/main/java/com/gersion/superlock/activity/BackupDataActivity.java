package com.gersion.superlock.activity;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.utils.Aes;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.EmailUtil;
import com.gersion.superlock.utils.GsonHelper;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SDCardUtils;
import com.gersion.superlock.utils.SDCardUtilss;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.TitleView;
import com.orhanobut.logger.Logger;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

public class BackupDataActivity extends BaseActivity {

    private TitleView mTitleView;
    private RadioGroup mRgBackLocation;
    private DbManager mDbManager;
    private String mEncryptResult;
    private TextView mTvCommit;
    private int backupLocation = R.id.rb_local;

    @Override
    protected int setLayoutId() {
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
        mDbManager.onStart();
        List<DbBean> datas = mDbManager.load();
        if (datas != null && datas.size() > 0) {
            List<Keyer> keyers = new ArrayList<>();
            for (DbBean data : datas) {
                Keyer keyer = new Keyer(data);
                keyers.add(keyer);
            }
            String dataJson = GsonHelper.toJsonFromList(keyers);
            Logger.e(dataJson);

            mEncryptResult = Aes.encryptWithSuperPassword(dataJson);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbManager.destroy();
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
                backup2Local();
                break;
            case R.id.rb_mail:
                backup2Email();
                break;
        }
    }

    private void backup2Local() {
        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
        if (!sdCardEnable) {
            ToastUtils.showTasty(this, "没有SD卡，备份工作无法继续进行", TastyToast.WARNING);
            return ;
        }

        List<String> sdCardPaths = SDCardUtilss.getSDCardPaths(this);
        for (String sdCardPath : sdCardPaths) {
            Logger.e(sdCardPath);
        }
        try {
            boolean b = SDCardUtils.saveFileToSDCard(MyConstants.BACKUP_PATH, MyConstants.BACKUP_FILE_NAME + "."+ MyConstants.FILE_TYPE, mEncryptResult);

            if (b){
                ToastUtils.showTasty(this, "备份成功", TastyToast.SUCCESS);
            }else {
                ToastUtils.showTasty(this, "备份失败", TastyToast.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showTasty(this, "备份出现异常", TastyToast.SUCCESS);
        }
    }

    private void backup2Email() {
        String backupEmailAddress = ConfigManager.getInstance().getBackupEmailAddress();
        if (backupEmailAddress==null){
            ToastUtils.show(this,"请先设置要备份到的邮箱地址");
            return;
        }
        String title = "";
        String body = "";
        String path = "";
        sendMail(backupEmailAddress,title,body,path);
    }

    public void sendMail(final String toMail, final String title,
                         final String body, final String filePath){
        new Thread(new Runnable() {
            public void run() {
                EmailUtil emailUtil = new EmailUtil();
                try {

                    String account = "cmmailserver@canmou123.com";
                    String password = "CANmou123";
                    // String authorizedPwd = "vxoxkgtwrtxvoqz";
                    emailUtil.sendMail(toMail, account, "smtp.mxhichina.com",
                            account, password, title, body, filePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
