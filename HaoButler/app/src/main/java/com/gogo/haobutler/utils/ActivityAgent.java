package com.gogo.haobutler.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function:
 */
public class ActivityAgent {
    private static List<Activity> mActivityList = new ArrayList<>();

    public static void remove(Activity activity) {
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
    }

    public static void add(Activity activity) {
        if (!mActivityList.contains(activity)) {
            mActivityList.add(activity);
        }
    }

    public static void finishAll() {
        for (Activity activity : mActivityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        mActivityList.clear();
    }
}
