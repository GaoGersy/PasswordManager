package com.gersion.superlock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gersion.superlock.R;

/**
 * Created by ruifeng on 2016/12/17.
 */
public class ItemView extends RelativeLayout {

    private View mView;
    private ImageView mGo;
    private ImageView mNew;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.view_item, this, true);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        ImageView icon = (ImageView) mView.findViewById(R.id.iv_icon);
        TextView tv_title = (TextView) mView.findViewById(R.id.tv_title);
        TextView tv_divide = (TextView) mView.findViewById(R.id.devider);
        mGo = (ImageView) mView.findViewById(R.id.iv_go);
        mNew = (ImageView) mView.findViewById(R.id.iv_new);

        int iconId = a.getResourceId(R.styleable.ItemView_item_icon, R.drawable.settings);
        boolean isGoShow = a.getBoolean(R.styleable.ItemView_item_go_show, true);
        boolean isNewShow = a.getBoolean(R.styleable.ItemView_item_new_show, false);
        String title = a.getString(R.styleable.ItemView_item_title);
        tv_title.setText(title);
        if (isGoShow) {
            mGo.setVisibility(VISIBLE);
        } else {
            mGo.setVisibility(GONE);
        }
        if (isNewShow) {
            mNew.setVisibility(VISIBLE);
        } else {
            mNew.setVisibility(GONE);
        }

        if ("退出".equals(title)) {
            tv_divide.setVisibility(GONE);
        }
        a.recycle();
        icon.setImageResource(iconId);

    }

    public void setGoHidden() {
        mGo.setVisibility(GONE);
    }

    public void setNewShow(boolean isShow) {
        if (isShow) {
            mNew.setVisibility(VISIBLE);
        } else {
            mNew.setVisibility(GONE);
        }
    }

    public void setItemClickListener(OnClickListener listener) {
        if (mView != null) {
            mView.setOnClickListener(listener);
        }
    }

}
