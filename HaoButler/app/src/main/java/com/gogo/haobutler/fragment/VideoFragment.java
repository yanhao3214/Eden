package com.gogo.haobutler.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.gogo.haobutler.R;
import com.gogo.haobutler.adapter.VideoAdapter;
import com.gogo.haobutler.model.video.VideoData;
import com.gogo.haobutler.model.video.VideoItem;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.gogo.haobutler.utils.Consts;
import com.gogo.haobutler.utils.JsonParser;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: 闫昊
 * @date: 2018/7/1 0001
 * @function: 待解决：
 * 1.fragment切换时停止播放 yes
 * 2.滑动冲突SwipeRefreshLayout和ListView、SampleCoverVideo和ViewPager
 * 3.下拉刷新，上拉加载 yes
 * 做了一些非空的判断（事实上，大部分是测试出现bug后增加的），是为了提高代码的健壮性
 */
public class VideoFragment extends BaseFragment implements AbsListView.OnScrollListener {

    /**
     * UI
     */
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private VideoAdapter mVideoAdapter;

    /**
     * 加载更多FooterView
     */
    private View mLoadingFooter;

    /**
     * 没有更多FooterView
     */
    private View mNoMoreFooter;

    /**
     * Data
     */
    private String url;

    /**
     * 当前视频数据，随上拉加载更新
     */
    private VideoData mCurrentData;
    private List<VideoItem> mVideoList = new ArrayList<>();
    private VideoHandler mHandler = new VideoHandler(this);
    private int mLastVisibleItem;

    /**
     * 是否处于上拉加载状态
     */
    private boolean isLoading;
    private static final int HAS_MORE_VIDEO = 1;
    private static final int NO_MORE_VIDEO = -1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_video_layout, container, false);
        initView();
        initData();
        return rootView;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        mListView = rootView.findViewById(R.id.list_video);
        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.footer_load_more_layout, null);
        mLoadingFooter.setVisibility(View.GONE);
        mNoMoreFooter = LayoutInflater.from(context).inflate(R.layout.footer_no_more_layout, null);
        mNoMoreFooter.setVisibility(View.GONE);
        mListView.addFooterView(mLoadingFooter, null, false);
        mListView.addFooterView(mNoMoreFooter, null, false);
        mVideoAdapter = new VideoAdapter(getContext(), mVideoList);
        mListView.setAdapter(mVideoAdapter);
        mListView.setOnScrollListener(this);
        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mVideoList.clear();
            getVideoData(url);
            mListView.setSelection(0);
        });
    }

    @Override
    public void initData() {
        url = Consts.EYEPETIZER_VIDEO_URL;
        getVideoData(url);
    }

    /**
     * 根据url获取视频数据
     */
    private void getVideoData(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            /**
             * 子线程设置Looper，弹Toast
             */
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
//                Toast.makeText(getActivity(), R.string.weather_data_fialed, Toast.LENGTH_SHORT).show();
                Log.e("yh", getString(R.string.weather_data_failed));
                Looper.loop();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Log.d("yh", "3 ---> onResponse: json == " + json);
                final VideoData videoData = JsonParser.parseVideoData(json);
                Message message = Message.obtain();
                if (videoData != null) {
                    message.what = HAS_MORE_VIDEO;
                    message.obj = videoData;
                } else {
                    message.what = NO_MORE_VIDEO;
                }
                mHandler.sendMessage(message);
            }
        });
    }

    /**
     * 上拉加载，条件：
     * 1.显示最后一个item；
     * 2.滑动停止；
     * 3.不处于上拉加载状态中，即isLoading == false
     */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int totalItem = mVideoList.size() + mListView.getHeaderViewsCount() + mListView.getFooterViewsCount();
        if (mLastVisibleItem >= totalItem && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading) {
                isLoading = true;
                mLoadingFooter.setVisibility(View.VISIBLE);
                if (mCurrentData != null) {
                    Log.d("yh", "1 ---> onScrollStateChanged: mCurrentData == " + mCurrentData.toString());
                    String url = mCurrentData.getNextPageUrl();
                    if (!TextUtils.isEmpty(url) && !"null".equals(url)) {
                        Log.d("yh", "2 ---> onScrollStateChanged: url == " + url);
                        getVideoData(url);
                    } else {
                        mLoadingFooter.setVisibility(View.GONE);
                        mNoMoreFooter.setVisibility(View.VISIBLE);
                        isLoading = false;
                    }
                } else {
                    //没有更多数据时，再次上拉关闭mNoMoreFooter
                    mNoMoreFooter.setVisibility(View.GONE);
                    isLoading = false;
                }
            }
        }
    }

    @SuppressWarnings("AlibabaAvoidComplexCondition")
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastVisibleItem = firstVisibleItem + visibleItemCount;
        View firstVisibleView = view.getChildAt(firstVisibleItem);

        /**
         * 滑动时播放设置
         */
        GSYVideoManager manager = GSYVideoManager.instance();
        if (manager.getPlayPosition() >= 0) {
            //当前播放位置
            int position = manager.getPlayPosition();
            if (VideoAdapter.TAG.equals(manager.getPlayTag()) && (position < firstVisibleItem || position > mLastVisibleItem)) {
                //全屏则不作处理
                if (GSYVideoManager.isFullState(getActivity())) {
                    return;
                }
                //滑出屏幕则停止播放
                GSYVideoManager.releaseAllVideos();
                mVideoAdapter.notifyDataSetChanged();
            }
        }

        /**
         * 解决ListView和SwipeRefreshLayout的滑动冲突
         * 当ListView上滑到顶时，SwipeRefreshLayout可用，否则不可用。
         */
        if (firstVisibleItem == 0 && (firstVisibleView == null || firstVisibleView.getTop() == 0)) {
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    /**
     * 自定义Handler，防止内存泄漏：
     * 1.静态内部类
     * 2.持有VideoFragment的弱引用
     */
    private static class VideoHandler extends Handler {
        WeakReference<Fragment> reference;

        VideoHandler(Fragment fragment) {
            reference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VideoFragment fragment = (VideoFragment) reference.get();
            switch (msg.what) {
                case HAS_MORE_VIDEO:
                    VideoData videoData = (VideoData) msg.obj;

                    /**
                     * 加载结束，更新界面,同时更新视频源数据mCurrentData
                     */
                    fragment.mCurrentData = videoData;
                    List<VideoItem> newList = videoData.getItemList();
                    if (newList != null && newList.size() > 0) {
                        fragment.mVideoList.addAll(videoData.getItemList());
                        fragment.mVideoAdapter.notifyDataSetChanged();
                        Log.d("yh", "4 ---> VideoFragment: videoData.getItemList().size() == " + videoData.getItemList().size());
                        Log.d("yh", "5 ---> VideoFragment: mVideoList.size() == " + fragment.mVideoList.size());
                        Log.d("yh", "6 ---> VideoFragment: videoData == " + videoData.toString());
                        /**
                         * 更新结束，设置状态
                         */
                        fragment.mSwipeRefreshLayout.setRefreshing(false);
                        fragment.isLoading = false;
                        fragment.mLoadingFooter.setVisibility(View.GONE);
                    }
                    break;
                case NO_MORE_VIDEO:
                    fragment.mCurrentData = null;
                    fragment.mNoMoreFooter.setVisibility(View.VISIBLE);
                    /**
                     * 更新结束，设置状态
                     */
                    fragment.mSwipeRefreshLayout.setRefreshing(false);
                    fragment.isLoading = false;
                    fragment.mLoadingFooter.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }


        }
    }

    /**
     * 将GSYVideoPlayer的生命周期与当前Fragment的可见性绑定
     *
     * @param isVisibleToUser 当前Fragment是否对用户可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            GSYVideoManager.onResume();
        } else {
            GSYVideoManager.onPause();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }
}
