package com.gogo.haobutler.utils.net;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.http.GET;

/**
 * @author: 闫昊
 * @date: 2018/5/29 0029
 * @function: 自定义简易的网络框架
 */
public class HttpUtil {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int TIME_OUT = 1;
    private static final BlockingQueue<Runnable> POOL_QUEUE =
            new LinkedBlockingQueue<>(128);

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        //原子操作的Integer类，实现线程安全的加减操作，适用多线程
        private final AtomicInteger mCount = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "HttpThread-" + mCount.incrementAndGet());
        }
    };

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            TIME_OUT,
            TimeUnit.SECONDS,
            POOL_QUEUE,
            THREAD_FACTORY);

    /**
     * get请求
     * @param url
     * @param callback
     */
    public static void getRequest(final String url, final HttpCallback callback) {
        EXECUTOR.execute(() -> {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(8000);
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                if (callback != null) {
                    callback.onSuccess(response.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.onFail(e);
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    public static void postRequest(String url, HttpCallback callback) {
        EXECUTOR.execute(() -> {
        });
    }
}