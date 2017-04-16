package com.gersion.superlock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gersion.superlock.R;

/**
 * Created by Gers on 2016/8/7.
 */

public class ToggleButtonView extends View {
    private Bitmap openBackgroundBitmap;
    private Bitmap closeBackgroundBitmap;
    private Bitmap buttonBitmap;
    private Paint paint;
    private boolean status;
    private OnStatusChangeListener listener;


    public ToggleButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ToggleButtonView);
        status = ta.getBoolean(R.styleable.ToggleButtonView_state, false);
        ta.recycle();
        paint = new Paint();
        buttonBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_button);
        openBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        closeBackgroundBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量按钮的长和宽
        setMeasuredDimension(closeBackgroundBitmap.getWidth(),buttonBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int rightPosition = closeBackgroundBitmap.getWidth() - buttonBitmap.getWidth(); 
        if (!status){
            status = true;
            listener.onSwitch(status);
            canvas.drawBitmap(openBackgroundBitmap,0,0,paint);
            canvas.drawBitmap(buttonBitmap,rightPosition,0,paint);
        }else{
            status = false;
            listener.onSwitch(status);
            canvas.drawBitmap(closeBackgroundBitmap,0,0,paint);
            canvas.drawBitmap(buttonBitmap,0,0,paint);
        }
        
    }

    //当被点击时，重绘界面
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            invalidate();
        }
        return true;
    }

    //当被点击时接口回调，将按钮当前的状态传给调用者
    public void setOnStatusChangeListener(OnStatusChangeListener listener){

        this.listener = listener;
    }
    
    public interface OnStatusChangeListener{

        void onSwitch(boolean status);
    }

}
