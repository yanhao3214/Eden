package com.gogo.haobutler.model.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function:
 */
public class AQI {
    @SerializedName("city")
    public AQICity aqiCity;
    public class AQICity {
        public String aqi;
        public String pm25;
        @SerializedName("qlty")
        public String airQuality;
    }
}
