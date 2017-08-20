package com.gersion.superlock.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gersion.superlock.R;

/**
 * Created by aa326 on 2017/8/18.
 */

public class TouchView extends View {

    private Paint mPaint;
    private OnPressListener mListener;
    private boolean mEnable = true;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;

    public TouchView(Context context) {
        this(context, null);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.finger_print);
        mHeight = mBitmap.getHeight();
        mWidth = mBitmap.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (widthMode == MeasureSpec.UNSPECIFIED
//                || widthMode == MeasureSpec.AT_MOST) {
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY);
//        }
//        if (heightMode == MeasureSpec.UNSPECIFIED
//                || heightMode == MeasureSpec.AT_MOST) {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);
//        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
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

    public void setEnable(boolean enable) {
        mEnable = enable;
    }

    public boolean isEnable() {
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
