package com.gersion.superlock.adapter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.activity.RegisterActivity;
import com.gersion.superlock.activity.SelectLockTypeActivity;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.sdsmdg.tastytoast.TastyToast;

public class LockTypeDetailAdapter extends MultiTypeAdapter<String, Object> {
    private OnItemClickListener mItemClickListener;

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final String name) {
        TextView tvEdit = (TextView) baseViewHolder.getView(R.id.tv_edit);
        TextView tvDel = (TextView) baseViewHolder.getView(R.id.tv_del);
        TextView tvSelect = (TextView) baseViewHolder.getView(R.id.tv_select);
        final ConfigManager configManager = ConfigManager.getInstance();
        tvDel.setText("删除图案密码");
        int lockType = 0;
        String msg = null;
        String selectTitle = null;
        switch (name) {
            case "密码解锁":
                msg = "已修改为密码解锁";
                selectTitle = "修改为密码解锁";
                lockType = 0;
                tvEdit.setText("修改密码");
                break;
            case "图案解锁":
                msg = "已修改为图案解锁";
                selectTitle = "修改为图案解锁";
                lockType = 1;
                if (configManager.getPatternString() != null) {
                    tvEdit.setText("修改图案密码");
                    tvDel.setVisibility(View.VISIBLE);
                    tvSelect.setVisibility(View.VISIBLE);
                } else {
                    tvEdit.setText("创建图案密码");
                    tvDel.setVisibility(View.GONE);
                    tvSelect.setVisibility(View.GONE);
                }
                break;
            case "指纹解锁":
                msg = "已修改为指纹解锁";
                selectTitle = "修改为指纹解锁";
                lockType = 2;
                tvEdit.setVisibility(View.GONE);
                tvDel.setVisibility(View.GONE);
                break;
        }
        tvSelect.setText(selectTitle);
        final int finalLockType = lockType;
        final SelectLockTypeActivity activity = (SelectLockTypeActivity) baseViewHolder.getConvertView().getContext();
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patternString = configManager.getPatternString();
                Bundle bundle = new Bundle();
                Logger.e(finalLockType+"");
                bundle.putInt("lockType", finalLockType);
                if (finalLockType == MyConstants.LockType.TYPE_PATTERN && TextUtils.isEmpty(patternString)) {
                    bundle.putBoolean("reset", false);
                    activity.toActivityForResult(RegisterActivity.class, bundle, activity.CODE);
                } else {
                    bundle.putBoolean("reset", true);
                    activity.toActivity(RegisterActivity.class, bundle);
                }
            }
        });
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configManager.setPatternString(null);
                ToastUtils.showTasty(activity, "删除图案密码成功", TastyToast.SUCCESS);
            }
        });

        final String finalMsg = msg;
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configManager.setLockType(finalLockType);
                ToastUtils.showTasty(activity, finalMsg, TastyToast.SUCCESS);
            }
        });
    }

    public interface OnItemClickListener {
        void onClik(View view, PasswordData bean);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {

        mItemClickListener = itemClickListener;
    }
}
