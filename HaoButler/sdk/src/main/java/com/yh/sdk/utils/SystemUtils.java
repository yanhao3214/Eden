package com.yh.sdk.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author: 闫昊
 * @date: 2018/7/5 0005
 * @function:
 */
public class SystemUtils {

    /**
     * 隐藏和显示软键盘
     *
     * @param view
     * @param isShow
     */
    public static void showKeyboard(View view, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (!isShow && imm.isActive(view)) {
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            } else if (isShow && !imm.isActive(view)) {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }
}
