package com.gersion.superlock.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.activity.GlobalSearchActivity;
import com.gersion.superlock.activity.PasswordDetailActivity;
import com.gersion.superlock.base.BaseRVAdapter;
import com.gersion.superlock.base.BaseViewHolder;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.listener.OnItemClickListener;
import com.gersion.superlock.utils.KeyboardUtils;
import com.gersion.superlock.view.smartRecycleView.IRVAdapter;

import java.util.List;

public class SearchAdapter extends BaseRVAdapter<PasswordData> implements IRVAdapter<PasswordData> {

    private OnItemClickListener mOnItemClickListener;

    public SearchAdapter(List<PasswordData> data) {
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
        mData.removeAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void remove(PasswordData data) {
        mData.remove(data);
        notifyDataSetChanged();
    }

    @Override
    public List<PasswordData> getData() {
        return mData;
    }

    class SeachViewHolder extends BaseViewHolder<PasswordData> implements View.OnClickListener {

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
            mTvName = findView(R.id.tv_name);
            mTvTitle = findView(R.id.tv_title);
            mTvPassword = findView(R.id.tv_password);

        }

        @Override
        public void setData(final PasswordData passwordData) {
            mTvName.setText("用户名："+ passwordData.getName());
            mTvTitle.setText(passwordData.getAddress());
            mTvPassword.setText("密码："+ passwordData.getPwd());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalSearchActivity globalSearchActivity = (GlobalSearchActivity) v.getContext();
                    Intent intent = new Intent(globalSearchActivity,PasswordDetailActivity.class );
                    Long id = passwordData.getId();
                    intent.putExtra("id",id);
                    globalSearchActivity.startActivity(intent);
                    KeyboardUtils.hideSoftInput(globalSearchActivity);
                }
            });
        }
    }

}
