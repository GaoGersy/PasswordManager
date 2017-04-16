package com.gersion.superlock.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.activity.AddPasswordActivity;
import com.gersion.superlock.activity.MainActivity;
import com.gersion.superlock.activity.RegesterActivity;
import com.gersion.superlock.activity.SettingActivity;
import com.gersion.superlock.adapter.MainAdapter;
import com.gersion.superlock.bean.Keyer;
import com.gersion.superlock.controller.ChildView;
import com.gersion.superlock.controller.MessageEvent;
import com.gersion.superlock.dao.PasswordDao;
import com.gersion.superlock.dao.SqlPassword;
import com.gersion.superlock.utils.SDCardUtils;
import com.gersion.superlock.utils.SqliteUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.utils.UIUtils;
import com.gersion.superlock.view.FabMenu;
import com.gersion.superlock.view.MyToggleButton;
import com.gersion.toastlibrary.TastyToast;
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.fragment
 * @待完成
 * @创建时间 2016/9/6
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String TOTAL_COUNT = "total_count";
    public int mTotal_count = 0;
    RecyclerView mRvDetail;
    @Bind(R.id.ll_container)
    LinearLayout mLlContainer;
    @Bind(R.id.overlay)
    DimOverlayFrameLayout mOverlay;
    @Bind(R.id.fab)
    FabMenu mFab;
    @Bind(R.id.fab_sheet)
    CardView mFabSheet;
    @Bind(R.id.fab_sheet_item_add)
    TextView mFabSheetItemAdd;
    @Bind(R.id.fab_sheet_item_backup)
    TextView mFabSheetItemBackup;
    @Bind(R.id.fab_sheet_item_change_pwd)
    TextView mFabSheetItemChangePwd;
    @Bind(R.id.fab_sheet_item_setting)
    TextView mFabSheetItemSetting;
    @Bind(R.id.fab_sheet_item_upload)
    TextView mFabSheetItemUpload;
    @Bind(R.id.sv_container)
    ScrollView mSvContainer;
    //用来记录按返回键的次数，按两次退出程序
    private int flag = 1;
    private MainAdapter mAdapter;
    private MyToggleButton toggleButton;
    private List<Keyer> mList;
    private View mView;
    private MaterialSheetFab<FabMenu> materialSheetFab;
    private PasswordDao mDao;
    private MainActivity mActivity;
    private Handler mHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, mView);
        mDao = new PasswordDao(getActivity());
        initView();

        setupFabMenu();
        mActivity = (MainActivity) getActivity();
        return mView;
    }

    @Subscribe(sticky = true)
    public void onEvent(MessageEvent event) {
        if (event.message.equals("ChangePwd")) {
            ToastUtils.showTasty(getActivity(), "主密码修改成功", TastyToast.SUCCESS);
        }
        mDao.destory();
        mDao = new PasswordDao(getActivity());
        initData();
        if (event != null) {
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    //初始化控件
    private void initView() {
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }).start();
        initEvent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDao.destory();
    }

    //初始化数据
    private void initData() {
        if (mList == null) {
            initHandle(0);
        } else {
            if (mDao.query().size() > mList.size()) {
                initHandle(mList.size());
            }
        }
//        initHandle();

    }

    private void initHandle(int i) {
        mList = mDao.query();
        for (; i < mList.size(); i++) {
            Keyer keyer = mList.get(i);
            final ChildView childView = new ChildView();
            childView.init(getActivity(), keyer);

            int size = UIUtils.dp2Px(150);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size);
            params.topMargin = 20;
            params.leftMargin = 20;
            params.rightMargin = 20;
            childView.viewPager.setLayoutParams(params);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mLlContainer.addView(childView.viewPager);
                }
            });
        }
    }

    private void addView(Keyer keyer) {
        ChildView childView = new ChildView();
        childView.init(getActivity(), keyer);

        int size = UIUtils.dp2Px(150);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size);
        params.topMargin = 20;
        params.leftMargin = 20;
        params.rightMargin = 20;
        childView.viewPager.setLayoutParams(params);
        mLlContainer.addView(childView.viewPager);
    }

    private void initHandle() {
        mLlContainer.removeAllViews();
        Observable.just(mDao.query())
                .flatMap(new Func1<List<Keyer>, Observable<Keyer>>() {
                    @Override
                    public Observable<Keyer> call(List<Keyer> keyers) {
                        return Observable.from(keyers);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Keyer>() {
                    @Override
                    public void call(Keyer keyer) {
                        Logger.d(keyer.number +"");
                        addView(keyer);
                        mTotal_count++;
                    }
                });
    }

    //初始化事件监听
    private void initEvent() {
        mFabSheetItemAdd.setOnClickListener(this);
        mFabSheetItemBackup.setOnClickListener(this);
        mFabSheetItemChangePwd.setOnClickListener(this);
        mFabSheetItemSetting.setOnClickListener(this);
        mFabSheetItemUpload.setOnClickListener(this);
    }


    public void paowuxian(View view) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                // x方向200px/s ，则y方向0.5 * 10 * t
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                toggleButton.setX(point.x);
                toggleButton.setY(point.y);

            }

        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                toggleButton.setX(0);
                toggleButton.setY(0);
            }
        });
    }

    /**
     * Sets up the Floating action button.
     */
    private void setupFabMenu() {

        FabMenu fabMenu = (FabMenu) mView.findViewById(R.id.fab);
        View sheetView = mView.findViewById(R.id.fab_sheet);
        View overlay = mView.findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background_card);
        int FabMenuColor = getResources().getColor(R.color.theme_accent);

        // Create material sheet FabMenu
        materialSheetFab = new MaterialSheetFab<>(fabMenu, sheetView, overlay, sheetColor, FabMenuColor);

    }

    @Override
    public void onClick(View v) {
        materialSheetFab.hideSheet();
        switch (v.getId()) {
            case R.id.fab_sheet_item_add:
                Intent intent = new Intent(getActivity(), AddPasswordActivity.class);
                Logger.d(mTotal_count);
                intent.putExtra(TOTAL_COUNT, mList.size()+"");
                startActivity(intent);
                break;
            case R.id.fab_sheet_item_change_pwd:
                startActivity(new Intent(getActivity(), RegesterActivity.class));
//                mActivity.finish();
                break;
            case R.id.fab_sheet_item_backup:
                if (backupSqlData()) {
                    ToastUtils.showTasty(mActivity, "恭喜，备份成功", TastyToast.SUCCESS);
                } else {
                    ToastUtils.showTasty(mActivity, "真可惜，备份失败了", TastyToast.ERROR);
                }
                break;
            case R.id.fab_sheet_item_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.fab_sheet_item_upload:
                if (loadSqlData()) {

                    try {
                        mLlContainer.removeAllViews();
                        initHandle(0);
                        ToastUtils.showTasty(mActivity, "恭喜，载入成功", TastyToast.SUCCESS);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.showTasty(mActivity, "载入的数据与当前主密码不匹配", TastyToast.WARNING);
                    }
                } else {
                    ToastUtils.showTasty(mActivity, "真可惜，载入失败了", TastyToast.ERROR);
                }
                break;
        }
    }

    private boolean loadSqlData() {
        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
        if (!sdCardEnable) {
            ToastUtils.showTasty(mActivity, "没有SD卡，无法读取备份的文件", TastyToast.WARNING);
            return false;
        }

        File sqlPath = new File(SDCardUtils.getSDCardPath() + "biabia.db");
        if (!sqlPath.exists()) {
            ToastUtils.showTasty(mActivity, "没有找到任何备份了的文件", TastyToast.WARNING);
            return false;
        }

        boolean isSaved = false;
        try {
            FileInputStream fis = new FileInputStream(sqlPath);
            String path = getActivity().getDatabasePath("biabia.db").getAbsolutePath();
            Logger.d(path);
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            byte[] buffer = new byte[1];
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            BufferedInputStream bis = new BufferedInputStream(fis);
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer);
            }
            bis.close();
            bos.close();
            isSaved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    private boolean backupSqlData() {
        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
        if (!sdCardEnable) {
            ToastUtils.showTasty(mActivity, "没有SD卡，备份工作无法继续进行", TastyToast.WARNING);
            return false;
        }

        File sqlPath = SqliteUtils.getSqlPath(getActivity(), SqlPassword.DB_NAME);
        if (!sqlPath.exists()) {
            ToastUtils.showTasty(mActivity, "还没有任何数据，不需要备份", TastyToast.WARNING);
            return false;
        }

        if (SDCardUtils.getSDCardAllSize() < sqlPath.length()) {
            ToastUtils.showTasty(mActivity, "SD卡剩余容量不足，无法备份", TastyToast.WARNING);
            return false;
        }

        boolean isSaved = false;
        try {
            FileInputStream fis = new FileInputStream(sqlPath);
            String path = SDCardUtils.getSDCardPath() + "biabia.db";
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            byte[] buffer = new byte[1];
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            BufferedInputStream bis = new BufferedInputStream(fis);
            int len;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer);
            }
            bis.close();
            bos.close();
            isSaved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSaved;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
