package com.yh.sdk.utils;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function:
 */
public class TimeUtils {
    private static final int NUMBER_NINE = 9;

    /**
     * 将秒数转化为**:**:**的时长格式
     */
    public static String sec2Time(int seconds) {
        int hour = seconds / 3600;
        int minute = seconds / 60 % 60;
        int second = seconds % 60;
        String hourStr = hour == 0 ? "" : (timeFormat(hour) + ":");
        return hourStr + timeFormat(minute) + ":" + timeFormat(second);
    }

    private static String timeFormat(int i) {
        if (i >= 0 && i <= NUMBER_NINE) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }
}
