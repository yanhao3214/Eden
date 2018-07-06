package com.gogo.haobutler.model.weather;

import org.litepal.crud.DataSupport;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function:
 */
public class County extends DataSupport {
    private int countyCode;
    private String name;
    private String weatherId;
    private int cityId;

    public int getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(int countyCode) {
        this.countyCode = countyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "County{" +
                "countyCode=" + countyCode +
                ", name='" + name + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
