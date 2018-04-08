package com.gersion.superlock.adapter;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.view.DynamicDataView;

public class PasswordDetailAdapter extends MultiTypeAdapter<PasswordData, Object> {

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final PasswordData bean) {
        baseViewHolder.setText(R.id.tv_name, "用户名："+bean.getName());
        baseViewHolder.setText(R.id.tv_password, "密码："+bean.getPwd());
        baseViewHolder.setText(R.id.tv_notes, "备注："+bean.getNotes());
        baseViewHolder.setText(R.id.tv_address, bean.getAddress());
        DynamicDataView dynamicDataView = (DynamicDataView) baseViewHolder.getView(R.id.dynamicDataView);
        String extraOptions = bean.getExtraOptions();

        dynamicDataView.setData(extraOptions);
    }

}
