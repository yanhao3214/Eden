package com.gogo.haobutler.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.gogo.haobutler.adapter.MoocAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author: 闫昊
 * @date: 2018/5/18
 * @function:
 */
public class ImageLoader {
    private static ImageLoader mImageLoader;
    private ImageView mImageView;
    private String mURL;
    private LruCache<String, Bitmap> mLruCache;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mImageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    public static ImageLoader getInstance() {
        if (mImageLoader == null) {
            synchronized (ImageLoader.class) {
                if (mImageLoader == null) {
                    mImageLoader = new ImageLoader();
                }
            }
        }
        return mImageLoader;
    }

    private ImageLoader() {
        int maxMemorySize = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemorySize / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (mLruCache.get(url) == null) {
            mLruCache.put(url, bitmap);
            Log.d("yh", "addBitmapToCache");
        }
    }

    private Bitmap getBitmapFromCache(String url) {
        return mLruCache.get(url);
    }

    private void removeBitmapFromCache(String url) {
        mLruCache.remove(url);
    }

    /**
     * 用异步线程实现：ImageView异步加载Bitmap
     */
    public void showImageByThread(final String url, ImageView imageView) {
        mImageView = imageView;
        mURL = url;
        new Thread(() -> {
            Bitmap bitmap = getBitmapFromURL(mURL);
            Message message = Message.obtain();
            message.obj = bitmap;
            mHandler.sendMessage(message);
        }).start();

        // TODO: 2018/5/19 0019 使用mImageLoader时，ListView中存在图片加载错乱现象
    }

    /**
     * 先判断内存中是否有对应的Bitmap，没有的话再从网络加载
     */
    public void showImageByAsyncTask(String url, ImageView imageView) {
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            new ImageAsyncTask(imageView).execute(url);
        }
    }


    private Bitmap getBitmapFromURL(String url) {
        Bitmap bitmap = null;
        try {
            InputStream is = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(is);
            //将不在缓存的图片加入缓存
            if (bitmap != null) {
                addBitmapToCache(url, bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("yh", "url == " + url);
        return bitmap;
    }

    /**
     * 用AsyncTask实现：ImageView异步加载Bitmap
     */
    class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public ImageAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return getBitmapFromURL(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
