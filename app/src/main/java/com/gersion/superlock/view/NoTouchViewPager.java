  
package com.gersion.superlock.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

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
public class NoTouchViewPager extends ViewPager {

    private boolean isTouchable = false;
    public NoTouchViewPager(Context context) {
        super(context);
        
    }
    
    public NoTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isTouchable) {
            return super.onTouchEvent(ev);
        }else{
            return false;
        }
        
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isTouchable) {
            return super.onInterceptTouchEvent(arg0);
        }else{
            return false;
        }
        
    }

}
  