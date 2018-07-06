package com.gogo.haobutler.activity.base;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gogo.haobutler.R;
import com.gogo.haobutler.broadcast.ForcedOfflineReceiver;
import com.gogo.haobutler.utils.ActivityAgent;

/**
 * @author: 闫昊
 * @date: 2018/4/15
 * @function: Activity基类
 * 1.统一的属性
 * 2.统一的接口
 * 3.统一的方法
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public ActionBar actionBar;
    private ForcedOfflineReceiver mForcedOfflineReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ActivityAgent.add(this);
    }

    /**
     * 为任意处在栈顶的Activity注册广播：强制下线
     */
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter forcedOfflineFilter = new IntentFilter();
        forcedOfflineFilter.addAction("com.gogo.butler.BROADCAST_OFFLINE");
        mForcedOfflineReceiver = new ForcedOfflineReceiver();
        registerReceiver(mForcedOfflineReceiver, forcedOfflineFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mForcedOfflineReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityAgent.remove(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
