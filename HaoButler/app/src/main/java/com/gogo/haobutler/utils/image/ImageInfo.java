package com.gogo.haobutler.utils.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @author: 闫昊
 * @date: 2018/6/3 0003
 * @function: 图片加载信息，用以匹配uri和对应的ImageView
 */
public class ImageInfo {
    private String uri;
    private Bitmap bitmap;
    private ImageView imageView;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "uri='" + uri + '\'' +
                ", bitmap=" + bitmap +
                ", imageView=" + imageView +
                '}';
    }
}
