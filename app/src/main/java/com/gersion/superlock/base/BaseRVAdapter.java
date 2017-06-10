package com.gersion.superlock.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gersion.superlock.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 Gersy
 */
public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter implements OnItemClickListener {
    protected List<T> mData = new ArrayList<>();
    protected BaseViewHolder mViewHolder;
    private int mPosition;
    protected OnItemClickListener mListener;
    public Context mContext;

    public BaseRVAdapter(List<T> data) {
        if (data != null && data.size()>0){
            mData.addAll(data);
        }
    }

    public void addAllData(List<T> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addAll(List<T> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    public void setData(List<T> data){
        mData = data;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view =  LayoutInflater.from(mContext).inflate(setResourseId(), parent,false);
        mViewHolder = setViewHolder(view);
        mViewHolder.setOnItemClickListener(this);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mPosition = position;
        BaseViewHolder<T> holder1 = (BaseViewHolder<T>) holder;
        holder1.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected abstract BaseViewHolder setViewHolder(View view);

    protected abstract int setResourseId();

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mListener!=null) {
            mListener.onItemClick(view, position);
        }
    }
}