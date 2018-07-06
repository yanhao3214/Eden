package com.gogo.haobutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gogo.haobutler.R;
import com.gogo.haobutler.model.video.VideoItem;
import com.gogo.haobutler.test.SampleCoverVideo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: 闫昊
 * @date: 2018/7/1 0001
 * @function:
 */
public class VideoAdapter extends BaseAdapter {
    public static final String TAG = "videoFragmentListTag";
    private Context mContext;
    private List<VideoItem> mList;

    public VideoAdapter(Context mContext, List<VideoItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("SingleStatementInBlock")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        VideoItem videoItem = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        String duration = com.yh.sdk.utils.TimeUtils.sec2Time(videoItem.getDuration());
        holder.tvDuration.setText(duration);
        holder.tvTitle.setText(videoItem.getTitle());
        holder.tvAuthorName.setText(videoItem.getAuthorName());
        holder.tvCategory.setText("#" + videoItem.getCategory());
        Glide.with(mContext).load(videoItem.getAuthorIcon()).into(holder.civAuthorIcon);
        loadGSYPlayer(position, holder.sampleCoverVideo);
        return convertView;
    }

    /**
     * 加载列表视频
     *
     * @param position         位置
     * @param sampleCoverVideo Holder中的列表视频播放器
     */
    private void loadGSYPlayer(int position, SampleCoverVideo sampleCoverVideo) {
        VideoItem videoItem = mList.get(position);
        String coverUrl = videoItem.getCoverUrl();
        String playUrl = videoItem.getPlayUrl();
        String title = videoItem.getTitle();
        //加载视频封面图片
        sampleCoverVideo.loadCoverImage(coverUrl, R.drawable.xxx1);
        sampleCoverVideo.setUpLazy(playUrl, false, null, null, title);
        sampleCoverVideo.getFullscreenButton().setOnClickListener((v) ->
                sampleCoverVideo.startWindowFullscreen(mContext, false, true));
        //防止错位设置
        sampleCoverVideo.setPlayTag(TAG);
        sampleCoverVideo.setPlayPosition(position);
        //根据视频尺寸自动选择播放模式
        sampleCoverVideo.setAutoFullWithSize(true);
        //音视频焦点冲突时释放
        sampleCoverVideo.setReleaseWhenLossAudio(true);
        //全屏动画
        sampleCoverVideo.setShowFullAnimation(true);
        //小屏播放不支持滑动
        sampleCoverVideo.setIsTouchWiget(true);
        //全屏是否需要锁定屏幕键
        sampleCoverVideo.setNeedLockFull(true);
    }

    private class Holder {
        SampleCoverVideo sampleCoverVideo;
        TextView tvDuration;
        CircleImageView civAuthorIcon;
        TextView tvTitle;
        TextView tvAuthorName;
        TextView tvCategory;

        Holder(View view) {
            sampleCoverVideo = view.findViewById(R.id.video_item_player);
            tvDuration = view.findViewById(R.id.tv_duration);
            civAuthorIcon = view.findViewById(R.id.civ_author_icon);
            tvTitle = view.findViewById(R.id.tv_video_title);
            tvAuthorName = view.findViewById(R.id.tv_author_name);
            tvCategory = view.findViewById(R.id.tv_category);
        }
    }
}
