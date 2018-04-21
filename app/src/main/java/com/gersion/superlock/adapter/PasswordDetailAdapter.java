package com.gersion.superlock.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.ExtraOptionBean;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.utils.GsonHelper;

import java.util.ArrayList;
import java.util.List;

public class PasswordDetailAdapter extends MultiTypeAdapter<PasswordData, Object> {

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final PasswordData bean) {
        String extraOptions = bean.getExtraOptions();
        List<ExtraOptionBean> list =new ArrayList<>();
        list.add(new ExtraOptionBean("用户名",bean.getName()));
        list.add(new ExtraOptionBean("密码",bean.getPwd()));
        list.add(new ExtraOptionBean("备注",bean.getNotes()));
        List<ExtraOptionBean> extraList = getExtraList(extraOptions);
        if (extraList!=null) {
            list.addAll(extraList);
        }
        PasswordItemAdapter adapter = new PasswordItemAdapter();
        adapter.registerMultiBean(ExtraOptionBean.class,R.layout.view_detail);
        adapter.setItems(list);
        RecyclerView recyclerView = (RecyclerView) baseViewHolder.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    public List<ExtraOptionBean> getExtraList(String data){
        if (TextUtils.isEmpty(data)){
            return null;
        }else {
            List<ExtraOptionBean> extraOptionBeans = GsonHelper.toList(data, ExtraOptionBean.class);
            return extraOptionBeans;
        }
    }

}
