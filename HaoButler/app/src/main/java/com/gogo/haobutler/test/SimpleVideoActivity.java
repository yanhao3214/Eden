package com.gogo.haobutler.test;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gogo.haobutler.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * @author: 闫昊
 * @date: 2018/6/2 0002
 * @function: GSYVideoPlayer简单视频播放测试
 */
public class SimpleVideoActivity extends AppCompatActivity {
    private StandardGSYVideoPlayer mStandardGSYVideoPlayer;
    private OrientationUtils mOrientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_video_layout);
        init();
    }

    private void init() {
        mStandardGSYVideoPlayer = findViewById(R.id.video_player_standard);
        String url = "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=112045&resourceType=video&editionType=high&source=aliyun";
        mStandardGSYVideoPlayer.setUp(url, true, "第一个视频");

        //增加封面
        ImageView ivCover = new ImageView(this);
        ivCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivCover.setImageResource(R.drawable.xxx2);
        mStandardGSYVideoPlayer.setThumbImageView(ivCover);
        //增加title
        mStandardGSYVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //返回键可见
        mStandardGSYVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //是否可以滑动调整
        mStandardGSYVideoPlayer.setIsTouchWiget(true);

        mOrientationUtils = new OrientationUtils(this, mStandardGSYVideoPlayer);
        mStandardGSYVideoPlayer.getFullscreenButton().setOnClickListener(v ->
                mOrientationUtils.resolveByClick());

        //设置返回键功能
        mStandardGSYVideoPlayer.getBackButton().setOnClickListener(v -> onBackPressed());

        //开始按钮播放视频
        mStandardGSYVideoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStandardGSYVideoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStandardGSYVideoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //先返回正常状态
        if (mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            mStandardGSYVideoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        mStandardGSYVideoPlayer.setVideoAllCallBack(null);
    }
}
