//package com.gersion.superlock.adapter;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.TextView;
//
//import com.gersion.superlock.R;
//import com.gersion.superlock.base.BaseRVAdapter;
//import com.gersion.superlock.base.BaseViewHolder;
//import com.gersion.superlock.bean.UpdateData;
//import com.gersion.superlock.utils.TimeUtils;
//import com.gersion.superlock.view.smartRecycleView.IRVAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by a3266 on 2017/6/10.
// */
//
//public class HistoryDetialAdapter extends BaseRVAdapter<UpdateData> implements IRVAdapter<UpdateData> {
//    public HistoryDetialAdapter(List<UpdateData> data) {
//        super(data);
//    }
//
//    @Override
//    protected BaseViewHolder setViewHolder(View view) {
//        return new HistoryDetialViewHolder(view);
//    }
//
//    @Override
//    protected int setResourseId() {
//        return R.layout.item_history;
//    }
//
//    @Override
//    public RecyclerView.Adapter getAdapter() {
//        return this;
//    }
//
//    @Override
//    public void setNewData(List<UpdateData> data) {
//        mData = data==null?new ArrayList<UpdateData>():data;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void addData(List<UpdateData> data) {
//        mData.addAll(data);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void removeAll(List<UpdateData> data) {
//
//    }
//
//    @Override
//    public void remove(UpdateData data) {
//
//    }
//
//    @Override
//    public List<UpdateData> getData() {
//        return mData;
//    }
//
//    static class HistoryDetialViewHolder extends BaseViewHolder<UpdateData>{
//
//        private TextView mTvTime;
//        private TextView mTvPassword;
//
//        public HistoryDetialViewHolder(View itemView) {
//            super(itemView);
//        }
//
//        @Override
//        protected void initView(View itemView) {
//            mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
//            mTvPassword = (TextView) itemView.findViewById(R.id.tv_password);
//        }
//
//        @Override
//        public void setData(UpdateData updateBean) {
//            mTvTime.setText("时间："+TimeUtils.getTime(updateBean.getUpdateTime()));
//            mTvPassword.setText("密码："+updateBean.getPassword());
//        }
//    }
//}
