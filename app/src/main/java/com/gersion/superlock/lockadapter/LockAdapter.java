package com.gersion.superlock.lockadapter;

import android.content.Context;
import android.view.View;

/**
 * Created by aa326 on 2018/1/9.
 */

public interface LockAdapter {
    View init(Context context);

    void onStart();

    void setLockCallback(LockCallback lockCallback);

}
