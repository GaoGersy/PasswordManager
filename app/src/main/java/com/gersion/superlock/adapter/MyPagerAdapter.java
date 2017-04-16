package com.gersion.superlock.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gersion.superlock.R;

import java.util.List;

/**
 * @作者 Gers
 * @版本
 * @包名 com.gersion.superlock.adapter
 * @待完成
 * @创建时间 2016/8/18
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class MyPagerAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;

    private MyPagerAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.activity_guide, null);
        }
        return convertView;
    }

    class ViewHolder {

    }
}
