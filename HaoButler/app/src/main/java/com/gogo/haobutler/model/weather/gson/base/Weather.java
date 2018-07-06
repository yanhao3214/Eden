package com.gogo.haobutler.model.weather.gson.base;

import com.gogo.haobutler.model.weather.gson.AQI;
import com.gogo.haobutler.model.weather.gson.Basic;
import com.gogo.haobutler.model.weather.gson.Forecast;
import com.gogo.haobutler.model.weather.gson.Now;
import com.gogo.haobutler.model.weather.gson.Suggestion;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function:
 */
public class Weather {
    @SerializedName("basic")
    public Basic basicWeather;
    public String status;
    public Now now;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    public AQI aqi;
    public Suggestion suggestion;

    @Override
    public String toString() {
        return "Weather{" +
                "basicWeather=" + basicWeather +
                ", status='" + status + '\'' +
                ", now=" + now +
                ", forecastList=" + forecastList +
                ", aqi=" + aqi +
                ", suggestion=" + suggestion +
                '}';
    }
}
