package com.gersion.superlock.control;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
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
public class DetailFaceControl extends BaseDetailControl {


//    private final Keyer mData;

    private View mView;
    private TextView mAddress;
    private TextView mTime;
    private ImageView mIcon;

    public DetailFaceControl(Context context, Keyer keyer) {
        super(context, keyer);
    }

    //初始化控件
    public View initView() {
        mView = View.inflate(mContext, R.layout.item_face, null);
        mAddress = (TextView) mView.findViewById(R.id.tv_address);
        mTime = (TextView) mView.findViewById(R.id.tv_create_date);
        mIcon = (ImageView) mView.findViewById(R.id.iv_icon);
        mAddress.setText(mKeyer.address);
        mTime.setText("添加时间："+mKeyer.createTime);
        setIcon(mKeyer.address);
        return mView;
    }

    private void setIcon(String name){
        if (matching(name,"腾讯")||matching(name,"QQ")||matching(name,"qq")){
            mIcon.setImageResource(R.mipmap.qq);
        }else if (matching(name,"京东")||matching(name,"jingdong")||matching(name,"jindong")||matching(name,"JinDong")){
            mIcon.setImageResource(R.mipmap.jd);
        }else if (matching(name,"淘宝")||matching(name,"支付宝")||matching(name,"阿里巴巴")||matching(name,"taobao")){
            mIcon.setImageResource(R.mipmap.tb);
        }else if (matching(name,"网易")||matching(name,"wangyi")||matching(name,"易信")||matching(name,"yixing")){
            mIcon.setImageResource(R.mipmap.wy);
        }else if (matching(name,"百度")||matching(name,"爱奇艺")||matching(name,"iQiYi")||matching(name,"aiqiyi")){
            mIcon.setImageResource(R.mipmap.bd);
        }else if (matching(name,"美团")||matching(name,"meituan")){
            mIcon.setImageResource(R.mipmap.mt);
        }else if (matching(name,"微信")||matching(name,"WeiXin")||matching(name,"weixin")){
            mIcon.setImageResource(R.mipmap.wx);
        }else if (matching(name,"豆瓣")||matching(name,"doubang")||matching(name,"DouBang")){
            mIcon.setImageResource(R.mipmap.db);
        }
    }

    private boolean matching(String name,String criteria){
        return name.matches(".*"+criteria+".*");
    }

}
