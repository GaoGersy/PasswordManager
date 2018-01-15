package com.gersion.superlock.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.db.DbManager;

/**
 * Created by aa326 on 2018/1/13.
 */

public class DetailItemAdapter extends MultiTypeAdapter<ItemBean,Object> {
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final ItemBean bean) {
        switch (bean.getLayoutId()){
            case R.layout.item_detail_del:
                TextView tvDel = (TextView) baseViewHolder.getView(R.id.tv_del);
                tvDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DbManager.getInstance().deleteById(bean.getId());
                    }
                });
                break;
            case R.layout.item_detail_home:
                baseViewHolder.setText(R.id.tv_name,bean.getName());
                baseViewHolder.setText(R.id.tv_icon,bean.getAddress());
                break;
            case R.layout.item_detail_password:
                baseViewHolder.setText(R.id.tv_name,bean.getName());
                final EditText etPassword = (EditText) baseViewHolder.getView(R.id.et_password);
                etPassword.setText(bean.getPwd());
                ImageView ivEdit = (ImageView) baseViewHolder.getView(R.id.iv_edit);
                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etPassword.setEnabled(true);
                        etPassword.requestFocus();
                    }
                });
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
