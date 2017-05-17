package com.gersion.superlock.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jojo.sns.BuildConfig;
import com.jojo.sns.R;
import com.jojo.sns.listener.OnDialogClickListener;
import com.jojo.sns.utils.AccountUtil;
import com.jojo.sns.utils.ImageCompressionUtils;
import com.jojo.sns.utils.KeyboardUtils;
import com.jojo.sns.utils.LoggerUtils;
import com.jojo.sns.utils.ToastUtil;
import com.jojo.sns.views.dialog.CustomAlertDialog;
import com.jojo.sns.views.dialog.LoadingDialog;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.eventbus.ScopeBus;
import com.zhy.http.okhttp.OkHttpUtils;


public abstract class BaseActivity extends FragmentActivity {
    @SuppressWarnings("unused")
    private Class<?> currentActvitiy = null;
    public DialogFragment dialog;
    public LoadingDialog m_cProgressDialog;
    public String userid;
    public Activity context;

    /**
     * 从那个 activity 跳转而来
     **/
    protected int fromActivity = -1;

    public String m_pwd;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        getTimeUsed();
        //userid = PreferenceUtils.getString(this,"userid");
        userid = AccountUtil.getAccount().userid;
        NimUIKit.setAccount(userid);
        super.onCreate(savedInstanceState);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.yellow));
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }*/
        /* 取消自动弹出软键盘 */
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//		EventBus.getDefault().register(this);
        AppManager.getInstance().addActivity(this);
        currentActvitiy = AppManager.getInstance().currentActivity().getClass();
        bus.created();
        context = this;
        setupView();

//        setStatusBar();
//        try {
//            AndroidBug5497Workaround.assistActivity(this);
//        } catch (NullPointerException e) {
//
//        }
        initData();
        setClickListener();
        setTranslucentStatus(true);
        LoggerUtils.d(this.getClass().getSimpleName());
    }

    /**
     * ~~ 创建时间：2017/5/2 18:59 ~~
     * 用来检测耗时操作
     */
    private void getTimeUsed() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    public interface OnComfireClickLister{
        public void OnComfire();
    }

    public void showTipDialog(String s,final OnComfireClickLister lister) {
        if (BaseActivity.this!=null&&!BaseActivity.this.isFinishing()) {
        customAlertDialog = new CustomAlertDialog(this, new OnDialogClickListener() {
            @Override
            public void OnConfirmClick() {


            }

            @Override
            public void OnCancelClick() {
                lister.OnComfire();
                customAlertDialog.dismiss();
            }
        });
            customAlertDialog.setCancelable(false);

        customAlertDialog.setSigleChoose();
        customAlertDialog.setTitleText(s);
        customAlertDialog.setCancelText("确定");
        }

    }
    public void showNroDialog(String s,final OnComfireClickLister lister) {
        if (BaseActivity.this!=null&&!BaseActivity.this.isFinishing()) {
            customAlertDialog = new CustomAlertDialog(this, new OnDialogClickListener() {
                @Override
                public void OnConfirmClick() {


                }

                @Override
                public void OnCancelClick() {
                    lister.OnComfire();
                    customAlertDialog.dismiss();
                }
            });

            customAlertDialog.setCancelable(false);
            customAlertDialog.setTitleText(s);
            customAlertDialog.setCancelText("确定");
        }

    }
    public void dimissTipDialog(){
        if(customAlertDialog!=null){
            customAlertDialog.dismiss();
        }
    }
    public void showTipDialog(String s) {
        if (BaseActivity.this!=null&&!BaseActivity.this.isFinishing()) {
            customAlertDialog = new CustomAlertDialog(this, new OnDialogClickListener() {
                @Override
                public void OnConfirmClick() {


                }

                @Override
                public void OnCancelClick() {

                    customAlertDialog.dismiss();
                }
            });
            customAlertDialog.setCancelable(false);
            customAlertDialog.setSigleChoose();
            customAlertDialog.setTitleText(s);
            customAlertDialog.setCancelText("确定");
        }
    }

    protected final void postDelayed(final Runnable runnable, long delay) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // validate
                // TODO use getActivity ?


                // run
                runnable.run();
            }
        }, delay);
    }

    private void setStatusBar() {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }

    protected abstract void setupView();

    protected abstract void initData();

    protected abstract void setClickListener();

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = res.getConfiguration();
        config.fontScale = 1f; //1 设置正常字体大小的倍数
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 弹出进度条对话框
     */
    public void showProgressDialog() {
        if (isFinishing()) {
            return;
        }
        if (m_cProgressDialog == null) {
            m_cProgressDialog = new LoadingDialog(this);
        }

    }

    public void dismissLoadingDialog() {
        if (m_cProgressDialog != null) {
            m_cProgressDialog.dismiss();
            m_cProgressDialog = null;
        }
    }

    /**
     * 关闭等待对话框
     */
    public void closeProgressDialog() {
        if (!isFinishing() && m_cProgressDialog != null) {
            try {
                m_cProgressDialog.dismiss();
                m_cProgressDialog = null;
            } catch (Exception e) {
            }
        }
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public String getPwd() {
        return m_pwd;
    }

    public void setPwd(String m_pwd) {
        this.m_pwd = m_pwd;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
//		EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // isActive = true;
        OkHttpUtils.getInstance().cancelTag(this);
        ImageCompressionUtils.deleteAllFile();
        KeyboardUtils.closeKeyboard(this);
        bus.destoryed();
        AppManager.getInstance().finishActivity(this);
        closeProgressDialog();
        dismissLoadingDialog();

    }

    public void showToast(String msg) {
        ToastUtil.show(this, msg);
    }

    public void showToast(int resid) {
        Toast.makeText(this, getString(resid), Toast.LENGTH_SHORT).show();
    }

    public void finish(Activity activity) {
        AppManager.getInstance().finishActivity(activity);
    }

    public void clearTask() {
        AppManager.getInstance().finishAllActivity();
    }

    public void toActivity(Class<?> toClsActivity) {
        toActivity(toClsActivity, null);
    }

    public void toActivity(Class<?> toClsActivity, Bundle bundle) {
        Intent intent = new Intent(this, toClsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void toActivityDelay(Class<?> toClsActivity, Bundle bundle) {
        final Intent intent = new Intent(this, toClsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 700);

    }

    public void toActivityForResult(Class<?> toClsActivity, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, toClsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void toActivityForResult(Class<?> toClsActivity, int requestCode) {
        Intent intent = new Intent(this, toClsActivity);
        startActivityForResult(intent, requestCode);
    }

    public void toActivityForResult(Class<?> toClsActivity, String name, String content, int requestCode) {
        Intent intent = new Intent(this, toClsActivity);
        if (content != null) {
            intent.putExtra(name, content);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onBackPressed() {
        KeyboardUtils.hideSoftInput(this);
        finish(this);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }


    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public void hiddenTitle() {
    }//隐藏TitleView

    public void showTitle() {
    }//显示TitleView

}
