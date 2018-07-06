package com.gogo.haobutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gogo.haobutler.R;
import com.gogo.haobutler.model.wechat.WeChatData;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/6/15 0015
 * @function: 微信精选适配器
 */
public class WeChatAdapter extends BaseAdapter {
    private List<WeChatData> mDataList;
    private Context mContext;

    public WeChatAdapter(Context context, List<WeChatData> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeChatData data = mDataList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_wechat_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(data.getTitle());
        holder.tvResource.setText(data.getSource());
        //用Glide 4加载图片
        RequestOptions glideOptions = new RequestOptions().placeholder(R.drawable.xxx1).error(R.drawable.ic_error_image);
        Glide.with(mContext)
                .load(data.getFirstImg())
                .apply(glideOptions)
                .into(holder.imageView);
        return convertView;
    }

    class ViewHolder {

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.iv_picture);
            tvTitle = view.findViewById(R.id.tv_title);
            tvResource = view.findViewById(R.id.tv_resource);
        }

        ImageView imageView;
        TextView tvTitle;
        TextView tvResource;
    }
}
