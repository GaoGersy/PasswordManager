package com.gersion.superlock.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.gersion.superlock.ItemTransformer;
import com.gersion.superlock.R;
import com.gersion.superlock.bean.ItemBean;
import com.gersion.superlock.bean.PasswordData;
import com.gersion.superlock.fragment.HomeFragment;
import com.gersion.superlock.listener.OnItemClickListener;
import com.gersion.superlock.view.smartRecycleView.IRVAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.Orientation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.DefaultViewHolder> implements IRVAdapter<PasswordData> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private SwipeMenuRecyclerView mMenuRecyclerView;

    private List<PasswordData> mDatas;

    private OnItemClickListener mOnItemClickListener;
    private HomeFragment mHomeFragment;

    public ContentListAdapter(SwipeMenuRecyclerView menuRecyclerView, List<PasswordData> data) {
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
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        DefaultViewHolder viewHolder = new DefaultViewHolder(view);
//        viewHolder.mOnItemClickListener = mOnItemClickListener;
//        viewHolder.mMenuRecyclerView = mMenuRecyclerView;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(position);

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void setNewData(List<PasswordData> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public void addData(List<PasswordData> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<PasswordData> data) {
        mDatas.removeAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void remove(PasswordData data) {
        mDatas.remove(data);
//        DbManager.getInstance().delete(data);
        notifyDataSetChanged();
    }

    @Override
    public List<PasswordData> getData() {
        return mDatas;
    }

//    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
//
//        private TextView mTvPassword;
//        OnItemClickListener mOnItemClickListener;
//        SwipeMenuRecyclerView mMenuRecyclerView;
//        private FoldingCell mFcContainer;
//        private int mPosition;
//        private TextView mTvName;
//        private TextView mTvTitle;
//
//        public DefaultViewHolder(View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//            mTvName = findView(R.id.tv_name);
//            mTvTitle = findView(R.id.tv_title);
//            mTvPassword = findView(R.id.tv_password);
//            itemView.findViewById(R.id.iv_touch).setOnTouchListener(this);
//        }
//
//        protected <T extends View> T findView(int id){
//            return (T) itemView.findViewById(id);
//        }
//
//        public void setData(int position) {
//            mPosition = position;
//            PasswordData passwordBean = mDatas.get(position);
//            mTvName.setText("用户名："+passwordBean.getName());
//            mTvTitle.setText(passwordBean.getAddress());
//            mTvPassword.setText("密码："+passwordBean.getPwd());
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (mOnItemClickListener != null) {
//                mOnItemClickListener.onItemClick(v,getAdapterPosition());
//            }
//        }
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            int action = event.getAction();
//            switch (action) {
//                case MotionEvent.ACTION_DOWN: {
//                    mMenuRecyclerView.startDrag(this);
//                    break;
//                }
//            }
//            return false;
//        }
//    }

    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        private DiscreteScrollView itemPicker;
        private DetailItemAdapter mAdapter;
        private InfiniteScrollAdapter infiniteAdapter;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            initDiscreteScrollView();
        }

        private void initDiscreteScrollView() {
            itemPicker = (DiscreteScrollView) findView(R.id.item_picker);
            itemPicker.setOrientation(Orientation.HORIZONTAL);
//            itemPicker.addOnItemChangedListener();
            mAdapter = new DetailItemAdapter();
            mAdapter.registerMultiLayout(R.layout.item_detail_home);
            mAdapter.registerMultiLayout(R.layout.item_detail_more);
            mAdapter.registerMultiLayout(R.layout.item_detail_notice);
            mAdapter.registerMultiLayout(R.layout.item_detail_password);
//            infiniteAdapter = InfiniteScrollAdapter.wrap(mAdapter);
            itemPicker.setAdapter(mAdapter);
            itemPicker.setItemTransitionTimeMillis(100);
            itemPicker.setOffscreenItems(0);
            itemPicker.setItemTransformer(new ItemTransformer.Builder()
                    .setMinScale(0.7f)
                    .setDegree(90f)
                    .build());
            itemPicker.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
                @Override
                public void onScrollStart(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {

                }

                @Override
                public void onScrollEnd(@NonNull RecyclerView.ViewHolder currentItemHolder, int adapterPosition) {

                }

                @Override
                public void onScroll(float scrollPosition, int currentPosition, int newPosition, @Nullable RecyclerView.ViewHolder currentHolder, @Nullable RecyclerView.ViewHolder newCurrent) {

                }
            });
        }

        protected <T extends View> T findView(int id) {
            return (T) itemView.findViewById(id);
        }

        public void setData(int position) {
            PasswordData passwordData = mDatas.get(position);
            List<ItemBean> list = new ArrayList<>();

            ItemBean homeItemBean = new ItemBean();
            homeItemBean.setLayoutId(R.layout.item_detail_home);
            homeItemBean.setIcon(passwordData.getIcon());
            homeItemBean.setAddress(passwordData.getAddress());
            homeItemBean.setName(passwordData.getName());
            homeItemBean.setId(passwordData.getId());

            ItemBean detailItemBean = new ItemBean();
            detailItemBean.setLayoutId(R.layout.item_detail_password);
            detailItemBean.setAddress(passwordData.getAddress());
            detailItemBean.setPwd(passwordData.getPwd());
            detailItemBean.setName(passwordData.getName());
            detailItemBean.setNotes(passwordData.getNotes());
            detailItemBean.setId(passwordData.getId());
            detailItemBean.setExtraOptions(passwordData.getExtraOptions());

            ItemBean noticeItemBean = new ItemBean();
            noticeItemBean.setLayoutId(R.layout.item_detail_notice);
            noticeItemBean.setNotes(passwordData.getNotes());

            ItemBean moreItemBean = new ItemBean();
            moreItemBean.setId(passwordData.getId());
            moreItemBean.setExtraOptions(passwordData.getExtraOptions());
            moreItemBean.setLayoutId(R.layout.item_detail_more);

            list.add(homeItemBean);
            list.add(detailItemBean);
            list.add(noticeItemBean);
            list.add(moreItemBean);
            mAdapter.setItems(list);
            mAdapter.setOnItemClickListener(new DetailItemAdapter.OnItemClickListener() {
                @Override
                public void onClik(View view) {
                    if (view.getId()==R.id.fl_container){
                        itemPicker.smoothScrollToPosition(2);
                    }
                }
            });
//            itemPicker.scrollToPosition(1);
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

    public void setFragment(HomeFragment homeFragment){
        mHomeFragment = homeFragment;
    }

}
