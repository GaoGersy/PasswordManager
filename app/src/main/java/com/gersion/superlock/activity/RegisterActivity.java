package com.gersion.superlock.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.db.PasswordManager;
import com.gersion.superlock.lockadapter.LockAdapter;
import com.gersion.superlock.lockadapter.LockAdapterFactory;
import com.gersion.superlock.lockadapter.LockCallback;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.ImageLoader;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.NoTouchViewPager;
import com.gersion.superlock.view.TitleView;
import com.gyf.barlibrary.ImmersionBar;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

/**
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
public class RegisterActivity extends BaseActivity {
    public static final int OLD_PWD = 0;
    public static final int NEW_PWD = 1;
    public static final int VERIFY_PWD = 2;
    //用来记录当前到了第几个页面，如果是第一个页面，按下返回键就退出
    int stepNum = 0;
    private NoTouchViewPager mVp;
    private ArrayList<View> mList;
    private EditText mVerifyPwd;
    private EditText mRegesterPwd;
    private EditText mOldPwd;
    private boolean mIsChangePwd;
    private String mCurrentPwd;
    //用来判断是不是第一次创建密码，第一次的时候会跳转到LockActivity,所以visibleCount多加了1;
    // 为了以后的页面能够正常进入后台能加密，所以onStop又必须减1
    private boolean mIsFinishGuide;
    private String mUserName;
    private TextView mTvName;
    private TextView mTvRegisterName;
    private ConfigManager mConfigManager;
    private FrameLayout mFlContainer;
    private TitleView mTitleView;
    private LockAdapter mLockAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regester;
    }

    // 初始化控件
    @Override
    protected void initView() {
        ImmersionBar.with(this).fullScreen(false);
        mVp = (NoTouchViewPager) findViewById(R.id.activity_regester_vp);
        mFlContainer = (FrameLayout) findViewById(R.id.fl_container);
        mTitleView = (TitleView) findViewById(R.id.titleView);
        mTitleView.setAddVisiable(false);
        mTitleView.setSearchVisiable(false);
        mTitleView.setTitleText("设置APP启动密码");

        mConfigManager = ConfigManager.getInstance();
        mIsChangePwd = mConfigManager.isChangePwd();
        int mode = mIsChangePwd?MyConstants.LockMode.MODE_RESET:MyConstants.LockMode.MODE_INIT;
        mLockAdapter = LockAdapterFactory.create(mode);
        View view = mLockAdapter.init(this);
        mFlContainer.addView(view);

    }

    // 初始化数据
    @Override
    protected void initData() {
//        mList = new ArrayList<>();
//
//        mUserName = mConfigManager.getUserName();
//        mIsChangePwd = mConfigManager.isChangePwd();
//        if (mIsChangePwd) {
//            addPagerView(OLD_PWD);
//        }
//        addPagerView(NEW_PWD);
//        addPagerView(VERIFY_PWD);
//
//        RegesterAdapter adapter = new RegesterAdapter(mList);
//        mVp.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        mTitleView.setOnBackListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mLockAdapter.setLockCallback(new LockCallback() {
            @Override
            public void onSuccess() {
                if (!mIsChangePwd) {
                    ConfigManager.getInstance().setFinishGuide(true);
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
                finish();
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showTasty(RegisterActivity.this, msg, TastyToast.ERROR);
            }

            @Override
            public void onChangLockType(boolean b) {

            }
        });
//        if (mIsChangePwd) {
//            mOldPwd.setOnEditorActionListener(new OnEditorActionListener() {
//
//                @Override
//                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                    if (actionId == 0) {
//                        checkOldPwd();
//                    }
//                    return false;
//                }
//            });
//        }
//
//        mVerifyPwd.setOnEditorActionListener(new OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == 0) {
//                    verifyPwd();
//                }
//                return false;
//            }
//        });
//
//        mRegesterPwd.setOnEditorActionListener(new OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == 0) {
//                    regesterPwd();
//                }
//                return false;
//            }
//        });

    }

    private void addPagerView(final int type) {
        View view = View.inflate(this, R.layout.view_regester_vp, null);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        ImageLoader.getInstance().loadCircleIcon(R.drawable.pure_bg, ivIcon);
        TextView tvTitle = (TextView) view.findViewById(R.id.view_regester_title);
        final TextView tvNext = (TextView) view.findViewById(R.id.tv_next);
        final TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        EditText editText = (EditText) view.findViewById(R.id.view_regester_pwd);
        EditText userName = (EditText) view.findViewById(R.id.view_user_name);

        if (mUserName !=null){
            tvName.setText(mUserName);
        }

        String title = null;
        if (type == OLD_PWD) {
            mOldPwd = editText;
            title = "验证当前密码";
        } else if (type == NEW_PWD) {
            title = "创建新密码";
            mRegesterPwd = editText;
            mTvRegisterName = tvName;
            userName.setVisibility(mIsChangePwd?View.GONE:View.VISIBLE);
        } else if (type == VERIFY_PWD) {
            title = "确认新密码";
            mVerifyPwd = editText;
            mTvName = tvName;
            userName.setVisibility(View.GONE);
        }

        tvNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onNext(type);
            }
        });
        tvTitle.setText(title);

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvName.setText(s.toString());
                } else {
                    tvName.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 选择头像
            }
        });

        mList.add(view);
    }

    private void onNext(int type) {
        if (type == OLD_PWD) {
            checkOldPwd();
        } else if (type == NEW_PWD) {
            regesterPwd();
        } else if (type == VERIFY_PWD) {
            verifyPwd();
        }
    }

    private void verifyPwd() {

        String verifyPwd = mVerifyPwd.getText().toString().trim();
        if (TextUtils.equals(mCurrentPwd, verifyPwd)) {
            if (mIsChangePwd) {
                PasswordManager.getInstance().updatePassword(verifyPwd);
            } else {
                PasswordManager.getInstance().addPassword(verifyPwd);
                ConfigManager.getInstance().setFinishGuide(true);
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
            ConfigManager.getInstance().setPwdLength(verifyPwd.length());
            finish();
        } else {
            ToastUtils.showTasty(RegisterActivity.this, "两次密码不一致", TastyToast.WARNING);
        }
    }

    private void regesterPwd() {
        mCurrentPwd = mRegesterPwd.getText().toString().trim();
        if (checkPwdIsOk(mCurrentPwd)) {
            stepNum++;
            mVp.setCurrentItem(mVp.getCurrentItem() + 1);
            String userName = mTvRegisterName.getText().toString();
            mConfigManager.setUserName(userName);
            mTvName.setText(userName);
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
            ToastUtils.showTasty(RegisterActivity.this, "密码错误", TastyToast.ERROR);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(1);
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
        mIsFinishGuide = ConfigManager.getInstance().isFinishGuide();
        if (!mIsFinishGuide) {
            visibleCount++;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!mIsFinishGuide) {
            visibleCount--;
        }
    }
}
