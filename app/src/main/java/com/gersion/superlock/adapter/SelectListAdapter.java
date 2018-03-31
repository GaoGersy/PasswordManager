package com.gersion.superlock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseRVAdapter;
import com.gersion.superlock.base.BaseViewHolder;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.view.smartRecycleView.IRVAdapter;

import java.util.List;

/**
 * Created by a3266 on 2017/6/10.
 */

public class SelectListAdapter extends BaseRVAdapter<PasswordData> implements IRVAdapter<PasswordData> {

    private OnItemClickListener mListener;

    public SelectListAdapter(List<PasswordData> data) {
        super(data);
    }

    @Override
    protected BaseViewHolder setViewHolder(View view) {
        return new SelectListViewHolder(view);
    }

    @Override
    protected int setResourseId() {
        return R.layout.item_select_list;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void setNewData(List<PasswordData> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void addData(List<PasswordData> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<PasswordData> data) {

    }

    @Override
    public void remove(PasswordData data) {

    }

    @Override
    public List<PasswordData> getData() {
        return mData;
    }

    class SelectListViewHolder extends BaseViewHolder<PasswordData>{

        private TextView mTvName;
        private TextView mTvPassword;
        private TextView mTvTitle;
        private LinearLayout mContainer;
        private PasswordData mBean;

        public SelectListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvPassword = (TextView) itemView.findViewById(R.id.tv_password);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mContainer = (LinearLayout) itemView.findViewById(R.id.container);

            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        mListener.onItemClick(mBean);
                    }
                }
            });
        }

        @Override
        public void setData(PasswordData bean) {
            mBean = bean;
            mTvName.setText(bean.getName());
            mTvPassword.setText(bean.getPwd());
            mTvTitle.setText(bean.getAddress());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(PasswordData bean);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

}
