package com.gogo.haobutler.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.base.BaseActivity;

/**
 * @author: 闫昊
 * @date: 2018/4/15
 * @function:
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_layout);
        initData();
        initView();
    }

    private void initData() {
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {

    }
}
