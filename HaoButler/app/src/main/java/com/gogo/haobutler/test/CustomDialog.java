package com.gogo.haobutler.test;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.gogo.haobutler.R;

/**
 * @author: 闫昊
 * @date: 2018/5/5
 * @function: 练习用
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int width, int height, int layout, int gravity, int theme, int anim) {
        super(context, theme);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        setContentView(layout);
        params.windowAnimations = anim;
        window.setAttributes(params);
    }

    public CustomDialog(@NonNull Context context) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_login_layout, Gravity.CENTER, R.style.theme_hao_dialog, R.style.dialog_anim_style);
    }

    public CustomDialog(Context context, int layout) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layout, Gravity.CENTER, R.style.theme_hao_dialog, R.style.dialog_anim_style);
    }

    public CustomDialog(Context context, int layout, int theme) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layout, Gravity.CENTER, theme, R.style.dialog_anim_style);
    }

    public CustomDialog(Context context, int layout, int theme, int anim) {
        this(context, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, layout, Gravity.CENTER, theme, anim);
    }
}
