package com.gersion.superlock.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gersion.superlock.bean.Keyer;

import java.util.List;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.adapter
 * @待完成
 * @创建时间 2016/8/23
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class MainAdapter extends BaseAdapter {
    private Context mContext;
    private List<Keyer> mList;
    private boolean isOpen = false;
    private float mDistance = 20;

    public MainAdapter(Context context, List<Keyer> list) {
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
//        ViewHolder holder = new ViewHolder();
//        if (convertView == null) {
//            convertView = View.inflate(mContext, R.layout.item_card_view, null);
//            holder.mCvInfo = (CardView) convertView.findViewById(R.id.cv_info);
//            holder.mCvNotes = (CardView) convertView.findViewById(R.id.cv_notes);
//            holder.mTvNoteKey = (TextView) convertView.findViewById(R.id.tv_noteKey);
//            convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder) convertView.getTag();
//        }
//        final ViewHolder finalHolder = holder;
//        holder.mTvNoteKey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openNotes(finalHolder.mTvNoteKey, finalHolder.mCvNotes, finalHolder.mCvInfo);
//
//                if (isOpen){
//
//                }else{
//
//                }
//            }
//        });
//        holder.mCvNotes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closeNotes(finalHolder.mTvNoteKey, finalHolder.mCvNotes, finalHolder.mCvInfo);
//
//            }
//        });
        return convertView;
    }

//    class ViewHolder{
//       public CardView mCvNotes;
//       public CardView mCvInfo;
//       public TextView mTvNoteKey;
//    }
//    private void openNotes(TextView mTvNoteKey, CardView mCvNotes, CardView mCvInfo) {
//        isOpen = true;
//        mTvNoteKey.setText("密码信息");
//        ObjectAnimator animatorNotes = ObjectAnimator.ofFloat(mCvInfo, "rotationY", 0,180);
////        ObjectAnimator animatorInfo = ObjectAnimator.ofFloat(mCvInfo, "translationY",  -mCvInfo.getHeight()+mDistance);
////        AnimatorSet set = new AnimatorSet();
////        set.playTogether(animatorInfo,animatorNotes);
//        animatorNotes.setDuration(500);
//        animatorNotes.setInterpolator(new OvershootInterpolator());
//        animatorNotes.start();
//    }
//
//    private void closeNotes(TextView mTvNoteKey, CardView mCvNotes, CardView mCvInfo) {
//        isOpen = false;
//        mTvNoteKey.setText("备注");
//        ObjectAnimator animatorNotes = ObjectAnimator.ofFloat(mCvInfo,"rotationY",180, 0);
////        ObjectAnimator animatorInfo = ObjectAnimator.ofFloat(mCvInfo, "translationY",  0);
//        AnimatorSet set = new AnimatorSet();
//        animatorNotes.setDuration(500);
//        animatorNotes.setInterpolator(new AnticipateInterpolator());
//        animatorNotes.start();
//    }
}

