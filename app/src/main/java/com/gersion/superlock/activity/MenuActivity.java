package com.gersion.superlock.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;

import com.gersion.superlock.R;


public class MenuActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_menu);
        // 占满屏
        LayoutParams lay = getWindow().getAttributes();
        lay.height = LayoutParams.MATCH_PARENT;
        lay.width = LayoutParams.MATCH_PARENT;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.push_up_out);
    }

}

