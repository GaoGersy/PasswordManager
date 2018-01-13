package com.gersion.superlock.adapter;

import android.text.TextUtils;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.ItemBean;

/**
 * Created by aa326 on 2018/1/13.
 */

public class DetailItemAdapter extends MultiTypeAdapter<ItemBean,Object> {
    @Override
    protected void convert(BaseViewHolder baseViewHolder, ItemBean bean) {
        switch (bean.getLayoutId()){
            case R.layout.item_detail_del:
                break;
            case R.layout.item_detail_home:
                baseViewHolder.setText(R.id.tv_name,bean.getName());
                break;
            case R.layout.item_detail_password:
                baseViewHolder.setText(R.id.tv_name,bean.getName());
                baseViewHolder.setText(R.id.et_password,bean.getPwd());
                break;
            case R.layout.item_detail_notice:
                String notes = bean.getNotes();
                if (TextUtils.isEmpty(notes)){
                    notes="没有任何备注信息";
                }
                baseViewHolder.setText(R.id.tv_notice, notes);
                break;
            default:
                break;
        }
    }
}
