package com.gersion.superlock.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.RegesterAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.controller.MessageEvent;
import com.gersion.superlock.db.PasswordManager;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.NoTouchViewPager;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * ClassName: NewsCenterBean <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2016年8月9日 下午7:36:01 <br/>
 *
 * @作者 Gers
 * @版本
 * @包名 com.example.smartbeijing.bean
 * @待完成 TODO
 * @创建时间 2016年8月9日
 * @描述 主密码的创建流程，修改主密码地逻辑也会进入此界面，根据mIsChangePwd的值判断是否是修改密码
 * @更新人 $Author$
 * @更新时间 $Date$
 * @更新版本 $Rev$
 */
public class RegesterActivity extends BaseActivity {
    public static final String CHANGE_PWD = "change_pwd";
    //用来记录当前到了第几个页面，如果是第一个页面，按下返回键就退出
    int stepNum = 0;
    private NoTouchViewPager mVp;
    private ArrayList<View> mList;
    private ImageView mVerifyGo;
    private EditText mVerifyPwd;
    private EditText mRegesterPwd;
    private ImageView mRegesterGo;
    private EditText mOldPwd;
    private ImageView mOldPwdGo;
    private boolean mIsChangePwd;
    private String mCurrentPwd;
    //用来判断是不是第一次创建密码，第一次的时候会跳转到LockActivity,所以visibleCount多加了1;
    // 为了以后的页面能够正常进入后台能加密，所以onStop又必须减1
    private boolean mIsFinishGuide;
    private String mOriginalPwd;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_regester;
    }

    // 初始化控件
    @Override
    protected void initView() {
        mVp = (NoTouchViewPager) findViewById(R.id.activity_regester_vp);

    }

    // 初始化数据
    @Override
    protected void initData() {
        mList = new ArrayList<View>();
        mIsChangePwd = SpfUtils.getBoolean(this, MyConstants.IS_CHANGE_PWD, false);
        if (mIsChangePwd) {
            initCheckView();
        }
        initRegesterView();
        initVerifyView();

        RegesterAdapter adapter = new RegesterAdapter(mList);
        mVp.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        oldPwdGoClick();
        regesterGoClick();
        verifyGoClick();
        if (mIsChangePwd) {
            oldPwdTextChange();
        }
        verifyPwdTextChange();
        regesterPwdTextChange();
    }

    // 初始化检查原密码的View
    private void initCheckView() {
        View oldPwdView = View.inflate(this, R.layout.view_regester_vp, null);
        mOldPwd = (EditText) oldPwdView.findViewById(R.id.view_regester_pwd);
        mOldPwdGo = (ImageView) oldPwdView.findViewById(R.id.view_regester_go);
        RelativeLayout mOldPwdBg = (RelativeLayout) oldPwdView.findViewById(R.id.view_regester_bg);
        TextView mOldPwdTitle = (TextView) oldPwdView.findViewById(R.id.view_regester_title);
        mOldPwdTitle.setText("验证当前密码");
        mOldPwdBg.setBackgroundResource(R.mipmap.login_paine);
        mList.add(oldPwdView);
    }

    // 初始化注册密码的View
    private void initRegesterView() {
        View regesterView = View.inflate(this, R.layout.view_regester_vp, null);
        mRegesterPwd = (EditText) regesterView.findViewById(R.id.view_regester_pwd);
        mRegesterGo = (ImageView) regesterView.findViewById(R.id.view_regester_go);
        RelativeLayout mRegesterBg =
                (RelativeLayout) regesterView.findViewById(R.id.view_regester_bg);
        TextView mRegesterTitle = (TextView) regesterView.findViewById(R.id.view_regester_title);
        mRegesterTitle.setText("创建新密码");
        mRegesterBg.setBackgroundResource(R.mipmap.regester_paine);
        mList.add(regesterView);
    }

    // 初始化确认注册密码的View
    private void initVerifyView() {
        View verifyView = View.inflate(this, R.layout.view_regester_vp, null);
        mVerifyPwd = (EditText) verifyView.findViewById(R.id.view_regester_pwd);
        mVerifyGo = (ImageView) verifyView.findViewById(R.id.view_regester_go);
        RelativeLayout mVerifyBg = (RelativeLayout) verifyView.findViewById(R.id.view_regester_bg);
        TextView mVerifyTitle = (TextView) verifyView.findViewById(R.id.view_regester_title);
        mVerifyTitle.setText("确认新密码");
        mVerifyBg.setBackgroundResource(R.mipmap.regester_verify_paine);
        mList.add(verifyView);
    }

    private void verifyPwdTextChange() {

        mVerifyPwd.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0) {
                    verifyPwd();
                }
                return false;
            }
        });

    }

    /**
     * DESC : . <br/>
     */
    private void oldPwdTextChange() {
        mOldPwd.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0) {
                    checkOldPwd();
                }
                return false;
            }
        });
    }

    private void regesterPwdTextChange() {

        mRegesterPwd.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0) {
                    regesterPwd();
                }
                return false;
            }
        });
    }

    private void oldPwdGoClick() {
        if (mIsChangePwd) {
            // 验证密码界面的点击
            mOldPwdGo.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkOldPwd();
                }

            });
        }
    }

    private void regesterGoClick() {
        // 创建密码界面的点击
        mRegesterGo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                regesterPwd();
            }

        });
    }

    private void verifyGoClick() {
        // 确认密码界面的点击
        mVerifyGo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPwd();
            }

        });
    }

    private void verifyPwd() {

        String verifyPwd = mVerifyPwd.getText().toString().trim();
        if (TextUtils.equals(mCurrentPwd, verifyPwd)) {
            if (mIsChangePwd) {
                PasswordManager.getInstance().updatePassword(verifyPwd);
            } else {
                PasswordManager.getInstance().addPassword(verifyPwd);
                ConfigManager.getInstance().setFinishGuide(true);
                startActivity(new Intent(RegesterActivity.this, MainActivity.class));
            }
            ConfigManager.getInstance().setPwdLength(verifyPwd.length());
            finish();
        } else {
            ToastUtils.showTasty(RegesterActivity.this, "两次密码不一致", TastyToast.WARNING);
        }
    }

    private void regesterPwd() {
        mCurrentPwd = mRegesterPwd.getText().toString().trim();
        if (checkPwdIsOk(mCurrentPwd)) {
            stepNum++;
            mVp.setCurrentItem(mVp.getCurrentItem() + 1);
        }
    }

    private boolean checkPwdIsOk(String pwd) {
        if (pwd.length() < 6) {
            ToastUtils.showTasty(this, "密码长度不能小于6位", TastyToast.ERROR);
            return false;
        }

        if (!pwd.matches(".*[a-zA-Z].*")) {
            ToastUtils.showTasty(this, "密码必须包含字母", TastyToast.ERROR);
            return false;
        }

        if (!pwd.matches(".*[0-9].*")) {
            ToastUtils.showTasty(this, "密码必须包含数字", TastyToast.ERROR);
            return false;
        }
        return true;
    }

    private void checkOldPwd() {
        String oldPwd = PasswordManager.getInstance().getEncyptPassword(mOldPwd.getText().toString().trim());
        String password = PasswordManager.getInstance().getPassword();
        if (TextUtils.equals(password, oldPwd)) {
            stepNum++;
            mVp.setCurrentItem(mVp.getCurrentItem() + 1);
        } else {
            ToastUtils.showTasty(RegesterActivity.this, "密码错误", TastyToast.ERROR);
        }
    }

    @Override
    public void onBackPressed() {
        mVp.setCurrentItem(mVp.getCurrentItem() - 1);
        if (mVp.getCurrentItem() == 0) {
            stepNum--;
        }
        if (stepNum < 0) {
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mIsFinishGuide = SpfUtils.getBoolean(RegesterActivity.this, MyConstants.IS_FINISH_GUIDE, false);
        if (!mIsFinishGuide) {
            visibleCount++;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        if (!mIsFinishGuide) {
            visibleCount--;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
    }
}
