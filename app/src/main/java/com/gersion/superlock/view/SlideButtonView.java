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
import android.widget.Scroller;

import com.gersion.superlock.R;

/**
 * Created by Gers on 2016/8/7.
 */

public class SlideButtonView extends View {

    private final Bitmap buttonBitmap;
    private final Bitmap backgroundBitmap;
    private Paint paint;
    private float downX;
    private float moveX;
    private Scroller mScroller;
    private boolean status;



    public SlideButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideButtonView);
        status = ta.getBoolean(R.styleable.SlideButtonView_status,false);
        ta.recycle();

        paint = new Paint();
        mScroller = new Scroller(context);
        //背景图片
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.toggle_off);
        //按钮的图片
        buttonBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.toggle_on);
    }

    //测量要绘制的bitmap的长宽
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(backgroundBitmap.getWidth(),backgroundBitmap.getHeight());
    }

    //将bitmap画到界面上
    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(backgroundBitmap,0,0,paint);
        canvas.drawBitmap(buttonBitmap,0,0,paint);
    }

    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                preformDown();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
//                preformMove();
                break;
            case MotionEvent.ACTION_UP:
//                preformUp();
                break;
        }
        return true;
    }

    //手指抬起时要执行的动作
    private void preformUp() {

    }

    private boolean isOpen = false;
    //手指按下时要执行的动作
    private void preformDown() {
        //滑动到右边的极限值
        int rightMax = backgroundBitmap.getWidth() - buttonBitmap.getWidth();
        if (status){
            mScroller.startScroll(0,0,-rightMax,0,300);
            status = false;
        }else{
            mScroller.startScroll(-rightMax,0,rightMax,0,300);
            status = true;
        }
        invalidate();
    }

    // 计算移动
    @Override
    public void computeScroll() {

        // 是否仍然没有移动到目标位置
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            // 移动位置
            scrollTo(currX, 0);

            // 重绘
            invalidate();
        }
    }
}
