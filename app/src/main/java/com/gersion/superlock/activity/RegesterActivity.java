package com.gersion.superlock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.RegesterAdapter;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.controller.MessageEvent;
import com.gersion.superlock.dao.MainKeyDao;
import com.gersion.superlock.dao.PasswordDao;
import com.gersion.superlock.dao.SqlPassword;
import com.gersion.superlock.utils.Md5Utils;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;
import com.gersion.superlock.utils.SqliteUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.NoTouchViewPager;
import com.orhanobut.logger.Logger;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    private MainKeyDao mMkd;
    private String mCurrentPwd;
    //用来判断是不是第一次创建密码，第一次的时候会跳转到LockActivity,所以visibleCount多加了1;
    // 为了以后的页面能够正常进入后台能加密，所以onStop又必须减1
    private boolean mIsFinishGuide;
    private String mOriginalPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regester);
        mMkd = new MainKeyDao(this);
        mOriginalPwd = mMkd.query();
        initView();

        initData();

        initEvent();

    }

    // 初始化控件
    private void initView() {
        mVp = (NoTouchViewPager) findViewById(R.id.activity_regester_vp);

    }

    // 初始化数据
    private void initData() {
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

    // 初始化事件监听
    private void initEvent() {
        oldPwdGoClick();
        regesterGoClick();
        verifyGoClick();
        if (mIsChangePwd) {
            oldPwdTextChange();
        }
        verifyPwdTextChange();
        regesterPwdTextChange();

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

        if (!mIsChangePwd){
            long timeMillis = System.currentTimeMillis();
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
            String currenTime = format.format(new Date(timeMillis));
            SpfUtils.putString(this,MyConstants.FIRST_TIME,currenTime);
        }
        final String verifyPwd = mVerifyPwd.getText().toString().trim();
        if (TextUtils.equals(mCurrentPwd, verifyPwd)) {
            Observable.just("")
                    .map(new Func1<String, Object>() {
                        @Override
                        public Object call(String s) {
                            return mMkd.add(verifyPwd);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .subscribe();
            if (mIsChangePwd){

                EventBus.getDefault().postSticky(new MessageEvent("ChangePwd"));
            }else{
                startActivity(new Intent(RegesterActivity.this, MainActivity.class));
            }
            SpfUtils.putBoolean(RegesterActivity.this, MyConstants.IS_CHANGE_PWD, true);
            SpfUtils.putBoolean(this, MyConstants.IS_FINISH_GUIDE,true);
            SpfUtils.putInt(this, MyConstants.LENGTH,mVerifyPwd.getText().toString().trim().length());

            boolean b = resetSqlData();
            finish();
        } else {
            ToastUtils.showTasty(RegesterActivity.this,"两次密码不一致", TastyToast.WARNING);
        }

    }

    private void handleReset(){
        Observable.just(resetSqlData())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        resetSqlData();
                    }
                });
    }

    //因为数据库加密密码与主密码有关，所以在修改主密码后也必须对数据库重新设置密码
    private boolean resetSqlData(){
        final File sqlPath = SqliteUtils.getSqlPath(this, SqlPassword.DB_NAME);
        if (sqlPath.exists()){
            final PasswordDao dao = new PasswordDao(this,mOriginalPwd);
            Observable.just("a")
                    .map(new Func1<String, List<Keyer>>() {
                        @Override
                        public List<Keyer> call(String s) {
                            return dao.query();
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action1<List<Keyer>>() {
                        @Override
                        public void call(List<Keyer> keyers) {
                            Logger.d(keyers.size());
                            dao.destory();
                            sqlPath.delete();
                            PasswordDao passwordDao = new PasswordDao(RegesterActivity.this);
                            passwordDao.addAll(keyers);
                            passwordDao.destory();
                        }
                    });
        }
        return true;
    }

    private void regesterPwd() {
        mCurrentPwd = mRegesterPwd.getText().toString().trim();
        if(checkPwdIsOk(mCurrentPwd)){
            stepNum++;
            mVp.setCurrentItem(mVp.getCurrentItem() + 1);
        }
    }

    private boolean checkPwdIsOk(String pwd){
        if (pwd.length()<6){
            ToastUtils.showTasty(this,"密码长度不能小于6位",TastyToast.ERROR);
            return false;
        }

        if (!pwd.matches(".*[a-zA-Z].*")){
            ToastUtils.showTasty(this,"密码必须包含字母",TastyToast.ERROR);
            return false;
        }

        if (!pwd.matches(".*[0-9].*")){
            ToastUtils.showTasty(this,"密码必须包含数字",TastyToast.ERROR);
            return false;
        }
        return true;
    }

    private void checkOldPwd() {
        String mainKey = mMkd.query();
        String oldPwd = Md5Utils.encodeTimes(
                MyConstants.ADD_SALT + mOldPwd.getText().toString().trim() + MyConstants.ADD_SALT);
        if (TextUtils.equals(mainKey, oldPwd)) {
            stepNum++;
            mVp.setCurrentItem(mVp.getCurrentItem() + 1);
        } else {
            ToastUtils.showTasty(RegesterActivity.this,"密码错误", TastyToast.ERROR);
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
        if (!mIsFinishGuide){
            visibleCount++;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        if (!mIsFinishGuide){
            visibleCount--;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
    }
}
