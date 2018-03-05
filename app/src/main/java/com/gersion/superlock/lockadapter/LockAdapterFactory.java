package com.gersion.superlock.lockadapter;

import com.gersion.superlock.utils.ConfigManager;
import com.gersion.superlock.utils.MyConstants;

/**
 * Created by aa326 on 2018/1/9.
 */

public class LockAdapterFactory {

    public static LockAdapter create() {
        return create(MyConstants.LockMode.MODE_LOCK);
    }

    public static LockAdapter create(int mode) {
        int lockType = ConfigManager.getInstance().getLockType();
        return create(lockType, mode);
    }

    public static LockAdapter initLock() {
        int lockType = ConfigManager.getInstance().getLockType();
        lockType=MyConstants.LockType.TYPE_FINGER_PRINT;
        return initLock(lockType);
    }

    public static LockAdapter initLock(int lockType) {
        if (lockType == MyConstants.LockType.TYPE_PATTERN) {
            String patternString = ConfigManager.getInstance().getPatternString();
            if (patternString == null) {
                lockType = MyConstants.LockType.TYPE_PIN;
            }
        }
        return create(lockType, MyConstants.LockMode.MODE_LOCK);
    }

    public static LockAdapter create(int lockType, int lockMode) {
        switch (lockType){
            case MyConstants.LockType.TYPE_FINGER_PRINT:
                return new FingerPrintAdapter();
            case MyConstants.LockType.TYPE_PATTERN:
                return new PatternLockAdapter(lockMode);
            case MyConstants.LockType.TYPE_PIN:
                return new PinAdapter(lockMode);
            default:
                return new PinAdapter(lockMode);
        }
    }
}
