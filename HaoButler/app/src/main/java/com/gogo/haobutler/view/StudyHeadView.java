package com.gogo.haobutler.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.WebViewActivity;
import com.gogo.haobutler.model.study.StudyHead;
import com.gogo.haobutler.model.study.StudyHeadFooterItem;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.Arrays;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function: 学习乐园的列表头View
 */
public class StudyHeadView extends RelativeLayout implements OnBannerListener {

    /**
     * UI
     */
    private Context mContext;
    private LayoutInflater mInflater;
    private View mRootView;
    public Banner mBanner;
    private TextView tvHot;
    private ImageView[] imageViews = new ImageView[4];
    private LinearLayout mLayoutFooter;

    /**
     * Data
     */
    private StudyHead mData;
    private RequestOptions mGlideOptions;

    public StudyHeadView(Context context, StudyHead data) {
        this(context, null, data);
    }

    public StudyHeadView(Context context, AttributeSet attrs, StudyHead data) {
        super(context, attrs);
        mContext = context;
        mData = data;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGlideOptions = new RequestOptions().placeholder(R.drawable.ic_error_image).error(R.drawable.ic_error_image);
        initView();
    }

    private void initView() {
        mRootView = mInflater.inflate(R.layout.view_study_header_layout, this);
        mBanner = mRootView.findViewById(R.id.banner_header);
        tvHot = mRootView.findViewById(R.id.tv_hot);
        imageViews[0] = mRootView.findViewById(R.id.iv_middle_one);
        imageViews[1] = mRootView.findViewById(R.id.iv_middle_two);
        imageViews[2] = mRootView.findViewById(R.id.iv_middle_three);
        imageViews[3] = mRootView.findViewById(R.id.iv_middle_four);
        mLayoutFooter = mRootView.findViewById(R.id.layout_content);

        initBanner();
        initMiddle();
        initFooter();
    }

    /**
     * 为banner设置内容
     */
    private void initBanner() {
        String[] titles = getResources().getStringArray(R.array.banner_title);
        List<String> bannerTitles = Arrays.asList(titles);
        mBanner.setBannerTitles(bannerTitles)
                .setImages(mData.ads)
                .setImageLoader(new GlideLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setBannerAnimation(Transformer.Accordion)
                .setDelayTime(3000)
                .setOnBannerListener(this)
                .start();
    }

    /**
     * 为middle设置内容
     */
    private void initMiddle() {
        int length = imageViews.length;
        for (int i = 0; i < length; i++) {
            Glide.with(mContext).load(mData.middle.get(i)).apply(mGlideOptions).into(imageViews[i]);
        }
    }

    /**
     * 为footer设置内容
     */
    private void initFooter() {
        List<StudyHeadFooterItem> footerList = mData.footer;
        for (StudyHeadFooterItem item : footerList) {
            mLayoutFooter.addView(new StudyHeadFooterView(mContext, item));
        }
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    @Override
    public void OnBannerClick(int position) {
        toDetailWeb();
    }

    private void toDetailWeb() {
        Intent toWeb = new Intent(mContext, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "APP作者的GitHub");
        bundle.putString("url", "https://github.com/yanhao3214");
        toWeb.putExtras(bundle);
        mContext.startActivity(toWeb);
    }

    private class GlideLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).apply(mGlideOptions).into(imageView);
        }
    }
}
