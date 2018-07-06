package com.gogo.haobutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.mooc.MoocData;
import com.gogo.haobutler.utils.ImageLoader;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/5/18
 * @function:
 */
public class MoocAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private List<MoocData> mList;
    private Context context;
    private ImageLoader mImageLoader;
    private int start;
    private int end;
    public static String[] urls;

    public MoocAdapter(Context context, List<MoocData> moocBeanList) {
        super();
        this.context = context;
        this.mList = moocBeanList;
        mImageLoader = ImageLoader.getInstance();
        urls = new String[end - start];
        for (int i = 0; i < end - start; i++) {
            urls[i] = moocBeanList.get(i).getIcon();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MoocData moocBean = mList.get(i);
        String urlIcon = moocBean.getIcon();
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_mooc_layout, null);
            holder = new ViewHolder();
            holder.ivIcon = view.findViewById(R.id.iv_icon);
            holder.tvTitle = view.findViewById(R.id.tv_title);
            holder.tvContent = view.findViewById(R.id.tv_content);
            view.setTag(holder);
            holder.ivIcon.setTag(urlIcon);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (holder.ivIcon.getTag() == urlIcon) {
            mImageLoader.showImageByAsyncTask(urlIcon, holder.ivIcon);
        }
        holder.tvTitle.setText(moocBean.getTitle());
        holder.tvContent.setText(moocBean.getContent());
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == SCROLL_STATE_IDLE) {
            // TODO: 2018/5/19 0019 加载可见项
        } else {
            // TODO: 2018/5/19 0019  停止任务
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        start = i;
        end = i + i1;
    }

    private class ViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvContent;
    }
}
