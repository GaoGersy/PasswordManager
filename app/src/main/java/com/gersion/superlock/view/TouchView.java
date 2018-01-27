package com.gersion.superlock.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by aa326 on 2018/1/27.
 */

public class TouchView extends AppCompatTextView {
    private OnPressListener mListener;
    private boolean mEnable;

    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (!mEnable){
//            return super.onTouchEvent(event);
//        }
        if (mListener == null) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Logger.d("手指按下");
                mListener.onPress();
                break;
            case MotionEvent.ACTION_UP:
//                Logger.d("手指抬起");
                mListener.onRelease();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setCanTouch(boolean enable) {
        mEnable = enable;
    }

    public boolean isCanTouch() {
        return mEnable;
    }

    public void setOnPressListener(OnPressListener listener) {
        mListener = listener;
    }

    public interface OnPressListener {
        void onPress();

        void onRelease();
    }
}
