package com.gogo.haobutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.PhotoViewActivity;
import com.gogo.haobutler.adapter.StudyAdapter;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.gogo.haobutler.model.study.Study;
import com.gogo.haobutler.model.study.StudyRecommend;
import com.gogo.haobutler.utils.JsonParser;
import com.gogo.haobutler.view.StudyHeadView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.gogo.haobutler.activity.PhotoViewActivity.PHOTO_VIEW_URLS;

/**
 * @author: 闫昊
 * @date: 2018/7/3 0003
 * @function: 学习园地
 */
public class StudyFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    /**
     * UI
     */
    private View rootView;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private StudyHeadView mStudyHeadView;

    /**
     * Data
     */
    private String mJson;
    private Study mStudy;
    private List<StudyRecommend> mRecommendList;
    private StudyAdapter mStudyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_study_layout, container, false);
        initView();
        initData();
        return rootView;
    }

    @Override
    public void initView() {
        mListView = rootView.findViewById(R.id.list_view_study);
        mProgressBar = rootView.findViewById(R.id.progress_bar_study);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        mJson = getJson();
        mStudy = JsonParser.parseStudyData(mJson);
        if (mStudy != null && mStudy.data != null) {
            showSuccess();
        } else {
            showError();
        }
    }

    private void showSuccess() {
        mRecommendList = mStudy.data.list;
        if (mRecommendList != null && mRecommendList.size() > 0) {
            mProgressBar.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mStudyAdapter = new StudyAdapter(context, mRecommendList);
            mStudyHeadView = new StudyHeadView(context, mStudy.data.head);
            mListView.addHeaderView(mStudyHeadView);
            mListView.setAdapter(mStudyAdapter);
        } else {
            showError();
        }
    }

    private void showError() {
    }

    private String getJson() {
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open("study.json"));
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("yh", "getJson: json == " + sb.toString());
        return sb.toString();
    }

    /**
     * 优化轮播器
     */
    @Override
    public void onStart() {
        super.onStart();
        if (mStudyHeadView != null) {
            mStudyHeadView.mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mStudyHeadView != null) {
            mStudyHeadView.mBanner.stopAutoPlay();
        }
    }

    /**
     * 单图或者多图item，点击跳转大图浏览Activity
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StudyRecommend item = (StudyRecommend) mStudyAdapter.getItem(position - mListView.getHeaderViewsCount());
        Log.d("yh", "onItemClick: " + item.toString());
        if (item.type != 0) {
            ArrayList<String> urlList = new ArrayList<>(item.url);
            Log.d("yh", "onItemClick: 跳转啦---urlList.size() == " + urlList.size());
            Intent intent = new Intent(context, PhotoViewActivity.class);
            intent.putStringArrayListExtra(PHOTO_VIEW_URLS, urlList);
            startActivity(intent);
        }
    }
}
