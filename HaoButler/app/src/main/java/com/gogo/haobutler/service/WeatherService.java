package com.gogo.haobutler.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.WeatherShowActivity;
import com.gogo.haobutler.utils.net.HttpCallback;
import com.gogo.haobutler.utils.net.HttpUtil;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function: 天气信息后台自动更新
 */
public class WeatherService extends Service {
    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intentUpdate = new Intent(this, WeatherService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, intentUpdate, 0);
        //触发数据更新的间隔时间
        long trigger = SystemClock.elapsedRealtime() + 10 * 1000;
        alarmManager.cancel(pi);
        //使用相对时间、睡眠状态下也会唤醒系统执行服务
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, trigger, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        String url = getSharedPreferences("weather", MODE_PRIVATE).getString("weatherUrl", "");
        if (!TextUtils.isEmpty(url)) {
            HttpUtil.getRequest(url, new HttpCallback() {
                @Override
                public void onSuccess(String response) {
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), WeatherShowActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), "default")
                            .setContentTitle("生活小助手")
                            .setContentText("天气已更新")
                            .setAutoCancel(true)
                            .setContentIntent(pi)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_weather_nav))
                            .setSmallIcon(R.drawable.ic_hao)
                            .build();
                    if (manager != null) {
                        manager.notify(1, notification);
                    }
                    getSharedPreferences("weather", MODE_PRIVATE)
                            .edit()
                            .putString("weatherInfo", response)
                            .apply();
                }

                @Override
                public void onFail(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
