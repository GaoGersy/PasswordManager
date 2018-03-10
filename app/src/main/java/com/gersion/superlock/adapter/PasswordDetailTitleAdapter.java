package com.gersion.superlock.adapter;

import android.view.View;
import android.widget.FrameLayout;

import com.gersion.library.adapter.MultiTypeAdapter;
import com.gersion.library.viewholder.BaseViewHolder;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.ItemBean;

public class PasswordDetailTitleAdapter extends MultiTypeAdapter<ItemBean, Object> {
    private OnItemClickListener mItemClickListener;

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final ItemBean bean) {
        baseViewHolder.setText(R.id.tv_name, bean.getName());
        baseViewHolder.setText(R.id.tv_icon, bean.getAddress());
        FrameLayout flContainer = (FrameLayout) baseViewHolder.getView(R.id.fl_container);
        flContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onClik(v,bean);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onClik(View view,ItemBean bean);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {

        mItemClickListener = itemClickListener;
    }
}
