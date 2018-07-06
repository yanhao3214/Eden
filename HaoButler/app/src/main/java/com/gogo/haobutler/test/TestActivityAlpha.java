package com.gogo.haobutler.test;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gogo.haobutler.R;
import com.gogo.haobutler.broadcast.LocalTestReceiver;
import com.gogo.haobutler.activity.base.BaseActivity;

/**
 * @author: 闫昊
 * @date: 2018/6/17 0017
 * @function:
 */
public class TestActivityAlpha extends BaseActivity implements View.OnClickListener {
    private Button btnForcedOffline;
    private Button btnLocalTest;
    private LocalBroadcastManager mLocalBroadcastManager;
    private LocalTestReceiver mLocalTestReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nav_layout);
        initView();
        registerLocalBroadcast();
    }

    private void initView() {
        btnForcedOffline = findViewById(R.id.btn_forced_offline_test);
        btnForcedOffline.setOnClickListener(this);
        btnLocalTest = findViewById(R.id.btn_local_broadcast_test);
        btnLocalTest.setOnClickListener(this);


    }

    /**
     * 注册接收本地广播
     */
    private void registerLocalBroadcast() {
        IntentFilter localBroadcastFilter = new IntentFilter();
        localBroadcastFilter.addAction("com.gogo.butler.LOCAL_BROADCAST");
        mLocalTestReceiver = new LocalTestReceiver();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mLocalTestReceiver, localBroadcastFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(mLocalTestReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发送普通广播：强制下线
            case R.id.btn_forced_offline_test:
                Intent offlineIntent = new Intent("com.gogo.butler.BROADCAST_OFFLINE");
                sendBroadcast(offlineIntent);
                break;
            //发送本地广播：测试用
            case R.id.btn_local_broadcast_test:
                Intent localBroadcastIntent = new Intent("com.gogo.butler.LOCAL_BROADCAST");
                mLocalBroadcastManager.sendBroadcast(localBroadcastIntent);
                break;
            default:
                break;
        }
    }
}
