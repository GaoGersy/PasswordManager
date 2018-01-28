package com.gersion.superlock.base;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.gersion.superlock.R;


/**
 * Created by Administrator on 2017/2/10.
 */
public abstract class BaseAlertDialog {
    protected AlertDialog mDialog;
    protected Window mWindow;
    protected Context mContext;

    public BaseAlertDialog(Context context) {
        mContext = context;
        mDialog = new AlertDialog.Builder(context).create();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mWindow = mDialog.getWindow();
        mWindow.setBackgroundDrawableResource(R.drawable.transpant);
        mWindow.setContentView(getLayoutId());
        initView();
        initListener();
    }

//    private void startAnimator() {
//        final View container = mWindow.findViewById(R.id.container);
//        if (container==null){
//            return;
//        }
//        ValueAnimator animator = ValueAnimator.ofFloat(0.5f,1f);
//        animator.setInterpolator(new OvershootInterpolator());
//        animator.setDuration(500);
//        animator.start();
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float animatedValue = (Float) animation.getAnimatedValue();
//                container.setScaleX(animatedValue);
//                container.setScaleY(animatedValue);
//            }
//        });
//    }

    public void setCancelable(boolean isCancelable){
        mDialog.setCancelable(isCancelable);
    }

    public void setCanceledOnTouchOutside(boolean isCancelable){
        mDialog.setCanceledOnTouchOutside(isCancelable);
    }

    public void dismiss(){
        if (mDialog!=null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    protected View findViewById(int id){
       return mWindow.findViewById(id);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();
}
