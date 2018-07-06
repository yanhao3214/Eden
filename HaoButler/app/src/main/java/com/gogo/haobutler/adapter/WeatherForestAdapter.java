package com.gogo.haobutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.weather.gson.Forecast;

import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/6/20 0020
 * @function: 天气预报适配器
 */
public class WeatherForestAdapter extends BaseAdapter {
    private List<Forecast> mDataList;
    private Context mContext;
    private LayoutInflater mInflater;

    public WeatherForestAdapter(Context context, List<Forecast> dataList) {
        mContext = context;
        mDataList = dataList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder holder;
        Forecast forecast = mDataList.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_forecast_weather, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDate.setText(forecast.forecastDate);
        holder.tvCondition.setText(forecast.condition.condition);
        holder.tvMax.setText(forecast.temperature.max);
        holder.tvMin.setText(forecast.temperature.min);
        return convertView;
    }

    private class ViewHolder {
        ViewHolder(View view) {
            tvDate = view.findViewById(R.id.tv_date);
            tvCondition = view.findViewById(R.id.tv_condition);
            tvMax = view.findViewById(R.id.tv_temp_max);
            tvMin = view.findViewById(R.id.tv_temp_min);
        }

        private TextView tvDate;
        private TextView tvCondition;
        private TextView tvMax;
        private TextView tvMin;
    }
}
