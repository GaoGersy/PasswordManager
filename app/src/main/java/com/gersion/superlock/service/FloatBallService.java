package com.gersion.superlock.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gersion.superlock.utils.ConfigManager;

/**
 * Created by wangxiandeng on 2016/11/25.
 */

public class FloatBallService extends Service {
    public static final int TYPE_ADD = 0;
    public static final int TYPE_DEL = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("color_lock_type_title","intent = "+intent);
        if (intent != null) {
            Bundle data = intent.getExtras();
            if (data != null) {
                int type = data.getInt("type");
                if (type == TYPE_ADD) {
                    FloatWindowManager.addBallView(this);
                    ConfigManager.getInstance().setEnableFloatBall(true);
                } else {
                    FloatWindowManager.removeBallView(this);
                    ConfigManager.getInstance().setEnableFloatBall(false);
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

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
