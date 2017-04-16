
package com.gersion.superlock.utils;

import android.util.Log;

/**
 * ClassName:LogUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2016-7-22改良版，能传入Object类型的数据 <br/>
 *
 * @author Alpha
 */
public final class LogUtils {
    /**
     * Log defaulticon tag.
     */
    private static String sTagDefault = "Gersy";

    /**
     * Log toggle for release, defaulticon value is false.
     */
    private static boolean sToggleRelease = false;

    /**
     * Log toggle for print Throwable info, defaulticon value is true.
     */
    private static boolean sToggleThrowable = true;

    /**
     * Log toggle for print thread name, defaulticon value is false.
     */
    private static boolean sToggleThread = false;

    /**
     * Log toggle for print class name and method name, defaulticon value is false.
     */
    private static boolean sToggleClassMethod = true;

    /**
     * Log toggle for print file name and code line number, defaulticon value is false.
     */
    private static boolean sToggleFileLineNumber = true;

    public static void e(String tag, String msg, Throwable e) {
        printLog(Log.ERROR, tag, msg, e);
    }

    public static void e(String msg, Throwable e) {
        printLog(Log.ERROR, null, msg, e);
    }

    public static void e(String msg) {
        printLog(Log.ERROR, null, msg, null);
    }

    /**
     * DESC : 如果传入的是Object 类型就加上一个空串 . <br/>
     *
     * @param msg
     */
    public static void e(Object msg) {
        printLog(Log.ERROR, null, msg + "", null);
    }

    public static void w(String tag, String msg, Throwable e) {
        printLog(Log.WARN, tag, msg, e);
    }

    public static void w(String msg, Throwable e) {
        printLog(Log.WARN, null, msg, e);
    }

    public static void w(String msg) {
        printLog(Log.WARN, null, msg, null);
    }

    /**
     * DESC : 如果传入的是Object 类型就加上一个空串 . <br/>
     *
     * @param msg
     */
    public static void w(Object msg) {
        printLog(Log.WARN, null, msg + "", null);
    }

    /**
     * DESC : 如果传入的是Object 类型就加上一个空串 . <br/>
     *
     * @param msg
     */


    public static void i(String tag, String msg, Throwable e) {
        printLog(Log.INFO, tag, msg, e);
    }

    public static void i(String msg) {
        printLog(Log.INFO, null, msg, null);
    }

    /**
     * DESC : 如果传入的是Object 类型就加上一个空串 . <br/>
     *
     * @param msg
     */
    public static void i(Object msg) {
        printLog(Log.INFO, null, msg + "", null);
    }

    public static void d(String tag, String msg, Throwable e) {
        printLog(Log.DEBUG, tag, msg, e);
    }

    public static void d(String msg, Throwable e) {
        printLog(Log.DEBUG, null, msg, e);
    }

    public static void d(String tag, String msg) {
        printLog(Log.DEBUG, tag, msg, null);
    }

    public static void d(String msg) {
        printLog(Log.DEBUG, null, msg, null);
    }

    /**
     * DESC : 如果传入的是Object 类型就加上一个空串 . <br/>
     *
     * @param msg
     */
    public static void d(Object msg) {
        printLog(Log.DEBUG, null, msg + "", null);
    }

    public static void v(String tag, String msg, Throwable e) {
        printLog(Log.VERBOSE, tag, msg, e);
    }

    public static void v(String tag, String msg) {
        printLog(Log.VERBOSE, tag, msg, null);
    }

    public static void v(String msg) {
        printLog(Log.VERBOSE, null, msg, null);
    }

    /**
     * DESC : 如果传入的是Object 类型就加上一个空串 . <br/>
     *
     * @param msg
     */
    public static void v(Object msg) {
        printLog(Log.VERBOSE, null, msg + "", null);
    }

    private static void printLog(int logType, String tag, String msg, Throwable e) {
        String tagStr = (tag == null) ? sTagDefault : tag;
        if (sToggleRelease) {
            if (logType < Log.INFO) {
                return;
            }
            String msgStr =
                    (e == null) ? msg : (msg + "\n" + Log.getStackTraceString(e));

            switch (logType) {
                case Log.ERROR:
                    Log.e(tagStr, msgStr);

                    break;
                case Log.WARN:
                    Log.w(tagStr, msgStr);

                    break;
                case Log.INFO:
                    Log.i(tagStr, msgStr);

                    break;
                default:
                    break;
            }

        } else {
            StringBuilder msgStr = new StringBuilder();

            if (sToggleThread || sToggleClassMethod || sToggleFileLineNumber) {
                Thread currentThread = Thread.currentThread();

                if (sToggleThread) {
                    msgStr.append("<");
                    msgStr.append(currentThread.getName());
                    msgStr.append("> ");
                }

                if (sToggleClassMethod) {
                    StackTraceElement ste = currentThread.getStackTrace()[4];
                    String className = ste.getClassName();
                    msgStr.append("[类名");
                    msgStr.append(className == null ? null
                            : className.substring(className.lastIndexOf('.') + 1));
                    msgStr.append("方法名");
                    msgStr.append(ste.getMethodName());
                    msgStr.append(" ] ");
                }

                if (sToggleFileLineNumber) {
                    StackTraceElement ste = currentThread.getStackTrace()[4];
                    msgStr.append("[文件名");
                    msgStr.append(ste.getFileName());
                    msgStr.append("行数");
                    msgStr.append(ste.getLineNumber());
                    msgStr.append(" ] ");
                }
            }

            msgStr.append(" 日志内容");
            msgStr.append(msg);
            if (e != null && sToggleThrowable) {
                msgStr.append('\n');
                msgStr.append(Log.getStackTraceString(e));
            }

            switch (logType) {
                case Log.ERROR:
                    Log.e(tagStr, msgStr.toString());

                    break;
                case Log.WARN:
                    Log.w(tagStr, msgStr.toString());

                    break;
                case Log.INFO:
                    Log.i(tagStr, msgStr.toString());

                    break;
                case Log.DEBUG:
                    Log.d(tagStr, msgStr.toString());

                    break;
                case Log.VERBOSE:
                    Log.v(tagStr, msgStr.toString());

                    break;
                default:
                    break;
            }
        }
    }

}
