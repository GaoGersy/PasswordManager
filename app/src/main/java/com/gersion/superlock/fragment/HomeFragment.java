package com.gersion.superlock.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.activity.AddPasswordActivity;
import com.gersion.superlock.adapter.ContentListAdapter;
import com.gersion.superlock.base.BaseFragment;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.db.BaseDbManager;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private ContentListAdapter mPasswordShowAdapter;
    private List<PasswordData> mDataList = new ArrayList<>();
    private SwipeMenuRecyclerView mRecyclerView;
    /**
     * 当Item移动的时候。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = viewHolder1.getAdapterPosition();
            Collections.swap(mDataList, fromPosition, toPosition);
            mPasswordShowAdapter.notifyItemMoved(fromPosition, toPosition);
            long fromIndex = mDataList.get(fromPosition).getIndex();
            long toIndex = mDataList.get(toPosition).getIndex();
            DbManager.getInstance().registerDataChangeListener(null);
            DbManager.getInstance().swap(fromIndex,toIndex);
            DbManager.getInstance().registerDataChangeListener(dataChangeListener);
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder viewHolder) {
            int position = viewHolder.getAdapterPosition();
            mDataList.remove(position);
            mPasswordShowAdapter.notifyItemRemoved(position);
        }


    };
    private TextView mTvAdd;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mRecyclerView = findView(R.id.smartRecycleView);
        mTvAdd = findView(R.id.tv_add);
        init();
    }

    @Override
    protected void initData(Bundle bundle) {
        getDataFromDB();
    }

    @Override
    protected void initListener() {
        mPasswordShowAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                PasswordData passwordData = mDataList.get(position);
                startTrainsition(view, passwordData);
            }
        });

        mTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(AddPasswordActivity.class);
            }
        });

        DbManager.getInstance().registerDataChangeListener(dataChangeListener);
    }

    DbManager.OnDataChangeCallback<PasswordData> dataChangeListener = new BaseDbManager.OnDataChangeCallback<PasswordData>() {
        @Override
        public void onDataChange(List<PasswordData> list) {
            mDataList.clear();
            mDataList.addAll(list);
            mPasswordShowAdapter.setNewData(mDataList);
        }
    };

    private void init() {
        mPasswordShowAdapter = new ContentListAdapter(mRecyclerView, mDataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mPasswordShowAdapter);

        // 触摸拖拽的代码在Adapter中：SwipeMenuRecyclerView#startDrag(ViewHolder);
//        mDragAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerView.setLongPressDragEnabled(true); // 开启拖拽。
        mRecyclerView.setItemViewSwipeEnabled(false); // 开启滑动删除。
        mRecyclerView.setOnItemMoveListener(onItemMoveListener);// 监听拖拽，更新UI。
//        menuRecyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DbManager.getInstance().unregisterDataChangeListener(dataChangeListener);
    }

    private void getDataFromDB() {

        final List<PasswordData> passwordBeans = DbManager.getInstance().queryAll();
        mDataList.clear();
        mDataList.addAll(passwordBeans);
        mPasswordShowAdapter.setNewData(mDataList);
    }

    private void startTrainsition(View view, PasswordData bean){
//        Intent intent = DetailActivity.getDetailIntent(getActivity(), bean.getId());
//
//        EasyTransitionOptions options =
//                EasyTransitionOptions.makeTransitionOptions(
//                        getActivity(),
//                        view.findViewById(R.id.piv_icon),
//                        view.findViewById(R.id.tv_title));
////                        findViewById(R.id.v_top_card));
//
//        EasyTransition.startActivity(intent, options);
    }

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
//                startActivity(new Intent(getActivity(), RegisterActivity.class));
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
