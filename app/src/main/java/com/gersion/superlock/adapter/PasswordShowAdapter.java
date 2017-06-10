/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gersion.superlock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.bean.DbBean;
import com.gersion.superlock.listener.OnItemClickListener;
import com.gersion.superlock.view.smartRecycleView.IRVAdapter;
import com.ramotion.foldingcell.FoldingCell;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.HashSet;
import java.util.List;

public class PasswordShowAdapter extends SwipeMenuAdapter<PasswordShowAdapter.DefaultViewHolder>implements IRVAdapter<DbBean> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private SwipeMenuRecyclerView mMenuRecyclerView;

    private List<DbBean> mDatas;

    private OnItemClickListener mOnItemClickListener;

    public PasswordShowAdapter(SwipeMenuRecyclerView menuRecyclerView, List<DbBean> data) {
        this.mMenuRecyclerView = menuRecyclerView;
        this.mDatas = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_password_show, parent, false);
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    @Override
    public PasswordShowAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        viewHolder.mMenuRecyclerView = mMenuRecyclerView;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PasswordShowAdapter.DefaultViewHolder holder, int position) {
        holder.setData(position);

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void setNewData(List<DbBean> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public void addData(List<DbBean> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<DbBean> data) {
        mDatas.removeAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void remove(DbBean data) {
        mDatas.remove(data);
        notifyDataSetChanged();
    }

    @Override
    public List<DbBean> getData() {
        return null;
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {

        OnItemClickListener mOnItemClickListener;
        SwipeMenuRecyclerView mMenuRecyclerView;
        private FoldingCell mFcContainer;
        private int mPosition;
        private TextView mTvName;
        private TextView mTvTitle;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mFcContainer = (FoldingCell) itemView.findViewById(R.id.fc_container);
            mTvName = findView(R.id.tv_name);
            mTvTitle = findView(R.id.tv_title);
            itemView.findViewById(R.id.iv_touch).setOnTouchListener(this);
        }

        protected <T extends View> T findView(int id){
            return (T) itemView.findViewById(id);
        }

        public void setData(int position) {
            mPosition = position;
            DbBean passwordBean = mDatas.get(position);
            mTvName.setText(passwordBean.getUpdateHistorys().get(0).getUpdateTime()+"");
            mTvTitle.setText(passwordBean.getName());
            if (unfoldedIndexes.contains(position)) {
                mFcContainer.unfold(true);
            } else {
                mFcContainer.fold(true);
            }
        }

        @Override
        public void onClick(View v) {
            mFcContainer.toggle(false);
            registerToggle(mPosition);
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mMenuRecyclerView.startDrag(this);
                    break;
                }
            }
            return false;
        }
    }

}
