package com.gogo.haobutler.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gogo.haobutler.R;
import com.gogo.haobutler.callback.OnPhotoClickListener;

import java.util.List;
import java.util.Random;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function:
 */
public class BasketballRecyclerViewAdapter extends RecyclerView.Adapter<BasketballRecyclerViewAdapter.BasketballHolder> {
    private Context mContext;
    private List<String> mList;
    private OnPhotoClickListener mOnPhotoClickListener;

    public BasketballRecyclerViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setOnPhotoClickListener(OnPhotoClickListener mOnPhotoClickListener) {
        this.mOnPhotoClickListener = mOnPhotoClickListener;
    }

    @NonNull
    @Override
    public BasketballHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_basketball_layout, parent, false);
        return new BasketballHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketballHolder holder, int position) {
        Glide.with(mContext).load(mList.get(position)).into(holder.mImageView);
        if (mOnPhotoClickListener != null) {
            holder.mImageView.setOnClickListener((v -> mOnPhotoClickListener.onPhotoClick(position)));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class BasketballHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        BasketballHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
        }
    }
}
