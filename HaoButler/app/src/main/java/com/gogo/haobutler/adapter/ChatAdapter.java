package com.gogo.haobutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.chat.ChatMsg;

import java.util.List;

import static com.gogo.haobutler.model.chat.ChatMsg.CHAT_ITEM_LEFT;

/**
 * @author: 闫昊
 * @date: 2018/6/14 0014
 * @function:
 */
public class ChatAdapter extends BaseAdapter {
    private List<ChatMsg> mList;
    private Context mContext;

    public ChatAdapter(Context mContext, List<ChatMsg> mList) {
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

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftHolder leftHolder = null;
        RightHolder rightHolder = null;
        ChatMsg chatMsg = mList.get(position);
        int type = getItemViewType(position);
        if (convertView == null) {
            if (type == CHAT_ITEM_LEFT) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_chat_left_layout, null);
                leftHolder = new LeftHolder(convertView);
                convertView.setTag(leftHolder);
            } else {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_chat_right_layout, null);
                rightHolder = new RightHolder(convertView);
                convertView.setTag(rightHolder);
            }
        } else {
            if (type == CHAT_ITEM_LEFT) {
                leftHolder = (LeftHolder) convertView.getTag();
            } else {
                rightHolder = (RightHolder) convertView.getTag();
            }
        }
        if (type == CHAT_ITEM_LEFT) {
            leftHolder.tvMsg.setText(chatMsg.getMsg());
        } else {
            rightHolder.tvMsg.setText(chatMsg.getMsg());
        }
        return convertView;
    }

    private class LeftHolder {

        LeftHolder(View view) {
            tvMsg = view.findViewById(R.id.tv_hao);
        }

        TextView tvMsg;
    }

    private class RightHolder {

        RightHolder(View view) {
            tvMsg = view.findViewById(R.id.tv_user);
        }

        TextView tvMsg;
    }
}
