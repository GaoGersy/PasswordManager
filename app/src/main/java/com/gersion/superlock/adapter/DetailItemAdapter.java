package com.gersion.superlock.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.activity.AddPasswordActivity;
import com.gersion.superlock.activity.MainActivity;
import com.gersion.superlock.activity.PasswordDetailActivity;
import com.gersion.superlock.bean.ExtraOptionBean;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.db.DbManager;
import com.gersion.superlock.utils.GsonHelper;
import com.gersion.superlock.utils.ToastUtils;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

/**
 * Created by aa326 on 2018/1/13.
 */

public class DetailItemAdapter extends MultiTypeAdapter<ItemBean, Object> {
    private OnItemClickListener mItemClickListener;
    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ItemBean bean) {
        switch (bean.getLayoutId()) {
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
                ImageView ivInfo = (ImageView) baseViewHolder.getView(R.id.iv_info);
                ivInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity mainActivity = (MainActivity) baseViewHolder.getConvertView().getContext();
                        Bundle bundle = new Bundle();
                        bundle.putLong("id",bean.getId());
                        mainActivity.toActivity(PasswordDetailActivity.class,bundle);
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
            case R.layout.item_detail_more:
                TextView tvNoData = (TextView) baseViewHolder.getView(R.id.tv_no_data);
                LinearLayout container = (LinearLayout) baseViewHolder.getView(R.id.container);
                Context context = baseViewHolder.getConvertView().getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                String extraOptions = bean.getExtraOptions();
                if (extraOptions!=null){
                    List<ExtraOptionBean> extraOptionBeans = GsonHelper.toList(extraOptions, ExtraOptionBean.class);
                    if (extraOptionBeans!=null) {
                        tvNoData.setVisibility(View.GONE);
                        for (ExtraOptionBean extraOptionBean : extraOptionBeans) {
                            TextView view = (TextView) inflater.inflate(R.layout.view_more_item, null);
                            view.setText(extraOptionBean.getKey() + " ： " + extraOptionBean.getValue());
                            container.addView(view);
                        }
                    }
                }
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

}
