package com.gogo.haobutler.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gogo.haobutler.model.phone.PhoneData;
import com.gogo.haobutler.activity.base.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: 闫昊
 * @date: 2018/6/2 0002
 * @function:
 */
public class OKTestActivity extends BaseActivity {


    private void sendRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .get()
                .url("ssdadasdas")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        List<String> sList = Collections.synchronizedList(new ArrayList<String>());
        List<Integer> iList = new LinkedList<>();
        List<PhoneData> vector = Collections.synchronizedList(new Vector<PhoneData>());
    }
}
