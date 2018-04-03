package com.gersion.superlock.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.gersion.superlock.R;
import com.gersion.superlock.activity.MainActivity;
import com.gersion.superlock.bean.DaoSession;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.RudenessScreenHelper;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.Screen;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * @作者 Gersy
 */
public class SuperLockApplication extends Application {
    //上下文
    public static Context mContext;

    //主线程的handler
    public static Handler mHandler;
    public static FingerprintIdentify mFingerprintIdentify;
    public final static float DESIGN_WIDTH = 750; //绘制页面时参照的设计图宽度
    public static IWXAPI api;
    private DaoSession mDaoSession;
    private static SuperLockApplication INSTANCE;

    /**
     * 得到上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    public static SuperLockApplication getInstance(){
        return INSTANCE;
    }

    /**
     * 得到主线程handler对象
     *
     * @return
     */
    public static Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        resetDensity();
    }

    public void resetDensity(){
        Point size = new Point();
        ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        getResources().getDisplayMetrics().xdpi = size.x/DESIGN_WIDTH*72f;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
//        Beta.installTinker();
//        Beta.canNotifyUserRestart = true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化上下文
        mContext = getApplicationContext();
        INSTANCE = this;
        initWeixinApi();
        initBugly();
//        resetDensity();
        new RudenessScreenHelper(this, 750).activate();
//        PasswordManager.getInstance().init(this,1);
        mDaoSession = DbManager.getInstance().upgradeDb(mContext, "pass");
        CalligraphyConfig.
                initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/NotoSansHans.otf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
        initLogger();
        mFingerprintIdentify = new FingerprintIdentify(getApplicationContext(), new BaseFingerprint.FingerprintIdentifyExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {
                Logger.d(exception.getMessage());
            }
        });

        //初始化主线程的一个handler
        mHandler = new Handler();
//        initActionButton();
    }

    private void initWeixinApi() {
        api = WXAPIFactory.createWXAPI(this, MyConstants.WEIXIN_APP_ID, true);
        api.registerApp(MyConstants.WEIXIN_APP_ID);
    }

    private void initActionButton() {
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.mipmap.finger_print);

        CircleMenu circleMenu = new CircleMenu(this);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#258CFF"), R.mipmap.icon_home)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.icon_search)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.icon_notify)
                .addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.icon_setting)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.icon_gps)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {}

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {}

        });
        FloatWindow
                .with(getApplicationContext())
                .setView(circleMenu)
                .setX(Screen.width, 0.8f)
                .setY(Screen.height, 0.3f)
                .setMoveType(MoveType.slide)
                .setMoveStyle(500, new BounceInterpolator())
                .setDesktopShow(true)
                .build();
    }

    private void initLogger() {
        Logger
                .init("Gersy")                 // defaulticon PRETTYLOGGER or use just init()
                .methodCount(5)                 // defaulticon 2
//                .hideThreadInfo()               // defaulticon shown
                .logLevel(LogLevel.FULL)        // defaulticon LogLevel.FULL
                .methodOffset(0);
    }

    private void initBugly(){

        Beta.autoInit = true;

        /**
         * true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = true;

        /**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         */
        Beta.upgradeCheckPeriod = 60 * 1000;
        /**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         */
        Beta.initDelay = 3 * 1000;
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.mipmap.lock;
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.mipmap.lock;
        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.mipmap.lock;
        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        /**
         * 已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true;
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity.class);

        /***** Bugly高级设置 *****/
        BuglyStrategy strategy = new BuglyStrategy();
//        /**
//         * 设置app渠道号
//         */
//        strategy.setAppChannel(APP_CHANNEL);

        /***** 统一初始化Bugly产品，包含Beta *****/
        Bugly.init(this, "83a16f6a72", true, strategy);
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        mDaoSession = daoSession;
    }
}
