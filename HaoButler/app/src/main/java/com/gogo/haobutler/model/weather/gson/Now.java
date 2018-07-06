package com.gogo.haobutler.model.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function:
 */
public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("fl")
    public String feelingTemperature;

    @SerializedName("lon")
    public String longitude;

    @SerializedName("lat")
    public String latitude;

    @SerializedName("hum")
    public String humidity;

    @SerializedName("pres")
    public String pressure;

    @SerializedName("wind_dir")
    public String windDirection;

    @SerializedName("wind_sc")
    public String windScale;

    @SerializedName("vis")
    public String visibleDistance;

    @SerializedName("cond")
    public Condition condition;

    public class Condition {
        public String code;

        @SerializedName("txt")
        public String weatherCondition;
    }
}
