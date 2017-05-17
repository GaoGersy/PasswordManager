package com.gersion.superlock.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.transition.VisibilityPropagation;
import android.view.Gravity;
import android.view.ViewGroup;

import com.gersion.superlock.activity.LockActivity;
import com.gersion.superlock.utils.MyConstants;
import com.gersion.superlock.utils.SpfUtils;

/**
 * 是所有窗体控件的父类，封装了检测程序是否在前台的方法，如果进入后台，再次进入应用会进入登录界面
 *
 * @author Ger
 */
public class BaseActivity extends AppCompatActivity {
    public static int visibleCount = 0;
    public int liveCount = 0;
    public long lastActive = 0;
    public long lockTimeOut = 0;
    public int UNLOCK_PASSWORD = 0;
    public int LOCK_PASSWORD = 1;
    public int type;
    public boolean NEED_LOCK;

    @Override
    protected void onStart() {
        super.onStart();
        visibleCount++;
//        Logger.d(this.getClass().getSimpleName()+"  onstart: "+visibleCount+"  需要锁屏"+shouldLockSceen(this));
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    private boolean shouldLockSceen(Activity activity) {


        // no enough timeout
        long passedTime = System.currentTimeMillis() - lastActive;
        if (lastActive > 0 && passedTime <= lockTimeOut) {
            return false;
        }

        // start more than one page
        if (visibleCount > 1) {
            return false;
        }
//        if (visibleCount > 1) {
//            return false;
//        }

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureLockScreenActivityTransitions(this);
        super.onCreate(savedInstanceState);
        type = UNLOCK_PASSWORD;
        liveCount++;
    }


    @Override
    protected void onResume() {

        boolean isLock = SpfUtils.getBoolean(this, MyConstants.IS_LOCK, true);
        if (isLock && shouldLockSceen(this) && (!(this instanceof LockActivity))) {
            Intent intent = new Intent(this,
                    LockActivity.class);
            startActivity(intent);
        }
//        visibleCount++;
//        Logger.d(this.getClass().getSimpleName()+"  onResume: "+visibleCount+"   需要锁屏"+shouldLockSceen(this));
        super.onResume();
//        lastActive = 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        visibleCount--;

//        Logger.d(this.getClass().getSimpleName()+"  onStop: "+visibleCount+"   需要锁屏"+shouldLockSceen(this));
        if (visibleCount == 0) {
            lastActive = System.currentTimeMillis();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        liveCount--;
        if (liveCount == 0) {
            lastActive = System.currentTimeMillis();
        }
    }

    public static void configureLockScreenActivityTransitions(Activity paramActivity) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        paramActivity.getWindow().requestFeature(13);
        paramActivity.getWindow().requestFeature(12);
        paramActivity.getWindow().setTransitionBackgroundFadeDuration(750L);
        Object localObject = new Slide();
        ((Slide) localObject).setSlideEdge(Gravity.TOP);
        ((Slide) localObject).addTarget(2131624245);
        Slide localSlide = new Slide();
        localSlide.setSlideEdge(Gravity.LEFT);
        localSlide.addTarget(2131624246);
        localSlide.addTarget(2131624247);
        localSlide.addTarget(2131624249);
        localSlide.setPropagation(new VisibilityPropagation() {
            public long getStartDelay(ViewGroup paramAnonymousViewGroup, Transition paramAnonymousTransition, TransitionValues paramAnonymousTransitionValues1, TransitionValues paramAnonymousTransitionValues2) {
                return 0L;
            }
        });
        TransitionSet localTransitionSet = new TransitionSet();
        localTransitionSet.setOrdering(0);
        localTransitionSet.setDuration(500L);
        localTransitionSet.setStartDelay(250L);
        localTransitionSet.addTransition((Transition) localObject);
        localTransitionSet.addTransition(localSlide);
        paramActivity.getWindow().setExitTransition(localTransitionSet);
        paramActivity.getWindow().setReturnTransition(localTransitionSet);
        localObject = localTransitionSet.clone();
        ((TransitionSet) localObject).setStartDelay(0L);
        paramActivity.getWindow().setEnterTransition((Transition) localObject);
        paramActivity.getWindow().setReenterTransition((Transition) localObject);
    }

}