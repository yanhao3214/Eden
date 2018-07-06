package com.gogo.haobutler.model.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function:
 */
public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;
    public Sport sport;

    @SerializedName("cw")
    public CarWash carWash;

    public class Comfort {
        public String brf;
        public String txt;
    }

    public class Sport {
        public String brf;
        public String txt;
    }

    public class CarWash {
        public String brf;
        public String txt;
    }
}
