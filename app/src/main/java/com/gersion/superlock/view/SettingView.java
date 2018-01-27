package com.gersion.superlock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.kyleduo.switchbutton.SwitchButton;

/**
 * Created by gersy on 2017/6/22.
 */

public class SettingView extends RelativeLayout {

    private View mView;
    private ImageView mGo;
    private SwitchButton mSwitchButton;

    public SettingView(Context context) {
        this(context, null);
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mView = LayoutInflater.from(context).inflate(R.layout.view_setting_item, this, true);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingView);
        ImageView icon = (ImageView) mView.findViewById(R.id.iv_icon);
        TextView tv_title = (TextView) mView.findViewById(R.id.tv_title);
        TextView tv_divide = (TextView) mView.findViewById(R.id.devider);
        mSwitchButton = (SwitchButton) mView.findViewById(R.id.switchButton);
        mGo = (ImageView) mView.findViewById(R.id.iv_go);

        int iconId = a.getResourceId(R.styleable.SettingView_setting_icon, R.drawable.settings);
        boolean isGoShow = a.getBoolean(R.styleable.SettingView_setting_go_show, true);
        boolean isSwitchShow = a.getBoolean(R.styleable.SettingView_setting_switch_show, false);
        boolean isDividerShow = a.getBoolean(R.styleable.SettingView_setting_divider_show, true);
        String title = a.getString(R.styleable.SettingView_setting_title);
        tv_title.setText(title);
        setGoVisible(isGoShow);
        if (isSwitchShow) {
            mSwitchButton.setVisibility( VISIBLE);
            mGo.setVisibility(GONE);
        }
        tv_divide.setVisibility(isDividerShow?VISIBLE:GONE);
        icon.setImageResource(iconId);
        a.recycle();

    }

    public void setGoVisible(boolean visible) {
        mGo.setVisibility(visible?VISIBLE:GONE);
    }

    public void setSwitchVisible(boolean visible) {
        mSwitchButton.setVisibility(visible?VISIBLE:GONE);
    }

    public void setSwitchStatus(boolean isChecked){
        mSwitchButton.setChecked(isChecked);
    }

    public void setEnableEffect(boolean enable){
        mSwitchButton.setChecked(enable);
        mSwitchButton.setEnabled(enable);
    }

    public void setSwitchChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        if (mSwitchButton != null) {
            mSwitchButton.setOnCheckedChangeListener(listener);
        }
    }

    public void setItemClickListener(View.OnClickListener listener) {
        if (mView != null) {
            mView.setOnClickListener(listener);
        }
    }

}
