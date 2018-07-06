package com.gogo.haobutler.utils;

import android.text.TextUtils;
import android.util.Log;

import com.gogo.haobutler.model.image.Gank;
import com.gogo.haobutler.model.study.Study;
import com.gogo.haobutler.model.video.VideoData;
import com.gogo.haobutler.model.video.VideoItem;
import com.gogo.haobutler.model.weather.City;
import com.gogo.haobutler.model.weather.County;
import com.gogo.haobutler.model.weather.Province;
import com.gogo.haobutler.model.weather.gson.base.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function:
 */
public class JsonParser {

    /**
     * TODO: 2018/6/18 0018 fastjson解析：try...catch?
     *
     * @param response 省份的json数据
     * @return 是否解析成功
     */
    public static boolean parseProvince(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Province province = new Province();
                    province.setName(object.getString("name"));
                    province.setProvinceCode(object.getInt("id"));
                    province.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * 解析城市数据
     */
    public static boolean parseCity(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    City city = new City();
                    city.setCityCode(object.getInt("id"));
                    city.setName(object.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * 解析县区数据
     */
    public static boolean parseCounty(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    County county = new County();
                    county.setName(object.getString("name"));
                    county.setCountyCode(object.getInt("id"));
                    county.setWeatherId(object.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * 解析天气数据
     */
    public static Weather parseWeather(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
                String weatherJson = jsonArray.get(0).toString();
                return new Gson().fromJson(weatherJson, Weather.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析Video数据
     */
    public static VideoData parseVideoData(String json) {
        if (!TextUtils.isEmpty(json)) {
            VideoData videoData = new VideoData();
            try {
                JSONObject object = new JSONObject(json);
                videoData.setNextPageUrl(object.optString("nextPageUrl", ""));
                videoData.setCurrentDate(object.optLong("date", 0));
                videoData.setNextPublishDate(object.optLong("nextPublishTime", 0));
                JSONArray array = object.getJSONArray("itemList");
                List<VideoItem> videoList = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    if ("video".equals(item.getString("type"))) {
                        JSONObject data = item.getJSONObject("data");
                        VideoItem videoItem = new VideoItem();
                        videoItem.setTitle(data.getString("title"));
                        videoItem.setDescription(data.getString("description"));
                        videoItem.setCollectionCount(data.getJSONObject("consumption").getInt("collectionCount"));
                        videoItem.setShareCount(data.getJSONObject("consumption").getInt("shareCount"));
                        videoItem.setReplyCount(data.getJSONObject("consumption").getInt("replyCount"));
                        videoItem.setSlogan(data.getString("slogan"));
                        videoItem.setCategory(data.getString("category"));
                        videoItem.setAuthorIcon(data.getJSONObject("author").getString("icon"));
                        videoItem.setAuthorName(data.getJSONObject("author").getString("name"));
                        videoItem.setAuthorDesc(data.getJSONObject("author").getString("description"));
                        videoItem.setCoverUrl(data.getJSONObject("cover").getString("homepage"));
                        videoItem.setDetailCoverUrl(data.getJSONObject("cover").getString("detail"));
                        videoItem.setPlayUrl(data.getString("playUrl"));
                        videoItem.setDuration(data.getInt("duration"));
                        //json原数据中个别"webUrl"对应的Object为null
                        JSONObject webUrlObject = data.optJSONObject("webUrl");
                        if (webUrlObject != null) {
                            videoItem.setWebUrl(webUrlObject.optString("raw", ""));
                        }
                        JSONArray playInfo = data.getJSONArray("playInfo");
                        if (playInfo.length() > 0) {
                            videoItem.setWidth(playInfo.getJSONObject(0).getInt("width"));
                            videoItem.setHeight(playInfo.getJSONObject(0).getInt("height"));
                        }
                        videoList.add(videoItem);
                    }
                }
                videoData.setCount(videoList.size());
                videoData.setItemList(videoList);
            } catch (JSONException e) {
                Log.e("yh", "parseVideoData: 解析失败" + e.getMessage());
                e.printStackTrace();
            }
            return videoData;
        }
        return null;
    }

    /**
     * 解析学习乐园数据
     */
    public static Study parseStudyData(String json) {
        if (!TextUtils.isEmpty(json)) {
            Study study = new Gson().fromJson(json, Study.class);
            Log.d("yh", "parseStudyData: study == " + study.toString());
            return study;
        }
        return null;
    }

    public static List<Gank> parseGankPic(String json) {
        List<Gank> ganks = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray results = object.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject temp = results.getJSONObject(i);
                Gank gank = new Gank();
                gank.setUrl(temp.getString("url"));
                gank.setDate(temp.optString("desc", ""));
                gank.setAuthor(temp.optString("who"));
                ganks.add(gank);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ganks;
    }
}
