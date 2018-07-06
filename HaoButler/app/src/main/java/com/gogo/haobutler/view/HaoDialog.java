package com.gogo.haobutler.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.gogo.haobutler.R;

/**
 * @author: 闫昊
 * @date: 2018/4/20
 * @function:
 */

public class HaoDialog extends Dialog {

    /**
     * 用户接口
     *
     * @param context
     * @param layoutRes
     * @param style
     */
    public HaoDialog(@NonNull Context context, int layoutRes, int style) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layoutRes, style, Gravity.CENTER);
    }

    /**
     * 定义属性
     * 调用父类构造方法
     * 通过window和LayoutParams设置属性
     *
     * @param context
     * @param width
     * @param height
     * @param layoutRes
     * @param style
     * @param gravity
     * @param anim
     */
    public HaoDialog(Context context, int width, int height, int layoutRes, int style, int gravity, int anim) {
        super(context, style);
        //先设置正常，后设置则dialog不能match_parent
        setContentView(layoutRes);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width == 0 ? WindowManager.LayoutParams.MATCH_PARENT : width;
        params.height = height == 0 ? WindowManager.LayoutParams.MATCH_PARENT : height;
        params.gravity = gravity;
        window.setAttributes(params);
        window.setWindowAnimations(anim);
    }

    /**
     * 用户接口
     * 统一动画（主题）的实例
     *
     * @param context
     * @param width
     * @param height
     * @param layoutRes
     * @param style
     * @param gravity
     */
    public HaoDialog(Context context, int width, int height, int layoutRes, int style, int gravity) {
        this(context, width, height, layoutRes, style, gravity, R.style.dialog_anim_style);
    }
}
