package com.gogo.haobutler.utils;

import com.gogo.haobutler.callback.EdenNetCallback;

import org.apache.http.client.HttpClient;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function:
 */
public class HttpCenter {

    /**
     * 获取bing每日一图
     */
    public static void getBingPic(EdenNetCallback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Consts.BING_PIC_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.error(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    callback.ok(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取gank福利图片
     * @param count 每次获取个数
     * @param page 图片页
     * @param callback 回调
     */
    public static void getGankPic(int count, int page, EdenNetCallback callback) {
        String url = Consts.GANK_PIC_BASE_URL + count + "/" + page;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.error(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    callback.ok(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
