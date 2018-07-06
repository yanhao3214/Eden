package com.gogo.haobutler.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.PhotoViewActivity;
import com.gogo.haobutler.adapter.BasketballRecyclerViewAdapter;
import com.gogo.haobutler.callback.EdenNetCallback;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.gogo.haobutler.model.image.Gank;
import com.gogo.haobutler.utils.HttpCenter;
import com.gogo.haobutler.utils.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/4/12
 * @function:
 */

public class BasketballFragment extends BaseFragment implements View.OnClickListener {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mUrls = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_basketball_layout, container, false);
        initData();
        initView();
        return mRootView;
    }

    @Override
    public void initData() {
        HttpCenter.getGankPic(50, 1, new EdenNetCallback() {
            @Override
            public void ok(String json) {
                List<Gank> ganks = JsonParser.parseGankPic(json);
            }

            @Override
            public void error(String eMsg) {
                Log.e("yh", "获取gank福利图片失败");
            }
        });
        for (int i = 0; i < 10; i++) {
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fswhaqvnobj30sg14hka0.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fsvb1xduvaj30u013175p.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fsq9iq8ttrj30k80q9wi4.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fsp4iok6o4j30j60optbl.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fsoe3k2gkkj30g50niwla.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fsmis4zbe7j30sg16fq9o.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1frslruxdr1j30j60ok79c.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1k9cb5j30sg0y7q61.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1ykabxj30k00pracv.jpg");
            mUrls.add("http://ww1.sinaimg.cn/large/0065oQSqly1fsfq2pwt72j30qo0yg78u.jpg");
        }
    }

    @Override
    public void initView() {
        mRecyclerView = mRootView.findViewById(R.id.recycler_view);
        BasketballRecyclerViewAdapter adapter = new BasketballRecyclerViewAdapter(context, mUrls);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        /**
         * 为图片添加点击事件，跳转至大图浏览
         */
        adapter.setOnPhotoClickListener((position) -> {
            Intent intentPhoto = new Intent(context, PhotoViewActivity.class);
            intentPhoto.putExtra(PhotoViewActivity.PHOTO_VIEW_POSITION, position);
            intentPhoto.putStringArrayListExtra(PhotoViewActivity.PHOTO_VIEW_URLS, mUrls);
            startActivity(intentPhoto);
        });
    }

    @Override
    public void onClick(View v) {
    }
}
