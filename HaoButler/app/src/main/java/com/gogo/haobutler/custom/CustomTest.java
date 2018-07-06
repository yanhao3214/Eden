package com.gogo.haobutler.custom;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewConfiguration;

/**
 * @author: 闫昊
 * @date: 2018/6/12 0012
 * @function:
 */
public class CustomTest extends AppCompatActivity{
    public CustomTest() {
        ViewConfiguration.get(getApplicationContext()).getScaledTouchSlop();
    }
}
