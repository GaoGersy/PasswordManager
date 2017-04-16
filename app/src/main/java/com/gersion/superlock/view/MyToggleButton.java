package com.gersion.superlock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gersion.superlock.R;

/**
 * @作者 Gers
 * @版本
 * @包名 com.gersion.superlock.view
 * @待完成
 * @创建时间 2016/8/14
 * @功能描述 TODO
 * @更新人 $
 * @更新时间 $
 * @更新版本 $
 */
public class MyToggleButton extends View implements View.OnClickListener {

    private boolean mState;
    private Paint mPaint;
    private OnStateSwitchListener mListener;
    private Bitmap mOnBitmap;
    private Bitmap mOffBitmap;

    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyToggleButton);
        mState = ta.getBoolean(R.styleable.MyToggleButton_switchState,false);
        ta.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mOnBitmap =  BitmapFactory.decodeResource(getResources(), R.mipmap.toggle_on);
        mOffBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.toggle_off);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mOnBitmap.getWidth(),mOffBitmap.getHeight());
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        mScaleX = (getMeasuredWidth()*1.0f)/mOnBitmap.getWidth();
//        mScaleY = (getMeasuredHeight()*1.0f)/mOnBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mState){
            mListener.onSwitchState(mState);
            canvas.drawBitmap(mOnBitmap,0,0,mPaint);
        }else{
            mListener.onSwitchState(mState);
            canvas.drawBitmap(mOffBitmap,0,0,mPaint);
        }
    }


    @Override
    public void onClick(View v) {
        mState = !mState;
        invalidate();
    }

    public void setOnStateSwitchListener(OnStateSwitchListener listener){

        mListener = listener;
    }

    public interface OnStateSwitchListener{
        void onSwitchState(boolean state);
    }
}
