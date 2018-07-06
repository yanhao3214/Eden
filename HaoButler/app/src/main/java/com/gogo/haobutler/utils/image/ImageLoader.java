package com.gogo.haobutler.utils.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.internal.DiskLruCache;

/**
 * @author: 闫昊
 * @date: 2018/6/3 0003
 * @function:
 */
public class ImageLoader {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long THREAD_TIME_OUT = 10L;
    private static final BlockingQueue<Runnable> POOL_QUEUE =
            new LinkedBlockingQueue<>(128);

    private static final int DISK_CHACHE_SIZE = 1024 * 1024 * 50;
    private boolean mDiskLruCreated = false;

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private AtomicInteger mCount = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageLoaderThread-" + mCount.incrementAndGet());
        }
    };
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            THREAD_TIME_OUT,
            TimeUnit.SECONDS,
            POOL_QUEUE,
            THREAD_FACTORY);
    private Handler nHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            ImageInfo info = (ImageInfo) msg.obj;
            ImageView imageView = info.getImageView();
            String uri = (String) imageView.getTag();
            if (uri.equals(info.getUri())) {
                imageView.setImageBitmap(info.getBitmap());
            } else {
                Log.w("ImageLoader", "uri not matched, setImageBitmap() is ignored");
            }
        }
    };

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private ImageFitter mImageFitter;
    private LruCache<String, Bitmap> mLruCache;
    private DiskLruCache mDiskLruCache;

    private ImageLoader() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (getAvailableSpace(diskCacheDir) > DISK_CHACHE_SIZE) {
//            mDiskLruCache = DiskLruCache.create(diskCacheDir, 1, 1, DISK_CHACHE_SIZE);
        }
    }

    public static ImageLoader getInstance(Context context) {
        mContext = context.getApplicationContext();
        return Holder.INSTANCE;
    }

    private static class Holder {
        @SuppressLint("StaticFieldLeak")
        private static final ImageLoader INSTANCE = new ImageLoader();
    }



    @SuppressWarnings("ConstantConditions")
    private File getDiskCacheDir(Context context, String name) {
        String filePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = context.getExternalCacheDir().getPath();
        } else {
            filePath = context.getCacheDir().getPath();
        }
        //File.separator:路径分隔符，代替绝对路径，造成跨平台异常
        return new File(filePath + File.separator + name);
    }

    private int getAvailableSpace(File diskCacheDir) {
        return 0;
    }
}
