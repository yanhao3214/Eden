package com.gogo.haobutler.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.gogo.haobutler.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function: 大图浏览适配器
 */
public class PhotoViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mList;

    public PhotoViewPagerAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView photo = new PhotoView(mContext);
        RequestOptions glideOptions = new RequestOptions().error(R.drawable.ic_error_image);
        Glide.with(mContext).load(mList.get(position)).apply(glideOptions).into(photo);
        container.addView(photo, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return photo;
    }
}
