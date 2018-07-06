package com.gogo.haobutler.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.base.BaseActivity;

/**
 * @author: 闫昊
 * @date: 2018/6/17 0017
 * @function: 显示位置选择Fragment，并控制跳转到真正的天气展示Activity
 */
public class WeatherActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_layout);
        String weatherUrl = getSharedPreferences("weather", MODE_PRIVATE).getString("weatherUrl", "");
        if (!TextUtils.isEmpty(weatherUrl)) {
            Intent intent = new Intent(this, WeatherShowActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
