package com.gersion.superlock.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.IconBean;

/**
 * Created by aa326 on 2018/1/13.
 */

public class IconListAdapter extends MultiTypeAdapter<IconBean,Object> {
    private CheckBox currentCheckBox;
    private IconBean mIconBean;
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final IconBean iconBean) {
        ImageView ivIcon = (ImageView) baseViewHolder.getView(R.id.iv_icon);
        ivIcon.setImageResource(iconBean.getResourceId());

        RelativeLayout view = (RelativeLayout) baseViewHolder.getView(R.id.rl_container);
        final CheckBox checkbox = (CheckBox) baseViewHolder.getView(R.id.checkbox);
        if (iconBean.isSelected()){
            currentCheckBox = checkbox;
            mIconBean = iconBean;
        }
        checkbox.setChecked(iconBean.isSelected());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(checkbox, iconBean);
            }
        });
    }

    private void changeStatus(CheckBox checkbox, IconBean bean) {
        if (currentCheckBox!=null){
            currentCheckBox.setChecked(false);
            mIconBean.setSelected(false);
        }
        checkbox.setChecked(true);
        currentCheckBox=checkbox;
        mIconBean = bean;
        mIconBean.setSelected(true);
    }

    public IconBean getIconBean() {
        return mIconBean;
    }

    public void setIconBean(IconBean bean) {
        mIconBean = bean;
    }
}
