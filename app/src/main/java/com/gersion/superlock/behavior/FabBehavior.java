package com.gersion.superlock.behavior;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import static android.view.View.VISIBLE;

public class FabBehavior extends CoordinatorLayout.Behavior<View> {
    private boolean mSwitchFabStatus =true;

    public FabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //判断滑动方向，因为我们只要垂直滑动，所以用nestedScrollAxes去&ViewCompat.SCROLL_AXIS_VERTICAL，如果不为0，就是垂直
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }
    //根据滑动距离，显示隐藏。
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (dy > 10) {
            hideFab(child);
        } else if (dy < -10) {
            showFab(child);
        }
    }

    public void hideFab(View view) {
        if (mSwitchFabStatus) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1F, 0F);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1F, 0F);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1F, 0F);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY, alpha);
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new OvershootInterpolator());
            animatorSet.start();

            mSwitchFabStatus = false;
        }
    }

    /**
     * 显示FloatingActionButtonPlus
     */
    public void showFab(View view) {
        if (!mSwitchFabStatus) {
            view.setVisibility(VISIBLE);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0F, 1F);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0F, 1F);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0F, 1F);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY, alpha);
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new OvershootInterpolator());
            animatorSet.start();
            mSwitchFabStatus = true;
        }
    }
}
