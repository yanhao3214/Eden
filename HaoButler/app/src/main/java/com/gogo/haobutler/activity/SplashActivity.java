package com.gogo.haobutler.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gogo.haobutler.R;
import com.gogo.haobutler.utils.ShareUtil;
import com.romainpiel.titanic.library.Titanic;
import com.romainpiel.titanic.library.TitanicTextView;
import com.yh.sdk.utils.TextUtils;

import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobUser;

import static com.gogo.haobutler.utils.Consts.HANDLER_SPLASH;
import static com.gogo.haobutler.utils.Consts.IS_FIRST_RUNNING;
import static com.gogo.haobutler.utils.Consts.SPLASH_TIME;

/**
 * @author: 闫昊
 * @date: 2018/4/15
 * @function: Splash页面
 * 1.展示公司、个人Logo
 * 2.广告
 * 3.TitanicTextView
 * 4.自定义字体
 * 5.静态内部类Handler，防止内存泄露
 */

public class SplashActivity extends AppCompatActivity {
    private TitanicTextView titanicSplash;
    private boolean isFirst;
    private SplashHandler mHandler = new SplashHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
        initView();
    }

    private void initView() {
        titanicSplash = findViewById(R.id.titanic_splash);
        TextUtils.setFont(this, titanicSplash);
        isFirst = ShareUtil.getBoolean(this, IS_FIRST_RUNNING, true);
        if (isFirst) {
            ShareUtil.putBoolean(this, IS_FIRST_RUNNING, false);
        }
        mHandler.sendEmptyMessageDelayed(HANDLER_SPLASH, SPLASH_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class SplashHandler extends Handler {
        private WeakReference<Context> reference;

        SplashHandler(Activity activity) {
            this.reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashActivity activity = (SplashActivity) reference.get();
            switch (msg.what) {
                case HANDLER_SPLASH:
                    //判断是否是第一次进入App、是否有用户登录
                    Intent intent = new Intent(activity, activity.isFirst ? GuideActivity.class :
                            (BmobUser.getCurrentUser() == null ? LoginActivity.class : MainActivity.class));
                    activity.startActivity(intent);
                    activity.finish();
                    break;
                default:
                    break;
            }
        }
    }
}
