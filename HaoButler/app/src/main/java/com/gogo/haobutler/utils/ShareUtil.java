package com.gogo.haobutler.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: 闫昊
 * @date: 2018/4/15
 * @function:
 */

public class ShareUtil {
    private static final String FILE_NAME = "user";

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String def) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, def);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int def) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, def);
    }

    public static void putBoolean(Context context, String key, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).apply();
    }

    public static Boolean getBoolean(Context context, String key, Boolean def) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, def);
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).apply();
    }

    public static Long getLong(Context context, String key, long def) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, def);
    }

    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).apply();
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    /**
     * 1.从ImageView中获取Bitmap；
     * 2.将Bitmap压缩为字符数组输出流（compress）；
     * 3.将字符数组输出流转化为字符串（Base64）；
     * 4.通过SharedPreferences存储图片对应的字符串。
     *
     * @param context
     * @param imageView
     */
    public static void putImage(Context context, String key, ImageView imageView) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream boStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, boStream);
        byte[] bytes = boStream.toByteArray();
        String imgStr = Base64.encodeToString(bytes, Base64.DEFAULT);
        putString(context, key, imgStr);
    }

    /**
     * 通过key从SharedPReferences中获取Bitmap
     *
     * @param context
     * @param key
     * @return
     */
    public static Bitmap getImage(Context context, String key, Bitmap defBitmap) {
        String imgStr = getString(context, key, "");
        if (!TextUtils.isEmpty(imgStr)) {
            byte[] bytes = Base64.decode(imgStr, Base64.DEFAULT);
            ByteArrayInputStream biStream = new ByteArrayInputStream(bytes);
            return BitmapFactory.decodeStream(biStream);
        }
        return defBitmap;
    }

    private HashMap<String, Integer> map = new HashMap<>();
    private void test() {
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        ArrayList<Map.Entry<String, Integer>> arrayList = new ArrayList<>(entrySet);
    }
}
