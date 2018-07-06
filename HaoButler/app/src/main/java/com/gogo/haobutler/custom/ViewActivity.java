package com.gogo.haobutler.custom;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.MainActivity;
import com.gogo.haobutler.activity.base.BaseActivity;
import com.gogo.haobutler.service.AlarmService;
import com.gogo.haobutler.service.TestService;
import com.gogo.haobutler.test.ListVideoActivity;
import com.gogo.haobutler.test.SimpleVideoActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: 闫昊
 * @date: 2018/6/12 0012
 * @function:
 */
public class ViewActivity extends BaseActivity {
    private Random random = new Random();
    private Button btnRandom;
    private TextView tvRandom;
    private CodeView codeView;
    private Button btnNotification;
    private Button btnSimpleVideo;
    private Button btnListViedo;
    private ImageView ivGlide;
    private Banner mBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test_layout);
        btnRandom = findViewById(R.id.btn_random_test);
        btnRandom.setOnClickListener(this);
        tvRandom = findViewById(R.id.tv_random_text);
        codeView = findViewById(R.id.code_view);
        ivGlide = findViewById(R.id.iv_glide_test);
        btnNotification = findViewById(R.id.btn_send_notification);
        mBanner = findViewById(R.id.banner_test);
        initBanner();

        btnNotification.setOnClickListener(this);
        findViewById(R.id.btn_alarm).setOnClickListener(this);
        findViewById(R.id.btn_service).setOnClickListener(this);
        Glide.with(this).load("http://guolin.tech/test.gif").into(ivGlide);
        findViewById(R.id.btn_simple_video).setOnClickListener(this);
        findViewById(R.id.btn_list_video).setOnClickListener(this);
    }

    private void initBanner() {
        List<String> urls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        urls.add("http://img.mukewang.com/54bf7e1f000109c506000338-590-330.jpg");
        urls.add("http://upload.techweb.com.cn/2015/0114/1421211858103.jpg");
        urls.add("http://img1.cache.netease.com/catchpic/A/A0/A0153E1AEDA115EAE7061A0C7EBB69D2.jpg");
        urls.add("http://image.tianjimedia.com/uploadImages/2015/202/27/57RF8ZHG8A4T_5020a2a4697650b89c394237ba9ffbb45fe8555a2cbec-6O6nmI_fw658.jpg");
        titles.add("轮播图1");
        titles.add("轮播图2");
        titles.add("轮播图3");
        titles.add("轮播图4");
        mBanner.setImages(urls)
                .setBannerTitles(titles)
                .setImageLoader(new GlideLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setOnBannerListener(position -> Toast.makeText(ViewActivity.this, "点击了轮播图的第一张", Toast.LENGTH_SHORT).show())
                .setBannerAnimation(Transformer.Accordion)
                .start();

    }

    /**
     * Banner 图片加载器
     */
    private class GlideLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            RequestOptions glideOptions = new RequestOptions().placeholder(R.drawable.ic_error_image).error(R.drawable.ic_error_image);
            Glide.with(context).load(path).apply(glideOptions).into(imageView);
        }
    }

    private char[] getCode(int length) {
        char[] code = new char[length];
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                code[i] = (char) (random.nextInt(10) + 48);
            } else {
                code[i] = (char) (random.nextInt(26) + (random.nextBoolean() ? 65 : 97));
            }
        }
        return code;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_random_test:
                tvRandom.setText(String.valueOf(codeView.getCode()));
                break;
            case R.id.btn_send_notification:
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
                Notification notificationCompat = new NotificationCompat.Builder(this, "default")
                        .setContentTitle("生活小助手")
                        .setContentText("您的生活小管家正在后台为您持续服务")
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_basketball_nav))
                        .setSmallIcon(R.drawable.ic_courier_nav)
                        .setContentIntent(pi)
                        .setAutoCancel(true)
                        .build();
                if (manager != null) {
                    manager.notify(1, notificationCompat);
                }
                break;
            case R.id.btn_alarm:
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intentAlarm = new Intent(this, AlarmService.class);
                PendingIntent piAlarm = PendingIntent.getService(this, 0, intentAlarm, 0);
                //noinspection ConstantConditions
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 3000, piAlarm);
                break;
            case R.id.btn_service:
                startService(new Intent(this, TestService.class));
                break;
            case R.id.btn_simple_video:
                startActivity(new Intent(this, SimpleVideoActivity.class));
                break;
            case R.id.btn_list_video:
                startActivity(new Intent(this, ListVideoActivity.class));
                break;
            default:
                break;
        }
    }
}
