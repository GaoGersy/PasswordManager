package com.gersion.superlock.adapter;

import android.view.View;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.DbBean;

public class PasswordDetailAdapter extends MultiTypeAdapter<DbBean, Object> {
    private OnItemClickListener mItemClickListener;

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final DbBean bean) {
        baseViewHolder.setText(R.id.tv_name, "用户名："+bean.getName());
        baseViewHolder.setText(R.id.tv_password, "密码："+bean.getPwd());
        baseViewHolder.setText(R.id.tv_notes, "备注："+bean.getNotes());
    }

    public interface OnItemClickListener {
        void onClik(View view, DbBean bean);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {

        mItemClickListener = itemClickListener;
    }
}
