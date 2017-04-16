  
package com.gersion.superlock.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import java.util.List;

/** 
 * ClassName: NewsCenterBean <br/> 
 * Function: TODO ADD FUNCTION. <br/>  
 * date: 2016年8月9日 下午7:36:01 <br/> 
 * 
 * @作者 Gers 
 * @版本  
 * @包名 com.example.smartbeijing.bean
 * @待完成 TODO
 * @创建时间 2016年8月9日
 * @描述 TODO
 * 
 * @更新人 $Author$
 * @更新时间 $Date$
 * @更新版本 $Rev$
 */
public class GuidePagerAdapter extends PagerAdapter {
     
    
    private Context mContext;
    private List<View> mList;

    public GuidePagerAdapter(Context context, List<View> list) {
        super();
        mContext = context;
        mList = list;
        
    }

    @Override
    public int getCount() {
        return mList.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View holderView = mList.get(position);
        holderView.setScaleX(0.6f);
        holderView.setScaleY(0.5f);

        ViewCompat.animate(holderView).scaleX(1).scaleY(1).setDuration(400).
                setInterpolator(new OvershootInterpolator(4)).start();
        container.addView(holderView);
        return holderView;
    }


}
  