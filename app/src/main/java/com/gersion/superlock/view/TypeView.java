package com.gersion.superlock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;

/**
 * Created by aa326 on 2017/8/16.
 */

public class TypeView extends LinearLayout {

    private ImageView mIvSelect;
    private TextView mTvName;
    private View mView;

    public TypeView(Context context) {
        this(context,null);
    }

    public TypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.view_type, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TypeView);
        mIvSelect = (ImageView) mView.findViewById(R.id.iv_select);
        mTvName = (TextView) mView.findViewById(R.id.tv_name);
        String lable = ta.getString(R.styleable.TypeView_typeLable);
        mTvName.setText(lable==null?"标签":lable);
        ta.recycle();
    }

    public void setSelected(boolean selected){
        mIvSelect.setSelected(selected);
    }

    public boolean getSelected(){
        return mIvSelect.isSelected();
    }

    public String getName() {
        return mTvName.toString().trim();
    }

    public void setTvName(String name) {
        mTvName.setText(name==null?"":name);
    }

    public void setOnSelectChangeListener(final OnSelectChangeListener listener){
        mView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvSelect.setSelected(!mIvSelect.isSelected());
                if (listener!=null){
                    listener.onCheckChange(mIvSelect.isSelected());
                }
            }
        });
    }

    public interface OnSelectChangeListener{
        void onCheckChange(boolean selected);
    }
}
