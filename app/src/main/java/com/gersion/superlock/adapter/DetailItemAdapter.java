package com.gersion.superlock.adapter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.activity.AddPasswordActivity;
import com.gersion.superlock.activity.MainActivity;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.fragment.HomeFragment;
import com.gersion.superlock.utils.ToastUtils;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.orhanobut.logger.Logger;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by aa326 on 2018/1/13.
 */

public class DetailItemAdapter extends MultiTypeAdapter<ItemBean, Object> {
    private static int[][] sPalettes = new int[][]{
            {0xFFF98989, 0xFFE03535},
            {0xFFC1E480, 0xFF67CC34},
            {0xFFEDF179, 0xFFFFB314},
            {0xFF80DDE4, 0xFF286EDC},
            {0xFFE480C6, 0xFFDC285E},
    };
    private HomeFragment mHomeFragment;
    private OnItemClickListener mItemClickListener;
    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ItemBean bean) {
        switch (bean.getLayoutId()) {
//            case R.layout.item_detail_del:
//                TextView tvDel = (TextView) baseViewHolder.getView(R.id.tv_del);
//                tvDel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DbManager.getInstance().deleteById(bean.getId());
//                    }
//                });
//                break;
            case R.layout.item_detail_home:
                baseViewHolder.setText(R.id.tv_name, bean.getName());
                baseViewHolder.setText(R.id.tv_icon, bean.getAddress());
                FrameLayout flContainer = (FrameLayout) baseViewHolder.getView(R.id.fl_container);
                flContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mItemClickListener.onClik(v);
                        }
                    }
                });
                break;
            case R.layout.item_detail_password:
                baseViewHolder.setText(R.id.tv_name, bean.getName());
                final TextView tvPassword = (TextView) baseViewHolder.getView(R.id.et_password);
                tvPassword.setText(bean.getPwd());
                TextView tvDel = (TextView) baseViewHolder.getView(R.id.tv_del);
                TextView tvEdit = (TextView) baseViewHolder.getView(R.id.tv_edit);
                tvDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MainActivity mainActivity = (MainActivity) baseViewHolder.getConvertView().getContext();
                        StyledDialog.buildIosAlert(mainActivity, "确认删除这条密码吗？", "删除后将不能恢复，请谨慎删除！", new MyDialogListener() {
                            @Override
                            public void onFirst() {
                                DbManager.getInstance().deleteById(bean.getId());
                                ToastUtils.showTasty(mainActivity, "删除成功", TastyToast.SUCCESS);
                            }

                            @Override
                            public void onSecond() {

                            }
                        }).show();

                    }
                });
                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity mainActivity = (MainActivity) baseViewHolder.getConvertView().getContext();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("itemBean",bean);
                        Logger.e(bean.getNotes());
                        mainActivity.toActivity(AddPasswordActivity.class,bundle);
                    }
                });
                break;
            case R.layout.item_detail_notice:
                String notes = bean.getNotes();
                if (TextUtils.isEmpty(notes)) {
                    notes = "没有任何备注信息";
                }
                baseViewHolder.setText(R.id.tv_notice, notes);
                break;
            default:
                break;
        }
    }

    public interface OnItemClickListener {
        void onClik(View view);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {

        mItemClickListener = itemClickListener;
    }

    public void setFragment(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }
}
