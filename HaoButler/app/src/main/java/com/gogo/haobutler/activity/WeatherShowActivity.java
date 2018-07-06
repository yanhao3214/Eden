package com.gogo.haobutler.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gogo.haobutler.R;
import com.gogo.haobutler.adapter.WeatherForestAdapter;
import com.gogo.haobutler.model.weather.gson.Forecast;
import com.gogo.haobutler.model.weather.gson.base.Weather;
import com.gogo.haobutler.activity.base.BaseActivity;
import com.gogo.haobutler.service.WeatherService;
import com.gogo.haobutler.utils.JsonParser;
import com.gogo.haobutler.utils.net.HttpCallback;
import com.gogo.haobutler.utils.net.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function: 天气展示Activity
 */
public class WeatherShowActivity extends BaseActivity {

    /**
     * UI
     */
    private TextView tvCity;
    private TextView tvUpdate;
    private TextView tvTemperature;
    private ImageView ivCondition;
    private TextView tvCondition;
    private TextView tvWindScale;
    private TextView tvHumidity;
    private TextView tvPressure;
    private TextView tvQAI;
    private TextView tvPM25;
    private ListView lvForecast;
    private TextView tvComfortBrief;
    private TextView tvComfort;
    private TextView tvSportBrief;
    private TextView tvSport;
    private TextView tvCarWashBrief;
    private TextView tvCarWash;
    public SwipeRefreshLayout swipeRefreshLayout;
    public DrawerLayout drawerLayout;

    /**
     * Data
     */
    private WeatherForestAdapter mAdapter;
    private Weather mWeather;
    private String mUrl;
    private List<Forecast> mForecastList = new ArrayList<>();
    private WeatherHandler mHandler = new WeatherHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_show_layout);
        initView();
        initData();
    }

    private void initView() {
        tvCity = findViewById(R.id.tv_title_loc);
        tvUpdate = findViewById(R.id.tv_title_time);
        tvTemperature = findViewById(R.id.tv_title_temp);
        ivCondition = findViewById(R.id.iv_title_weather);
        tvCondition = findViewById(R.id.tv_title_condition);
        tvWindScale = findViewById(R.id.tv_wind_scale);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvPressure = findViewById(R.id.tv_pressure);
        tvQAI = findViewById(R.id.tv_aqi_value);
        tvPM25 = findViewById(R.id.tv_pm25_value);
        lvForecast = findViewById(R.id.list_view_forecast);
        tvComfortBrief = findViewById(R.id.tv_comfort_brief);
        tvComfort = findViewById(R.id.tv_comfort);
        tvSportBrief = findViewById(R.id.tv_sport_brief);
        tvSport = findViewById(R.id.tv_sport);
        tvCarWashBrief = findViewById(R.id.tv_car_wash_brief);
        tvCarWash = findViewById(R.id.tv_car_wash);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(() -> refreshUI(mUrl));
    }

    /**
     * 1.若第一次从AreaFragment跳转过来，从intent中获取天气链接
     *   若再次进入，从sp中直接获取先前的天气链接;
     * 2.发送网络请求，获取实时天气的json数据;
     * 3.解析json数据到Weather对象;
     * 4.通过Handler传递Weather对象到主线程，更新UI.
     */
    private void initData() {
        startService(new Intent(this, WeatherService.class));
        mAdapter = new WeatherForestAdapter(this, mForecastList);
        lvForecast.setAdapter(mAdapter);
        mUrl = getSharedPreferences("weather", MODE_PRIVATE).getString("weatherUrl", "");
        if (TextUtils.isEmpty(mUrl)) {
            mUrl = getIntent().getStringExtra("weatherUrl");
            getSharedPreferences("weather", MODE_PRIVATE)
                    .edit()
                    .putString("weatherUrl", mUrl)
                    .apply();
        }
        refreshUI(mUrl);
    }

    @Override
    public void onClick(View v) {

    }

    public void refreshUI(String url) {
        mUrl = url;
        HttpUtil.getRequest(url, new HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("yh", "WeatherShowActivity: onSuccess---> response == " + response);
                final Weather weather = JsonParser.parseWeather(response);
                Message msg = Message.obtain();
                msg.obj = weather;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFail(Exception e) {
                Looper.prepare();
                Toast.makeText(WeatherShowActivity.this, R.string.weather_data_failed, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                Looper.loop();
            }
        });
    }

    private class WeatherHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mWeather = (Weather) msg.obj;
            showData(mWeather);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showData(Weather mWeather) {
        swipeRefreshLayout.setRefreshing(false);
        tvCity.setText(mWeather.basicWeather.getCounty());
        tvUpdate.setText(mWeather.basicWeather.getUpdate().updateTimeLoc);
        tvTemperature.setText(mWeather.now.temperature + getString(R.string.weather_temp_symbol));
        tvCondition.setText(mWeather.now.condition.weatherCondition);
        tvWindScale.setText(mWeather.now.windScale + getString(R.string.weather_wind_scale));
        tvHumidity.setText(mWeather.now.humidity + getString(R.string.weather_percent_dumidity));
        tvPressure.setText(mWeather.now.pressure + getString(R.string.weather_pressure_hpa));
        tvQAI.setText(mWeather.aqi.aqiCity.aqi);
        tvPM25.setText(mWeather.aqi.aqiCity.pm25);
        tvComfortBrief.setText(getString(R.string.weather_forecast_comfort)
                + mWeather.suggestion.comfort.brf);
        tvComfort.setText(mWeather.suggestion.comfort.txt);
        tvSportBrief.setText(getString(R.string.weather_forecast_sport)
                + mWeather.suggestion.sport.brf);
        tvSport.setText(mWeather.suggestion.sport.txt);
        tvCarWashBrief.setText(getString(R.string.weather_forecast_car_wash)
                + mWeather.suggestion.carWash.brf);
        tvCarWash.setText(mWeather.suggestion.carWash.txt);
        mForecastList.clear();
        mForecastList.addAll(mWeather.forecastList);
        mAdapter.notifyDataSetChanged();
        int code = Integer.parseInt(mWeather.now.condition.code);
        int resId;
        switch (code) {
            case 100:
                resId = R.drawable.ic_100_weather;
                break;
            default:
            case 101:
                resId = R.drawable.ic_101_weather;
                break;
            case 104:
                resId = R.drawable.ic_104_weather;
                break;
            case 302:
                resId = R.drawable.ic_302_weather;
                break;
            case 305:
                resId = R.drawable.ic_305_weather;
                break;
            case 306:
            case 307:
                resId = R.drawable.ic_3067_weather;
                break;
            case 310:
                resId = R.drawable.ic_310_weather;
                break;
            case 401:
            case 402:
            case 499:
                resId = R.drawable.ic_4012_499_weather;
                break;
        }
        ivCondition.setImageResource(resId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
