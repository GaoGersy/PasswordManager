package com.gersion.floattools.view;


import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.gersion.floattools.FloatWindowManager;
import com.gersion.floattools.R;
import com.gersion.floattools.utils.ScreenUtils;
import com.gersion.floattools.utils.SpfUtils;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by wangxiandeng on 2016/11/25.
 */

public class FloatBallView extends FrameLayout {
    private final static long LONG_CLICK_LIMIT = 300;
    private final static long REMOVE_LIMIT = 1500;
    private final static long CLICK_LIMIT = 200;
    private final static int MODE_NONE = 0x000;
    private final static int MODE_DOWN = 0x001;
    private final static int MODE_UP = 0x002;
    private final static int MODE_LEFT = 0x003;
    private final static int MODE_RIGHT = 0x004;
    private final static int MODE_MOVE = 0x005;
    private final static int MODE_GONE = 0x006;
    private final static int OFFSET = 30;
    boolean isAddCoverView = true;
    boolean isBaseLine = false;
    int clickCount = 2;
    boolean doubleClick = false;
    private ImageView mImgBall;
    private ImageView mImgBigBall;
    private ImageView mImgBg;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private long mLastDownTime;
    private float mLastDownX;
    private float mLastDownY;
    private boolean mIsLongTouch;
    private boolean mIsTouching;
    private float mTouchSlop;
    private int mStatusBarHeight;
    private Service mService;
    private int mCurrentMode;
    private int mAlpha;
    private int mRed;
    private int mGreen;
    private int mBlue;
    private float mBigBallX;
    private float mBigBallY;
    private int mOffsetToParent;
    private int mOffsetToParentY;
    private Vibrator mVibrator;
    private long[] mPattern = {0, 100};
    private View mView;

    public FloatBallView(Context context) {

        super(context);
        mService = (Service) context;
        mVibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mRed = SpfUtils.getInt(getContext(), "red");
        mGreen = SpfUtils.getInt(getContext(), "green");
        mBlue = SpfUtils.getInt(getContext(), "blue");
        mAlpha = SpfUtils.getInt(getContext(), "alpha");
        initView();
    }

    private void initView() {
        mView = inflate(getContext(), R.layout.layout_ball, this);
        mImgBall = (ImageView) findViewById(R.id.img_ball);
        mImgBigBall = (ImageView) findViewById(R.id.img_big_ball);
        mImgBg = (ImageView) findViewById(R.id.img_bg);

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mCurrentMode = MODE_NONE;

        mStatusBarHeight = getStatusBarHeight();
        mOffsetToParent = dip2px(25);
        mOffsetToParentY = mStatusBarHeight + mOffsetToParent;

        mImgBigBall.post(new Runnable() {
            @Override
            public void run() {
                mBigBallX = mImgBigBall.getX();
                mBigBallY = mImgBigBall.getY();
            }
        });

        mImgBg.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mImgBall.setAlpha(1f);
                        mImgBigBall.setAlpha(0.5f);
                        mIsTouching = true;
                        mImgBall.setVisibility(INVISIBLE);
                        mImgBigBall.setVisibility(VISIBLE);
                        mLastDownTime = System.currentTimeMillis();
                        mLastDownX = event.getX();
                        mLastDownY = event.getY();
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!mIsLongTouch && mIsTouching && mCurrentMode == MODE_NONE) {
                                    mIsLongTouch = isLongClick(event);
                                }
                            }
                        }, LONG_CLICK_LIMIT);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!mIsLongTouch && isTouchSlop(event)) {
                            return true;
                        }
                        if (mIsLongTouch && (mCurrentMode == MODE_NONE || mCurrentMode == MODE_MOVE)) {
                            mLayoutParams.x = (int) (event.getRawX() - mOffsetToParent);
                            mLayoutParams.y = (int) (event.getRawY() - mOffsetToParentY);
                            mWindowManager.updateViewLayout(FloatBallView.this, mLayoutParams);
                            mBigBallX = mImgBigBall.getX();
                            mBigBallY = mImgBigBall.getY();
                            mCurrentMode = MODE_MOVE;
                        } else {
                            doGesture(event);
                            doUp();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mIsTouching = false;

                        if (mIsLongTouch) {
                            mIsLongTouch = false;
                        } else if (isDoubleClick(event)) {
                            if (!isInMiddle) {
                                isInMiddle = true;
                                translationAnimator( mLayoutParams.x, ScreenUtils.getScreenWidth(getContext()) / 2-(mView.getWidth()/2));
                            }else{
                                isInMiddle = false;
                                translationAnimator(mLayoutParams.x, ScreenUtils.getScreenWidth(getContext()));
                            }
                        } else if (isClick(event)) {
//                            if (isAddCoverView) {
//                                isAddCoverView = false;
//                                FloatWindowManager.removeCoverView(getContext());
//                            } else {
//                                isAddCoverView = true;
//                                FloatWindowManager.addCoverView(getContext());
//                            }
                        } else {
                            saveValue();
                        }
                        mImgBall.setVisibility(VISIBLE);
                        mImgBigBall.setVisibility(INVISIBLE);
                        mCurrentMode = MODE_NONE;
                        break;
                }
                return true;
            }
        });
    }

    boolean isInMiddle = false;
    /**
     * 移除悬浮球
     */
    private void toRemove() {
        mVibrator.vibrate(mPattern, -1);
        FloatWindowManager.removeBallView(getContext());
    }

    /**
     * 判断是否是轻微滑动
     */
    private boolean isTouchSlop(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (Math.abs(x - mLastDownX) < mTouchSlop && Math.abs(y - mLastDownY) < mTouchSlop) {
            return true;
        }
        return false;
    }

    /**
     * 判断手势（左右滑动、上拉下拉)）
     */
    private void doGesture(MotionEvent event) {
        float offsetX = event.getX() - mLastDownX;
        float offsetY = event.getY() - mLastDownY;

        if (Math.abs(offsetX) < mTouchSlop && Math.abs(offsetY) < mTouchSlop) {
            return;
        }
        if (Math.abs(offsetX) > Math.abs(offsetY)) {
            if (offsetX > 0) {
                if (mCurrentMode == MODE_RIGHT) {
                    return;
                }
                mCurrentMode = MODE_RIGHT;
                mImgBigBall.setX(mBigBallX + OFFSET);
                mImgBigBall.setY(mBigBallY);
            } else {
                if (mCurrentMode == MODE_LEFT) {
                    return;
                }
                mCurrentMode = MODE_LEFT;
                mImgBigBall.setX(mBigBallX - OFFSET);
                mImgBigBall.setY(mBigBallY);
            }
        } else {
            if (offsetY > 0) {
                if (mCurrentMode == MODE_DOWN || mCurrentMode == MODE_GONE) {
                    return;
                }
                mCurrentMode = MODE_DOWN;
                mImgBigBall.setX(mBigBallX);
                mImgBigBall.setY(mBigBallY + OFFSET);
            } else {
                if (mCurrentMode == MODE_UP) {
                    return;
                }
                mCurrentMode = MODE_UP;
                mImgBigBall.setX(mBigBallX);
                mImgBigBall.setY(mBigBallY - OFFSET);
            }
        }
    }

    /**
     * 手指抬起后，根据当前模式触发对应功能
     */
    private void doUp() {
        String type = "";
        switch (mCurrentMode) {
            case MODE_LEFT:
                type = "green";
                updateColorValue("green", mGreen);
                break;
            case MODE_RIGHT:
                type = "blue";
                updateColorValue("blue", mBlue);
                break;
            case MODE_DOWN:
                if (mAlpha > 0 && !isBaseLine) {
                    mAlpha -= 2;
                    if (mAlpha <= 0) {
                        mAlpha = 0;
                        isBaseLine = true;
                    }
                } else {
                    mAlpha += 2;
                    if (mAlpha > 200) {
                        isBaseLine = false;
                    }
                }

                break;
            case MODE_UP:
                type = "red";
                updateColorValue("red", mRed);
                break;

        }
        CoverView coverView = FloatWindowManager.getCoverView();
        if (coverView != null) {
            coverView.setViewAlpha(mAlpha);
        }
        setColor(coverView, type);
        mImgBigBall.setX(mBigBallX);
        mImgBigBall.setY(mBigBallY);
    }

    private void setColor(CoverView coverView, String type) {
        if (coverView == null){
            return;
        }
        switch (type) {
            case "red":
                coverView.setRedColor(mRed);
                break;
            case "blue":
                coverView.setBlueColor(mBlue);
                break;
            case "green":
                coverView.setGreenColor(mGreen);
                break;
        }

    }

    private void updateColorValue(String type, int value) {
        if (value > 0 && !isBaseLine) {
            value -= 5;
            if (value <= 0) {
                value = 0;
                isBaseLine = true;
            }
        } else {
            value += 5;
            if (value > 255) {
                value = 255;
                isBaseLine = false;
            }

        }

        if ("red".equals(type)) {
            mRed = value;
        } else if ("green".equals(type)) {
            mGreen = value;
        } else if ("blue".equals(type)) {
            mBlue = value;
        }
    }

    private void saveValue() {
        CoverView coverView = FloatWindowManager.getCoverView();
        if (coverView == null) {
            return;
        }
        switch (mCurrentMode) {
            case MODE_LEFT:
                coverView.saveGreenColor();
                break;
            case MODE_RIGHT:
                coverView.saveBlueColor();
                break;
            case MODE_DOWN:
                coverView.saveAlpha();
                break;
            case MODE_UP:
                coverView.saveRedColor();
                break;

        }
        mImgBall.setAlpha(0.05f);
    }

    public void setLayoutParams(WindowManager.LayoutParams params) {
        mLayoutParams = params;
    }

    /**
     * 判断是否是长按
     */
    private boolean isLongClick(MotionEvent event) {
        float offsetX = Math.abs(event.getX() - mLastDownX);
        float offsetY = Math.abs(event.getY() - mLastDownY);
        long time = System.currentTimeMillis() - mLastDownTime;

        if (offsetX < mTouchSlop && offsetY < mTouchSlop && time >= LONG_CLICK_LIMIT) {
            //震动提醒
            mVibrator.vibrate(mPattern, -1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是单击
     */
    private boolean isClick(MotionEvent event) {
        float offsetX = Math.abs(event.getX() - mLastDownX);
        float offsetY = Math.abs(event.getY() - mLastDownY);
        long time = System.currentTimeMillis() - mLastDownTime;

        if (offsetX < mTouchSlop * 2 && offsetY < mTouchSlop * 2 && time < CLICK_LIMIT) {
            return true;
        } else {
            return false;
        }
    }

    //判断是否是双击
    private boolean isDoubleClick(MotionEvent event) {
        if (isClick(event)) {
            clickCount--;
            Logger.d("单击了 = " +clickCount);
        }else{
            return false;
        }
        Observable.just("")
                .timer(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        clickCount++;
                        if (clickCount == 2 && !doubleClick) {
                            if (isAddCoverView) {
                                isAddCoverView = false;
                                FloatWindowManager.removeCoverView(getContext());
                            } else {
                                isAddCoverView = true;
                                FloatWindowManager.addCoverView(getContext());
                            }
                        }
                    }
                });
        if (clickCount == 0) {
            doubleClick = true;
            Toast.makeText(getContext(), "双击了", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            doubleClick = false;
            return false;
        }
    }

    private void translationAnimator(final int currentPosition, final int toPosition) {
        ValueAnimator animator = ValueAnimator.ofFloat(currentPosition, toPosition);
        animator.setDuration(1000);
        animator.setInterpolator(new OvershootInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (Float) animation.getAnimatedValue();
                mLayoutParams.x = (int) animatedValue;
                mWindowManager.updateViewLayout(FloatBallView.this, mLayoutParams);
            }
        });
    }

    /**
     * 获取通知栏高度
     */
    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public int dip2px(float dip) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dip, getContext().getResources().getDisplayMetrics()
        );
    }

}
