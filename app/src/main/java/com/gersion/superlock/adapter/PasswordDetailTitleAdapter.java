package com.gersion.superlock.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.app.SuperLockApplication;
import com.gersion.superlock.bean.PasswordData;

public class PasswordDetailTitleAdapter extends MultiTypeAdapter<PasswordData, Object> {
    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final PasswordData bean) {
        baseViewHolder.setText(R.id.tv_name, bean.getName());
        baseViewHolder.setText(R.id.tv_address, bean.getAddress());
//        FrameLayout flContainer = (FrameLayout) baseViewHolder.getView(R.id.fl_container);
//        flContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mItemClickListener != null) {
//                    mItemClickListener.onClik(v,bean);
//                }
//            }
//        });
    }

    public void hightLightItem(RecyclerView.ViewHolder baseViewHolder){
        setColor((BaseViewHolder) baseViewHolder,R.color.font_yellow);
    }

    private void setColor(BaseViewHolder baseViewHolder, int colorResId) {
        BaseViewHolder viewHolder = baseViewHolder;
        TextView tvName = (TextView) viewHolder.getView(R.id.tv_name);
        TextView tvAddress = (TextView) viewHolder.getView(R.id.tv_address);
        int color = SuperLockApplication.getContext().getResources().getColor(colorResId);
        tvName.setTextColor(color);
        tvAddress.setTextColor(color);
    }

    public void lowLightItem(RecyclerView.ViewHolder currentItemHolder) {
        setColor((BaseViewHolder) currentItemHolder,R.color.item_title_font_color);
    }
}
