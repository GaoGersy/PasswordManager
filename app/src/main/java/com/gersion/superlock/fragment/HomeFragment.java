package com.gersion.superlock.fragment;

import android.os.Bundle;

import com.gersion.superlock.R;
import com.gersion.superlock.adapter.PasswordShowAdapter;
import com.gersion.superlock.base.BaseFragment;
import com.gersion.superlock.bean.PasswordBean;
import com.gersion.superlock.controller.MessageEvent;
import com.gersion.superlock.dao.PasswordDao;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.listener.OnItemClickListener;
import com.gersion.superlock.utils.ToastUtils;
import com.gersion.superlock.view.smartRecycleView.PullToRefreshLayout;
import com.gersion.superlock.view.smartRecycleView.SmartRecycleView;
import com.sdsmdg.tastytoast.TastyToast;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends BaseFragment {
    public static final String TOTAL_COUNT = "total_count";
    private PasswordDao mDao;
    private PasswordShowAdapter mPasswordShowAdapter;
    private List<PasswordBean> mDataList = new ArrayList<>();
    private SmartRecycleView mSmartRecycleView;
    /**
     * 当Item移动的时候。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            Collections.swap(mDataList, fromPosition, toPosition);
            mPasswordShowAdapter.notifyItemMoved(fromPosition, toPosition);
            DbManager.getInstance().swap(fromPosition,toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(int position) {
            mDataList.remove(position);
            mPasswordShowAdapter.notifyItemRemoved(position);
        }

    };

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mSmartRecycleView = findView(R.id.smartRecycleView);
        init();
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void setClickListener() {

    }

    private void init() {
        SwipeMenuRecyclerView menuRecyclerView = mSmartRecycleView.getRecyclerView();
        mPasswordShowAdapter = new PasswordShowAdapter(menuRecyclerView, mDataList);
        mSmartRecycleView
                .setAutoRefresh(true)
                .setAdapter(mPasswordShowAdapter)
                .loadMoreEnable(false)
                .refreshEnable(true)
                .setLayoutManger(SmartRecycleView.LayoutManagerType.LINEAR_LAYOUT)
                .setRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh(int page) {
                        //TODO
                        initData();
                    }

                    @Override
                    public void onLoadMore(final int page) {
                    }
                });

        // 触摸拖拽的代码在Adapter中：SwipeMenuRecyclerView#startDrag(ViewHolder);
//        mDragAdapter.setOnItemClickListener(onItemClickListener);
        mPasswordShowAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        menuRecyclerView.setLongPressDragEnabled(true); // 开启拖拽。
        menuRecyclerView.setItemViewSwipeEnabled(false); // 开启滑动删除。
        menuRecyclerView.setOnItemMoveListener(onItemMoveListener);// 监听拖拽，更新UI。
//        menuRecyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener);
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
    public void onDestroy() {
        super.onDestroy();
        DbManager.getInstance().destroy();
//        mDao.destory();
    }

    //初始化数据
    private void initData() {
        getDataFromDB();
    }

    private void getDataFromDB() {
//        for (int i = 0; i < 20; i++) {
//            PasswordBean bean = new PasswordBean();
//            bean.setIndex(i);
//            bean.setAddress("地址" + i);
//            bean.setCreateTime(TimeUtils.getCurrentTimeInString());
//            bean.setName("名称" + i);
//            bean.setNotes("备注" + i);
//            bean.setPwd("密码" + i);
//            mDataList.add(bean);
//            DbManager.getInstance().add(bean);
//        }
        List<PasswordBean> passwordBeans = DbManager.getInstance().load();
        mDataList.addAll(passwordBeans);
        mSmartRecycleView.handleData(mDataList);
    }

//    private void initHandle(int i) {
//        mList = mDao.query();
//        for (; i < mList.size(); i++) {
//            Keyer keyer = mList.get(i);
//            final ChildView childView = new ChildView();
//            childView.init(getActivity(), keyer);
//
//            int size = UIUtils.dp2Px(150);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size);
//            params.topMargin = 20;
//            params.leftMargin = 20;
//            params.rightMargin = 20;
//            childView.viewPager.setLayoutParams(params);
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mLlContainer.addView(childView.viewPager);
//                }
//            });
//        }
//    }
//
//    private void addView(Keyer keyer) {
//        ChildView childView = new ChildView();
//        childView.init(getActivity(), keyer);
//
//        int size = UIUtils.dp2Px(150);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size);
//        params.topMargin = 20;
//        params.leftMargin = 20;
//        params.rightMargin = 20;
//        childView.viewPager.setLayoutParams(params);
//        mLlContainer.addView(childView.viewPager);
//    }
//
//    private void initHandle() {
//        mLlContainer.removeAllViews();
//        Observable.just(mDao.query())
//                .flatMap(new Func1<List<Keyer>, Observable<Keyer>>() {
//                    @Override
//                    public Observable<Keyer> call(List<Keyer> keyers) {
//                        return Observable.from(keyers);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Keyer>() {
//                    @Override
//                    public void call(Keyer keyer) {
//                        Logger.d(keyer.number +"");
//                        addView(keyer);
//                        mTotal_count++;
//                    }
//                });
//    }
//
//    //初始化事件监听
//    private void initEvent() {
//    }
//
//
//    public void paowuxian(View view) {
//        ValueAnimator valueAnimator = new ValueAnimator();
//        valueAnimator.setDuration(3000);
//        valueAnimator.setObjectValues(new PointF(0, 0));
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
//            // fraction = t / duration
//            @Override
//            public PointF evaluate(float fraction, PointF startValue,
//                                   PointF endValue) {
//                // x方向200px/s ，则y方向0.5 * 10 * t
//                PointF point = new PointF();
//                point.x = 200 * fraction * 3;
//                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
//                return point;
//            }
//        });
//
//        valueAnimator.start();
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                PointF point = (PointF) animation.getAnimatedValue();
//                toggleButton.setX(point.x);
//                toggleButton.setY(point.y);
//
//            }
//
//        });
//        valueAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                toggleButton.setX(0);
//                toggleButton.setY(0);
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        materialSheetFab.hideSheet();
//        switch (v.getId()) {
//            case R.id.fab_sheet_item_add:
//                Intent intent = new Intent(getActivity(), AddPasswordActivity.class);
//                Logger.d(mTotal_count);
//                intent.putExtra(TOTAL_COUNT, mList.size()+"");
//                startActivity(intent);
//                break;
//            case R.id.fab_sheet_item_change_pwd:
//                startActivity(new Intent(getActivity(), RegesterActivity.class));
////                mActivity.finish();
//                break;
//            case R.id.fab_sheet_item_backup:
//                if (backupSqlData()) {
//                    ToastUtils.showTasty(mActivity, "恭喜，备份成功", TastyToast.SUCCESS);
//                } else {
//                    ToastUtils.showTasty(mActivity, "真可惜，备份失败了", TastyToast.ERROR);
//                }
//                break;
//            case R.id.fab_sheet_item_setting:
//                startActivity(new Intent(getActivity(), SettingActivity.class));
//                break;
//            case R.id.fab_sheet_item_upload:
//                if (loadSqlData()) {
//
//                    try {
//                        mLlContainer.removeAllViews();
//                        initHandle(0);
//                        ToastUtils.showTasty(mActivity, "恭喜，载入成功", TastyToast.SUCCESS);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        ToastUtils.showTasty(mActivity, "载入的数据与当前主密码不匹配", TastyToast.WARNING);
//                    }
//                } else {
//                    ToastUtils.showTasty(mActivity, "真可惜，载入失败了", TastyToast.ERROR);
//                }
//                break;
//        }
//    }
//
//    private boolean loadSqlData() {
//        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
//        if (!sdCardEnable) {
//            ToastUtils.showTasty(mActivity, "没有SD卡，无法读取备份的文件", TastyToast.WARNING);
//            return false;
//        }
//
//        File sqlPath = new File(SDCardUtils.getSDCardPath() + "biabia.db");
//        if (!sqlPath.exists()) {
//            ToastUtils.showTasty(mActivity, "没有找到任何备份了的文件", TastyToast.WARNING);
//            return false;
//        }
//
//        boolean isSaved = false;
//        try {
//            FileInputStream fis = new FileInputStream(sqlPath);
//            String path = getActivity().getDatabasePath("biabia.db").getAbsolutePath();
//            Logger.d(path);
//            File file = new File(path);
//            if (file.exists()) {
//                file.delete();
//            }
//            byte[] buffer = new byte[1];
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            int len;
//            while ((len = bis.read(buffer)) != -1) {
//                bos.write(buffer);
//            }
//            bis.close();
//            bos.close();
//            isSaved = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return isSaved;
//    }
//
//    private boolean backupSqlData() {
//        boolean sdCardEnable = SDCardUtils.isSDCardEnable();
//        if (!sdCardEnable) {
//            ToastUtils.showTasty(mActivity, "没有SD卡，备份工作无法继续进行", TastyToast.WARNING);
//            return false;
//        }
//
//        File sqlPath = SqliteUtils.getSqlPath(getActivity(), SqlPassword.DB_NAME);
//        if (!sqlPath.exists()) {
//            ToastUtils.showTasty(mActivity, "还没有任何数据，不需要备份", TastyToast.WARNING);
//            return false;
//        }
//
//        if (SDCardUtils.getSDCardAllSize() < sqlPath.length()) {
//            ToastUtils.showTasty(mActivity, "SD卡剩余容量不足，无法备份", TastyToast.WARNING);
//            return false;
//        }
//
//        boolean isSaved = false;
//        try {
//            FileInputStream fis = new FileInputStream(sqlPath);
//            String path = SDCardUtils.getSDCardPath() + "biabia.db";
//            File file = new File(path);
//            if (file.exists()) {
//                file.delete();
//            }
//            byte[] buffer = new byte[1];
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            BufferedInputStream bis = new BufferedInputStream(fis);
//            int len;
//            while ((len = bis.read(buffer)) != -1) {
//                bos.write(buffer);
//            }
//            bis.close();
//            bos.close();
//            isSaved = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return isSaved;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
