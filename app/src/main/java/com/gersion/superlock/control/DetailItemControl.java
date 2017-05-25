package com.gersion.superlock.control;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseDetailControl;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.config.ConfigBean;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.orhanobut.logger.Logger;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.control
 * @待完成
 * @创建时间 2016/9/16
 * @功能描述 主界面中listview中每个item中的内容展示，有3D立体效果的viewPager，每个界面展示不同的内容
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class DetailItemControl extends BaseDetailControl implements View.OnClickListener {

    private View mView;
    private TextView mBg;
    private TextView mBtn;
    private TextView mAddress;
    private TextView mName;
    private TextView mPassword;
    private TextView mTime;
    private String mTitle;
    private String mHintText;
    private ConfigBean mConfigBean;

    public DetailItemControl(Context context, Keyer keyer) {
        super(context, keyer);
    }

    public View getRootView() {
        mView = View.inflate(mContext, R.layout.item_main_show, null);
        mConfigBean = StyledDialog.buildNormalInput(mContext, mTitle, mHintText, null, "修改", "取消", new MyDialogListener() {
            @Override
            public void onFirst() {

            }

            @Override
            public void onSecond() {

            }

            @Override
            public void onGetInput(CharSequence input1, CharSequence input2) {
                super.onGetInput(input1, input2);
                String input = input1.toString();
                switch (mTitle){
                    case "修改位置信息":
                        mKeyer.address = input;
                        break;
                    case "修改用户名":
                        mKeyer.name = input;
                        break;
                    case "修改密码":
                        mKeyer.pwd = input;
                        break;
                }
                Logger.d(mKeyer.number+"asd");
            }
        });
        initView();

        initData();

        initEvent();
        return mView;
    }


    //初始化控件
    public View initView() {
        mView = View.inflate(mContext, R.layout.item_main_show, null);
        mAddress = (TextView) mView.findViewById(R.id.address);
        mName = (TextView) mView.findViewById(R.id.name);
        mPassword = (TextView) mView.findViewById(R.id.password);
        mTime = (TextView) mView.findViewById(R.id.time);

        mAddress.setText("密码所在的位置："+mKeyer.address);
        setKeyType();


        mName.setText("用户名："+mKeyer.name);
        boolean isShowUpdateTime = SpfUtils.getBoolean(mContext, MyConstants.IS_SHOW_UPDATE_TIME, false);
        if (isShowUpdateTime){
            mTime.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(mKeyer.updateTime)){
                mTime.setText("上次更新时间：从未更新过");
            }else{
                mTime.setText("上次更新时间："+mKeyer.updateTime);
            }
        }

        return mView;
    }

    private void setKeyType() {
        boolean isShowPwd = SpfUtils.getBoolean(mContext, MyConstants.IS_SHOW_PWD, true);
        if (isShowPwd){
            mPassword.setText("密码："+mKeyer.pwd);
        }else{
            mPassword.setText("密码：********");
        }
    }

    public void playAnimator(){
        doAnimator(mView, mAddress,0);
        doAnimator(mView, mName,100);
        doAnimator(mView, mPassword,200);
        doAnimator(mView, mTime,300);
    }

    //初始化数据
    private void initData() {


    }

    //初始化监听事件
    private void initEvent() {
        mAddress.setOnClickListener(this);
        mName.setOnClickListener(this);
        mPassword.setOnClickListener(this);
    }

    private void doAnimator(View mView, View view,int dely) {
        if (mView==null){
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",  mView.getWidth(),0);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setStartDelay(dely);
        animator.setDuration(500);
        animator.start();
    }

    private void outAnimator(View mView, View view,int dely) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",0,  mView.getWidth());
        animator.setInterpolator(new OvershootInterpolator());
        animator.setStartDelay(dely);
        animator.setDuration(500);
        animator.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address:
                mTitle = "修改位置信息";
                mHintText = "请输入一个新的位置";
                break;
            case R.id.name:
                mTitle = "修改用户名";
                mHintText = "请输入一个新的用户名";
                break;
            case R.id.password:
                mTitle = "修改密码";
                mHintText = "请输入一个新的密码";
                break;
        }
        mConfigBean.hint1 = mHintText;
        mConfigBean.title = mTitle;
        mConfigBean.show();
    }
}
