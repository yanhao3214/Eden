package com.gogo.haobutler.model.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function:
 */
public class Basic {
    @SerializedName("cid")
    private String weatherId;

    @SerializedName("location")
    private String county;

    @SerializedName("parent_city")
    private String city;

    @SerializedName("admin_area")
    private String province;

    private Update update;

    public class Update {
        @SerializedName("loc")
       public String updateTimeLoc;

        @SerializedName("utc")
        public String updateTimeUtc;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "Basic{" +
                "weatherId='" + weatherId + '\'' +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", update=" + update +
                '}';
    }
}
