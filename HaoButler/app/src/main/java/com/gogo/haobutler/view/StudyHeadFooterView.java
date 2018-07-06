package com.gogo.haobutler.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.WebViewActivity;
import com.gogo.haobutler.model.study.StudyHeadFooterItem;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function: 学习乐园列表头的尾部View
 */
public class StudyHeadFooterView extends RelativeLayout implements View.OnClickListener {

    /**
     * UI
     */
    private Context mContext;
    private LayoutInflater mInflater;
    private View mRootView;
    private TextView tvTitle;
    private TextView tvInfo;
    private TextView tvFrom;
    private ImageView ivUpper;
    private ImageView ivLower;

    /**
     * Data
     */
    private StudyHeadFooterItem mData;

    public StudyHeadFooterView(Context context, StudyHeadFooterItem studyHeadFooterItem) {
        this(context, null, studyHeadFooterItem);
    }

    public StudyHeadFooterView(Context context, AttributeSet attrs,
                               StudyHeadFooterItem studyHeadFooterItem) {
        super(context, attrs);
        mContext = context;
        mData = studyHeadFooterItem;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    private void initView() {
        mRootView = mInflater.inflate(R.layout.view_study_header_footer_layout, this);
        tvTitle = mRootView.findViewById(R.id.tv_title);
        tvInfo = mRootView.findViewById(R.id.tv_info);
        tvFrom = mRootView.findViewById(R.id.tv_from);
        ivUpper = mRootView.findViewById(R.id.iv_upper);
        ivLower = getRootView().findViewById(R.id.iv_lower);

        tvTitle.setText(mData.title);
        tvInfo.setText(mData.info);
        tvFrom.setText(mData.from);
        RequestOptions glideOptions = new RequestOptions()
                .placeholder(R.drawable.ic_error_image)
                .error(R.drawable.ic_error_image);
        Glide.with(mContext).load(mData.imageOne).apply(glideOptions).into(ivUpper);
        Glide.with(mContext).load(mData.imageTwo).apply(glideOptions).into(ivLower);

        mRootView.setOnClickListener(this);
    }

    /**
     * 点击跳转到详情页面
     */
    @Override
    public void onClick(View v) {
        Intent toWeb = new Intent(mContext, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", mData.title);
        bundle.putString("url", mData.destationUrl);
        toWeb.putExtras(bundle);
        mContext.startActivity(toWeb);
    }
}
