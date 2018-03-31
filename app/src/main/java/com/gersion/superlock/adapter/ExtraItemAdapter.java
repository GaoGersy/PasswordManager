package com.gersion.superlock.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.ExtraOptionBean;

import java.util.List;

/**
 * Created by aa326 on 2018/1/13.
 */

public class ExtraItemAdapter extends MultiTypeAdapter<ExtraOptionBean, Object> {
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final ExtraOptionBean extraOptionBean) {
        String title = extraOptionBean.getKey();
        String content = extraOptionBean.getValue();
        final EditText et_title = (EditText) baseViewHolder.getView(R.id.et_title);
        final EditText et_content = (EditText) baseViewHolder.getView(R.id.et_content);
        et_title.setText(title);
        et_content.setText(content);
        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String title = et_title.getText().toString().trim();
                extraOptionBean.setKey(title);
            }
        });
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = et_content.getText().toString().trim();
                extraOptionBean.setValue(content);
            }
        });
    }


    public void add(ExtraOptionBean bean){
        items.add(bean);
        notifyItemInserted(items.size()-1);
    }

    public void remove(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(List<ExtraOptionBean> list){
        items.addAll(list);
        notifyDataSetChanged();
    }
}
