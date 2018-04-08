package com.gersion.superlock.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.bean.ExtraOptionBean;
import com.gersion.superlock.utils.GsonHelper;

import java.util.List;

/**
 * Created by gersy on 2018/4/8.
 */

public class DynamicDataView extends LinearLayout {

    private LayoutInflater mInflater;
    private Context mContext;

    public DynamicDataView(Context context) {
        this(context,null);
    }

    public DynamicDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setData(String data){
        if (TextUtils.isEmpty(data)){
            return;
        }else {
            List<ExtraOptionBean> extraOptionBeans = GsonHelper.toList(data, ExtraOptionBean.class);
            for (int i = 0; i < extraOptionBeans.size(); i++) {
                ExtraOptionBean extraOptionBean = extraOptionBeans.get(i);
                TextView view = (TextView) mInflater.inflate(R.layout.view_more_item, null);
                view.setText(extraOptionBean.getKey() + " ： " + extraOptionBean.getValue());
                this.addView(view);
            }
        }
    }
}
