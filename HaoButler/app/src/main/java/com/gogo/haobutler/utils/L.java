package com.gogo.haobutler.utils;

import android.util.Log;

/**
 * @author: 闫昊
 * @date: 2018/4/15
 * @function: 日志工具类
 */

public class L {
    public static final Boolean LOG_PRINT = true;

    public static void e(String msg) {
        if (LOG_PRINT) {
            Log.e("HaoButler", msg);
        }
    }

    public static void d(String msg) {
        if (LOG_PRINT) {
            Log.d("HaoButler", msg);
        }
    }

    public static void i(String msg) {
        if (LOG_PRINT) {
            Log.i("HaoButler", msg);
        }
    }

    public static void v(String msg) {
        if (LOG_PRINT) {
            Log.v("HaoButler", msg);
        }
    }

    public static void w(String msg) {
        if (LOG_PRINT) {
            Log.w("HaoButler", msg);
        }
    }
}
