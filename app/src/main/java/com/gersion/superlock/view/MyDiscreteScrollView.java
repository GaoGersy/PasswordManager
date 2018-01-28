package com.gersion.superlock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.yarolegovich.discretescrollview.DiscreteScrollView;

/**
 * Created by aa326 on 2018/1/28.
 */

public class MyDiscreteScrollView extends DiscreteScrollView {
    public MyDiscreteScrollView(Context context) {
        super(context);
    }

    public MyDiscreteScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDiscreteScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LayoutManager layoutManager = getLayoutManager();
        int childCount = layoutManager.getChildCount();
        int currentItem = getCurrentItem();
        if (currentItem==childCount-1){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

}
