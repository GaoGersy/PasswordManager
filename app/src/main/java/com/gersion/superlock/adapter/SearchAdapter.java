package com.gersion.superlock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseRVAdapter;
import com.gersion.superlock.base.BaseViewHolder;
import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.listener.OnItemClickListener;
import com.gersion.superlock.view.smartRecycleView.IRVAdapter;
import com.ramotion.foldingcell.FoldingCell;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashSet;
import java.util.List;

public class SearchAdapter extends BaseRVAdapter<DbBean> implements IRVAdapter<DbBean> {

    private OnItemClickListener mOnItemClickListener;

    public SearchAdapter(List<DbBean> data) {
        super(data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    protected BaseViewHolder setViewHolder(View view) {
        return new SeachViewHolder(view);
    }

    @Override
    protected int setResourseId() {
        return R.layout.item_search;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void setNewData(List<DbBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void addData(List<DbBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<DbBean> data) {
        mData.removeAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void remove(DbBean data) {
        mData.remove(data);
        notifyDataSetChanged();
    }

    @Override
    public List<DbBean> getData() {
        return mData;
    }

    class SeachViewHolder extends BaseViewHolder<DbBean> implements View.OnClickListener {

        private TextView mTvPassword;
        private TextView mTvName;
        private TextView mTvTitle;

        public SeachViewHolder(View itemView) {
            super(itemView);
        }

        protected <T extends View> T findView(int id){
            return (T) itemView.findViewById(id);
        }

        @Override
        protected void initView(View itemView) {
            itemView.setOnClickListener(this);
            mTvName = findView(R.id.tv_name);
            mTvTitle = findView(R.id.tv_title);
            mTvPassword = findView(R.id.tv_password);
        }

        @Override
        public void setData(DbBean dbBean) {
            mTvName.setText("用户名："+dbBean.getName());
            mTvTitle.setText(dbBean.getAddress());
            mTvPassword.setText("密码："+dbBean.getPwd());
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }
    }

}
