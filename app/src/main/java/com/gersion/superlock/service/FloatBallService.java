package com.gersion.floattools;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wangxiandeng on 2016/11/25.
 */

public class FloatBallService extends Service {
    public static final int TYPE_ADD = 0;
    public static final int TYPE_DEL = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("aa","intent = "+intent);
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                int type = data.getInt("type");
                if (type == TYPE_ADD) {
                    FloatWindowManager.addCoverView(this);
                    FloatWindowManager.addBallView(this);
                } else {
                    FloatWindowManager.removeBallView(this);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
