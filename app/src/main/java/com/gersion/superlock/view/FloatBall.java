package com.gersion.superlock.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gersion.superlock.R;
import com.gersion.superlock.listener.OnMenuStatusChangeListener;

import static com.gersion.superlock.app.SuperLockApplication.mContext;

/**
 * Created by aa326 on 2017/8/23.
 */

public class FloatBall extends FrameLayout {
    private final static long CLICK_LIMIT = 200;
    private ImageView mIvBall;
    private CircleMenu mCircleMenu;
    private float mLastDownX;
    private float mLastDownY;
    private Service mService;
    private Vibrator mVibrator;
    private WindowManager mWindowManager;
    private int mTouchSlop;
    private long mLastDownTime;
    private WindowManager.LayoutParams mLayoutParams;
    private DisplayMetrics mDisplayMetrics;

    public FloatBall(@NonNull Context context) {
        this(context,null);
    }

    public FloatBall(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_float_ball, this);
        mIvBall = (ImageView) view.findViewById(R.id.iv_ball);
        mCircleMenu = (CircleMenu) view.findViewById(R.id.circleMenu);
        mService = (Service) context;
//        mContext = context;
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mVibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        mCircleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.mipmap.icon_cancel)
                .addSubMenu(Color.parseColor("#258CFF"), R.mipmap.icon_home)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.icon_search)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.icon_notify)
                .addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.icon_setting)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.icon_gps);
        setListener();
    }

    private void setListener() {
        mIvBall.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        mImgBall.setAlpha(1f);
//                        mImgBigBall.setAlpha(0.5f);
//                        mIsTouching = true;
//                        mImgBall.setVisibility(INVISIBLE);
//                        mImgBigBall.setVisibility(VISIBLE);
                        mLastDownTime = System.currentTimeMillis();
                        mLastDownX = event.getX();
                        mLastDownY = event.getY();
//                        postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (!mIsLongTouch && mIsTouching && mCurrentMode == MODE_NONE) {
//                                    mIsLongTouch = isLongClick(event);
//                                }
//                            }
//                        }, LONG_CLICK_LIMIT);
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        if (!mIsLongTouch && isTouchSlop(event)) {
//                            return true;
//                        }
//                        if (mIsLongTouch && (mCurrentMode == MODE_NONE || mCurrentMode == MODE_MOVE)) {
//                            mLayoutParams.x = (int) (event.getRawX() - mOffsetToParent);
//                            mLayoutParams.y = (int) (event.getRawY() - mOffsetToParentY);
//                            mWindowManager.updateViewLayout(FloatBall.this, mLayoutParams);
//                            mBigBallX = mImgBigBall.getX();
//                            mBigBallY = mImgBigBall.getY();
//                            mCurrentMode = MODE_MOVE;
//                        } else {
//                            doGesture(event);
//                            doUp();
//                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (isClick(event)) {
//                            FloatWindowManager.changeSelectListViewStatus(getContext());
                            mCircleMenu.setVisibility(VISIBLE);
                            mIvBall.setVisibility(GONE);
                            mCircleMenu.openMenu();
                        }
                        break;
                }
                return true;
            }
        });

        mCircleMenu.setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {
            @Override
            public void onMenuOpened() {

            }

            @Override
            public void onMenuClosed() {
                mCircleMenu.setVisibility(INVISIBLE);
                mIvBall.setVisibility(VISIBLE);
            }
        });
    }

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

    public void setLayoutParams(WindowManager.LayoutParams params) {
        mLayoutParams = params;
    }

    private void translationAnimator(final int currentPosition, final int toPosition) {
        ValueAnimator animator = ValueAnimator.ofFloat(currentPosition, toPosition);
        animator.setDuration(300);
        animator.start();
        if (currentPosition < toPosition) {
            mLayoutParams.y = (int) (mLayoutParams.y+dp2px(75));
        } else {
            mLayoutParams.y = (int) (mLayoutParams.y-dp2px(75));
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (Float) animation.getAnimatedValue();
                mLayoutParams.x = (int) animatedValue;

                mWindowManager.updateViewLayout(FloatBall.this, mLayoutParams);
            }

        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

            }
        });
    }

    private float dp2px(float size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, mDisplayMetrics);
    }
}
