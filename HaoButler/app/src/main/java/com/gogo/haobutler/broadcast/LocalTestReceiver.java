package com.gogo.haobutler.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function: 本地广播接收器
 */
public class LocalTestReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("yh", "localTestReceiver: received");
        Toast.makeText(context, "接收到本地广播啦！", Toast.LENGTH_LONG).show();
    }
}
