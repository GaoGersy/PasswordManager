package com.gersion.superlock.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.app.SuperLockApplication;
import com.gersion.superlock.base.BaseActivity;
import com.gersion.superlock.bean.ExtraOptionBean;
import com.gersion.superlock.utils.ClipBoardUtils;
import com.gersion.superlock.utils.ToastUtils;
import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by gersy on 2018/4/21.
 */

public class PasswordItemAdapter extends MultiTypeAdapter<ExtraOptionBean, Object> {

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ExtraOptionBean bean) {
        baseViewHolder.setText(R.id.tv_name,bean.getKey());
        final TextView tvValue = (TextView) baseViewHolder.getView(R.id.tv_value);
        tvValue.setText(bean.getValue());
        ImageView ivCopy = (ImageView) baseViewHolder.getView(R.id.iv_copy);
        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = tvValue.getText().toString().trim();
                ClipBoardUtils.copy(SuperLockApplication.getContext(),value);
                ToastUtils.showTasty((BaseActivity)tvValue.getContext(),"已复制到剪贴板", TastyToast.SUCCESS);
            }
        });
    }
}
