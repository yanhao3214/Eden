package com.gogo.haobutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogo.haobutler.R;
import com.gogo.haobutler.model.study.StudyRecommend;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/7/3 0003
 * @function:
 */
public class StudyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<StudyRecommend> mList;
    private RequestOptions mGlideOptions;

    public StudyPagerAdapter(Context mContext, List<StudyRecommend> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mGlideOptions = new RequestOptions().placeholder(R.drawable.xxx2).error(R.drawable.xxx1);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        StudyRecommend item = mList.get(position % mList.size());
        @SuppressLint("InflateParams")
        View pagerView = mInflater.inflate(R.layout.item_study_pager_layout, null);
        TextView tvTitle = pagerView.findViewById(R.id.tv_title);
        TextView tvPrice = pagerView.findViewById(R.id.tv_price);
        TextView tvInfo = pagerView.findViewById(R.id.tv_info);
        TextView tvText = pagerView.findViewById(R.id.tv_text);
        ImageView[] imageViews = new ImageView[3];
        imageViews[0] = pagerView.findViewById(R.id.iv_one);
        imageViews[1] = pagerView.findViewById(R.id.iv_two);
        imageViews[2] = pagerView.findViewById(R.id.iv_three);

        tvTitle.setText(item.title);
        tvPrice.setText(item.price);
        tvInfo.setText(item.info);
        tvText.setText(item.text);
        for (int i = 0; i < imageViews.length; i++) {
            Glide.with(mContext).load(item.url.get(i)).apply(mGlideOptions).into(imageViews[i]);
        }
        container.addView(pagerView, 0);
        return pagerView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
