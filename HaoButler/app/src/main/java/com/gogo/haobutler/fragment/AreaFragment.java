package com.gogo.haobutler.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gogo.haobutler.R;
import com.gogo.haobutler.model.weather.City;
import com.gogo.haobutler.model.weather.County;
import com.gogo.haobutler.model.weather.Province;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.gogo.haobutler.activity.WeatherActivity;
import com.gogo.haobutler.activity.WeatherShowActivity;
import com.gogo.haobutler.utils.Consts;
import com.gogo.haobutler.utils.JsonParser;
import com.gogo.haobutler.utils.net.HttpCallback;
import com.gogo.haobutler.utils.net.HttpUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/6/18 0018
 * @function: 使用litepal时，json 设置key为"id"坑啊
 */
public class AreaFragment extends BaseFragment {
    /**
     * UI
     */
    private View mView;
    private ImageView mImageView;
    private TextView mTextView;
    private ListView mListView;
    private ProgressBar mProgressBar;

    /**
     * Data
     * mCurrentLevel 当前的区域级别
     */
    private static final int AREA_LEVEL_PROVINCE = 0;
    private static final int AREA_LEVEL_CITY = 1;
    private static final int AREA_LEVEL_COUNTY = 2;
    private int mCurrentLevel;
    private ArrayAdapter<String> mAdapter;
    private List<String> mDataList = new ArrayList<>();
    private List<Province> mProvinceList = new ArrayList<>();
    private List<City> mCityList = new ArrayList<>();
    private List<County> mCountyList = new ArrayList<>();
    private Province mSelectedProvince;
    private City mSelectedCity;
    private County mSelectedCounty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_area_layout, container, false);
        initView();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("yh", "AreaFragment: onActivityCreated()");
        mListView.setOnItemClickListener(((parent, view, position, id) -> {
            switch (mCurrentLevel) {
                case AREA_LEVEL_PROVINCE:
                    mSelectedProvince = mProvinceList.get(position);
                    setCities();
                    break;
                case AREA_LEVEL_CITY:
                    mSelectedCity = mCityList.get(position);
                    setCounties();
                    break;
                case AREA_LEVEL_COUNTY:
                    String weatherId = mCountyList.get(position).getWeatherId();
                    String weatherUrl = Consts.HEFENG_WEATHER_URL + "?cityid=" + weatherId
                            + "&key=" + Consts.HEFENG_WEATHER_KEY;
                    Activity activity = getActivity();
                    if (activity instanceof WeatherActivity) {
                        //更新天气界面数据（位置变化）
                        activity.getSharedPreferences("weather", Context.MODE_PRIVATE)
                                .edit()
                                .putString("weatherUrl", "")
                                .apply();
                        Intent intentShow = new Intent(getActivity(), WeatherShowActivity.class);
                        intentShow.putExtra("weatherUrl", weatherUrl);
                        startActivity(intentShow);
                        getActivity().finish();
                    } else if (activity instanceof WeatherShowActivity) {
                        activity.getSharedPreferences("weather",Context.MODE_PRIVATE)
                                .edit()
                                .putString("weatherUrl", weatherUrl)
                                .apply();
                        ((WeatherShowActivity) activity).drawerLayout.closeDrawer(Gravity.START);
                        ((WeatherShowActivity) activity).swipeRefreshLayout.setRefreshing(true);
                        ((WeatherShowActivity) activity).refreshUI(weatherUrl);
                    }
                    break;
                default:
                    break;
            }
        }));
        mImageView.setOnClickListener(v -> {
            switch (mCurrentLevel) {
                case AREA_LEVEL_PROVINCE:
                    break;
                case AREA_LEVEL_CITY:
                    setProvinces();
                    break;
                case AREA_LEVEL_COUNTY:
                    setCities();
                    break;
                default:
                    break;
            }
        });
        setProvinces();
    }

    @Override
    public void initView() {
        Log.d("yh", "AreaFragment: initView()");
        mImageView = mView.findViewById(R.id.iv_back);
        mTextView = mView.findViewById(R.id.tv_title);
        mListView = mView.findViewById(R.id.list_area);
        mProgressBar = mView.findViewById(R.id.progress_bar);
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 1.从数据库中读取位置数据
     * 2.数据库中无相关数据，则从服务器上下载
     * 3.显示
     */
    private void setProvinces() {
        mCurrentLevel = AREA_LEVEL_PROVINCE;
        mTextView.setText(R.string.weather_china_area);
        mImageView.setVisibility(View.GONE);
        mProvinceList = DataSupport.findAll(Province.class);
        if (mProvinceList != null && mProvinceList.size() > 0) {
            mDataList.clear();
            for (Province province : mProvinceList) {
                Log.d("yh", "AreaFragment: setProvinces() --->" + province.toString());
                mDataList.add(province.getName());
            }
            removeNull(mDataList);
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
        } else {
            downloadAndShowAreaData(Consts.GUOLIN_AREA_URL);
        }
        Log.d("yh", "AreaFragment: setProvinces() --->mProvinceList.size() == " + mProvinceList.size());
    }

    private void setCities() {
        mCurrentLevel = AREA_LEVEL_CITY;
        mTextView.setText(mSelectedProvince.getName());
        mImageView.setVisibility(View.VISIBLE);
        mCityList = DataSupport.where("provinceId = ?",
                String.valueOf(mSelectedProvince.getProvinceCode())).find(City.class);
        if (mCityList != null && mCityList.size() > 0) {
            mDataList.clear();
            for (City city : mCityList) {
                Log.d("yh", "AreaFragment: setCities() --->" + city.toString());
                mDataList.add(city.getName());
            }
            removeNull(mDataList);
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
        } else {
            String cityUrl = Consts.GUOLIN_AREA_URL + "/" + mSelectedProvince.getProvinceCode();
            downloadAndShowAreaData(cityUrl);
        }
        Log.d("yh", "AreaFragment: setCities() --->mCityList.size() == " + mCityList.size());
    }

    private void setCounties() {
        mCurrentLevel = AREA_LEVEL_COUNTY;
        mTextView.setText(mSelectedCity.getName());
        mImageView.setVisibility(View.VISIBLE);
        mCountyList = DataSupport.where("cityId = ?",
                String.valueOf(mSelectedCity.getCityCode())).find(County.class);
        if (mCountyList != null && mCountyList.size() > 0) {
            mDataList.clear();
            for (County county : mCountyList) {
                Log.d("yh", "AreaFragment: setCounties() --->" + county.toString());
                mDataList.add(county.getName());
            }
            removeNull(mDataList);
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
        } else {
            String countyUrl = Consts.GUOLIN_AREA_URL + "/" + mSelectedProvince.getProvinceCode()
                    + "/" + mSelectedCity.getCityCode();
            downloadAndShowAreaData(countyUrl);
        }
    }

    /**
     * 从服务器上下载相关的位置数据，并显示
     *
     * @param url
     */
    private void downloadAndShowAreaData(String url) {
        showProgressBar();
        Log.d("yh", "AreaFragment: downloadAndShowAreaData() --->url == " + url);
        HttpUtil.getRequest(url, new HttpCallback() {
            @Override
            public void onSuccess(String response) {
                boolean result = false;
                Log.d("yh", "AreaFragment: downloadAndShowAreaData() --->json == " + response);
                switch (mCurrentLevel) {
                    case AREA_LEVEL_PROVINCE:
                        result = JsonParser.parseProvince(response);
                        break;
                    case AREA_LEVEL_CITY:
                        result = JsonParser.parseCity(response, mSelectedProvince.getProvinceCode());
                        break;
                    case AREA_LEVEL_COUNTY:
                        result = JsonParser.parseCounty(response, mSelectedCity.getCityCode());
                        break;
                    default:
                        break;
                }
                if (result) {
                    getActivity().runOnUiThread(() -> {
                        hideProgressBar();
                        switch (mCurrentLevel) {
                            case AREA_LEVEL_PROVINCE:
                                setProvinces();
                                break;
                            case AREA_LEVEL_CITY:
                                setCities();
                                break;
                            case AREA_LEVEL_COUNTY:
                                setCounties();
                                break;
                            default:
                                break;
                        }
                    });
                }
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeNull(List<String> list) {
        List<String> empty = new ArrayList<>(1);
        empty.add(null);
        list.removeAll(empty);
    }

    /**
     * 显示ProgressBar，设置用户无法与界面交互
     */
    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
