package com.gersion.superlock.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gersion.superlock.R;
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
public class MainAdapter_bak extends BaseAdapter {
    private Context mContext;
    private List<Keyer> mList;

    public MainAdapter_bak(Context context, List<Keyer> list) {
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
            convertView = View.inflate(mContext, R.layout.item_main_show, null);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.password = (TextView) convertView.findViewById(R.id.password);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        initPosition(holder,500);

        final Keyer keyer = mList.get(position);
        holder.password.setText(keyer.pwd);
        holder.name.setText(keyer.name);
        holder.address.setText(keyer.address);
//        holder.time.setText(keyer.createTime);
        final ViewHolder finalHolder = holder;
        final View finalConvertView = convertView;
        final ViewHolder finalHolder1 = holder;
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keyer.isSelected){
                    playOutAnimator(finalHolder1, keyer, finalHolder, finalConvertView);
                }else{
                    playAnimator(finalHolder1, keyer, finalHolder, finalConvertView);
                }
                keyer.isSelected = !keyer.isSelected;


            }
        });
        if (keyer.isSelected){
            initPosition(holder,0);
            holder.bg.setBackgroundResource(R.drawable.shape_item_change_bg);
        }else{
            initPosition(holder,500);
            holder.bg.setBackgroundResource(R.drawable.shape_item_bg);

        }


        View holderView = convertView;
        holderView.setScaleX(0.6f);
        holderView.setScaleY(0.5f);

        ViewCompat.animate(holderView).scaleX(1).scaleY(1).setDuration(400).
                setInterpolator(new OvershootInterpolator(4)).start();
        return holderView;
    }

    private void initPosition(ViewHolder holder,int x) {
        holder.address.setX(x);
        holder.password.setX(x);
        holder.name.setX(x);
        holder.time.setX(x);
    }


    private void playAnimator(final ViewHolder holder1, final Keyer keyer, final ViewHolder holder, final View View) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(holder.bg,"rotationX",0,180);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (keyer.isSelected){
                    holder.bg.setBackgroundResource(R.drawable.shape_item_change_bg);
                }else{
                    holder.bg.setBackgroundResource(R.drawable.shape_item_bg);

                }
                doAnimator(View, holder1.address,0);
                doAnimator(View, holder1.name,100);
                doAnimator(View, holder1.password,200);
                doAnimator(View, holder1.time,300);
            }
        });
    }

    private void playOutAnimator(final ViewHolder holder1, final Keyer keyer, final ViewHolder holder, final View View) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(holder.bg,"rotationX",0,180);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (keyer.isSelected){
                    holder.bg.setBackgroundResource(R.drawable.shape_item_change_bg);
                }else{
                    holder.bg.setBackgroundResource(R.drawable.shape_item_bg);

                }
                outAnimator(View, holder1.address,0);
                outAnimator(View, holder1.name,100);
                outAnimator(View, holder1.password,200);
                outAnimator(View, holder1.time,300);
            }
        });
    }


    class ViewHolder {
        TextView address;
        TextView name;
        TextView password;
        TextView time;
        TextView bg;
        TextView btn;
    }
    private void doAnimator(View mView, View view,int dely) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",  mView.getWidth(),0);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setStartDelay(dely);
        animator.setDuration(500);
        animator.start();
    }

    private void outAnimator(View mView, View view,int dely) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",0,  mView.getWidth());
        animator.setInterpolator(new OvershootInterpolator());
        animator.setStartDelay(dely);
        animator.setDuration(500);
        animator.start();
    }

}
