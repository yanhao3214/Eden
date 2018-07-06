package com.gogo.haobutler.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.base.AbstractImmerseActivity;
import com.gogo.haobutler.activity.base.BaseActivity;
import com.gogo.haobutler.adapter.PhotoViewPagerAdapter;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/6/17 0017
 * @function: 大图浏览
 */
public class PhotoViewActivity extends AbstractImmerseActivity {

    public static final String PHOTO_VIEW_URLS = "photo_view_urls";
    public static final String PHOTO_VIEW_POSITION = "photo_view_position";
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_layout);
        initView();
        Log.d("yh", "PhotoViewActivity: onCreate");
    }

    private void initView() {
        List<String> urls = getIntent().getStringArrayListExtra(PHOTO_VIEW_URLS);
        int position = getIntent().getIntExtra(PHOTO_VIEW_POSITION, 0);
        mViewPager = findViewById(R.id.view_pager_photo_view);
        mViewPager.setAdapter(new PhotoViewPagerAdapter(this, urls));
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onClick(View v) {
    }
}
