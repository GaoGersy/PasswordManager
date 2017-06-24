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

public class ContentListAdapter extends SwipeMenuAdapter<ContentListAdapter.DefaultViewHolder>implements IRVAdapter<DbBean> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private SwipeMenuRecyclerView mMenuRecyclerView;

    private List<DbBean> mDatas;

    private OnItemClickListener mOnItemClickListener;

    public ContentListAdapter(SwipeMenuRecyclerView menuRecyclerView, List<DbBean> data) {
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
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
    public ContentListAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        viewHolder.mMenuRecyclerView = mMenuRecyclerView;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContentListAdapter.DefaultViewHolder holder, int position) {
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
        return mDatas;
    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {

        private TextView mTvPassword;
        OnItemClickListener mOnItemClickListener;
        SwipeMenuRecyclerView mMenuRecyclerView;
        private FoldingCell mFcContainer;
        private int mPosition;
        private TextView mTvName;
        private TextView mTvTitle;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTvName = findView(R.id.tv_name);
            mTvTitle = findView(R.id.tv_title);
            mTvPassword = findView(R.id.tv_password);
            itemView.findViewById(R.id.iv_touch).setOnTouchListener(this);
        }

        protected <T extends View> T findView(int id){
            return (T) itemView.findViewById(id);
        }

        public void setData(int position) {
            mPosition = position;
            DbBean passwordBean = mDatas.get(position);
            mTvName.setText("用户名："+passwordBean.getName());
            mTvTitle.setText(passwordBean.getAddress());
            mTvPassword.setText("密码："+passwordBean.getPwd());
        }

        @Override
        public void onClick(View v) {
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
