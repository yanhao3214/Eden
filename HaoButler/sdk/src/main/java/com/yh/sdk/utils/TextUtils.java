package com.yh.sdk.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * @author: 闫昊
 * @date: 2018/7/5 0005
 * @function:
 */
public class TextUtils {

    /**
     * 设置字体
     * @param context
     * @param textView
     */
    public static void setFont(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }
}
