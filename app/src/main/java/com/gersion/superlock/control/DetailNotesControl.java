package com.gersion.superlock.control;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.gersion.superlock.R;
import com.gersion.superlock.base.BaseDetailControl;
import com.gersion.superlock.bean.Keyer;

/**
 * @作者 Gersy
 * @版本
 * @包名 com.gersion.superlock.control
 * @待完成
 * @创建时间 2016/9/16
 * @功能描述 主界面中listview中每个item中的内容展示，有3D立体效果的viewPager，每个界面展示不同的内容
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class DetailNotesControl extends BaseDetailControl{

    private TextView mNotes;

    public DetailNotesControl(Context context, Keyer keyer) {
        super(context, keyer);
    }

    private View mView;

    //初始化控件
    public View initView() {
        mView = View.inflate(mContext, R.layout.item_note, null);
//        mNotes = (TextView) mView.findViewById(R.id.tv_notes);
//        if (!TextUtils.isEmpty(mKeyer.notes)){
//            mNotes.setText(mKeyer.notes);
//        }
        return mView;
    }

}
