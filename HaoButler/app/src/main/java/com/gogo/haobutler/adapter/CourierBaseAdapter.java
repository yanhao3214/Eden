package com.gogo.haobutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.courier.CourierInfo;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/5/31 0031
 * @function:
 */
public class CourierBaseAdapter extends BaseAdapter {
    private List<CourierInfo> mList;
    private Context mContext;

    public CourierBaseAdapter(Context context, List<CourierInfo> list) {
        mContext = context;
        mList = list;
    }

    public void setList(List<CourierInfo> mList) {
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourierInfo info = mList.get(position);
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_courier_layout, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder)convertView.getTag();
        }
        holder.tvTime.setText(info.getDatetime());
        holder.tvRemark.setText(info.getRemark());
        return convertView;
    }

    private class Holder {

        Holder(View view){
            tvTime = view.findViewById(R.id.tv_time);
            tvRemark = view.findViewById(R.id.tv_remark);
        }

        TextView tvTime;
        TextView tvRemark;
    }
}
