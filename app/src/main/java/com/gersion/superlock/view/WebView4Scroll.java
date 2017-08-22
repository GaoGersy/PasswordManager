package com.gersion.superlock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class WebView4Scroll extends WebView {
    private OnWebViewDrawListener mListener;

    public WebView4Scroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.getScrollY() <= 0)
                    this.scrollTo(0, 1);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        LoggerUtils.d("onScrollChanged");
//        if (this.getScrollY() == 0) {
//            if (mListener!=null){
//                mListener.onRefreshEnable(true);
//            }
//        } else {
//            if (mListener!=null){
//                mListener.onRefreshEnable(false);
//            }
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mListener!=null) {
            if (getContentHeight()>0) {
                mListener.onPageLoaded();
            }
        }
    }

    public void setOnWebViewDrawListener(OnWebViewDrawListener listener) {
        mListener = listener;
    }

    /**
     * ~~ 创建时间：2017/5/15 15:17 ~~
     * 判断是否是开始绘制界面
     */
    public interface OnWebViewDrawListener {
        void onPageLoaded();
    }
}