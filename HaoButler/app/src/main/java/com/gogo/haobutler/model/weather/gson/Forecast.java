package com.gogo.haobutler.model.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function:
 */
public class Forecast {
    @SerializedName("date")
    public String forecastDate;

    @SerializedName("cond")
    public Condition condition;

    @SerializedName("tmp")
    public Temperature temperature;

    public class Condition {
        @SerializedName("txt_d")
        public String condition;
    }

    public class Temperature {
        public String max;
        public String min;
    }
}
