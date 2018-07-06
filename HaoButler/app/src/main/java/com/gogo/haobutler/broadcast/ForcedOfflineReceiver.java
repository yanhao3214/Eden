package com.gogo.haobutler.broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.LoginActivity;
import com.gogo.haobutler.utils.ActivityAgent;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function: 强制下线广播接收器
 */
public class ForcedOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.broadcast_remind))
                .setMessage(context.getString(R.string.broadcast_forced_offline))
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.dialog_ok), ((dialog, which) -> {
                    ActivityAgent.finishAll();
                    context.startActivity(new Intent(context, LoginActivity.class));
                    dialog.dismiss();
                }))
                .create();
        alertDialog.show();

    }
}
