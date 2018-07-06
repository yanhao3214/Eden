package com.gogo.haobutler.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gogo.haobutler.R;
import com.gogo.haobutler.adapter.CourierBaseAdapter;
import com.gogo.haobutler.model.courier.CourierInfo;
import com.gogo.haobutler.activity.base.BaseActivity;
import com.gogo.haobutler.utils.Consts;
import com.gogo.haobutler.utils.net.HttpCallback;
import com.gogo.haobutler.utils.net.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 闫昊
 * @date: 2018/5/23 0023
 * @function: 快递查询
 */
public class DistributionActivity
        extends BaseActivity
        implements AdapterView.OnItemSelectedListener {
    private EditText edtNumber;
    private List<CourierInfo> mInfoList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private static CourierBaseAdapter mCourierAdapter;
    private String company;
    private CourierHandler mHandler = new CourierHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution_layout);
        initView();
        testFastJson();
        getMemoryCache();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        Spinner spinCompany = findViewById(R.id.spinner_company);
        edtNumber = findViewById(R.id.edit_number);
        Button btnQuery = findViewById(R.id.btn_query);
        ListView listInfo = findViewById(R.id.list_info);

        edtNumber.setText("438532650175");
        edtNumber.setSelection(0, edtNumber.getText().toString().length());
        hideKeyboard(edtNumber);
        btnQuery.setOnClickListener(this);

        ArrayAdapter mSpinAdapter = ArrayAdapter.createFromResource(this,
                R.array.distribution_company, android.R.layout.simple_spinner_item);
        mSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCompany.setAdapter(mSpinAdapter);
        spinCompany.setOnItemSelectedListener(this);
        // TODO: 2018/6/3 0003 带true与否的区别？
        spinCompany.setSelection(0, true);

        mCourierAdapter = new CourierBaseAdapter(this, mInfoList);
        listInfo.setAdapter(mCourierAdapter);
        listInfo.setDivider(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                String number = edtNumber.getText().toString().trim();
                if (TextUtils.isEmpty(number)) {
                    Toast.makeText(this, getString(R.string.input_no_empty),
                            Toast.LENGTH_SHORT).show();
                } else {
                    String url = Consts.JUHE_COURIER_URL + "?key=" + Consts.JUHE_COURIER_KEY
                            + "&com=" + company + "&no=" + number;
                    HttpUtil.getRequest(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String response) {
                            if (TextUtils.isEmpty(response)) {
                                Toast.makeText(DistributionActivity.this, getString(
                                        R.string.attribution_no_result), Toast.LENGTH_SHORT).show();
                            } else {
                                List<CourierInfo> infoList = parseJsonByFastJson(response);
                                Collections.reverse(infoList);
                                setCourierInfo(infoList);
                            }
                        }

                        @Override
                        public void onFail(Exception e) {
                            Toast.makeText(DistributionActivity.this, e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void setCourierInfo(List<CourierInfo> infos) {
        Message message = Message.obtain();
        message.obj = infos;
        mHandler.sendMessage(message);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                company = "sf";
                break;
            case 1:
                company = "st";
                break;
            case 2:
                company = "yt";
                break;
            case 3:
                company = "yd";
                break;
            case 4:
                company = "tt";
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * 原生解析json数据
     *
     * @param response
     * @return
     */
    private List<CourierInfo> parseJsonManual(String response) {
        List<CourierInfo> infoList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(response);
            JSONObject result = object.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject temp = list.getJSONObject(i);
                CourierInfo courierInfo = new CourierInfo();
                courierInfo.setDatetime(temp.optString("datetime", "no datetime"));
                courierInfo.setRemark(temp.optString("remark", "no remark"));
                courierInfo.setZone(temp.optString("zone", "no zone"));
                infoList.add(courierInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infoList;
    }

    /**
     * 1. 通过JSON.parseObject(jsonString)获取最外层JSON对象object
     * 2. 通过object.getJSONObject()或者object.getJSONArray()逐层解析
     * 3. a.通过(1)jsonArray.toJavaList(Bean.class);
     * (2)JSON.parseArray(jsonArray.toJSONString(), Bean.class)
     * 最终获取java对象列表；
     * b.通过(1)jsonObject.toJavaObject(Bean.class);
     * (2)JSON.parseObject(jsonObject.toJSONString(), Bean.class)
     * 最终获得java对象
     *
     * @param response
     * @return
     */
    private List<CourierInfo> parseJsonByFastJson(String response) {
        com.alibaba.fastjson.JSONObject object = JSON.parseObject(response);
        com.alibaba.fastjson.JSONObject result = object.getJSONObject("result");
        com.alibaba.fastjson.JSONArray list = result.getJSONArray("list");
        //两种方法将JSONArray转化为java list：
        List<CourierInfo> infoList = JSON.parseArray(list.toJSONString(), CourierInfo.class);
        Log.d("yh", "parseJsonByFastJson: infoList.size() == " + infoList.size());
        return list.toJavaList(CourierInfo.class);
    }

    @SuppressWarnings("unchecked")
    private static class CourierHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCourierAdapter.setList((List<CourierInfo>) msg.obj);
            mCourierAdapter.notifyDataSetChanged();
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null && im.isActive(view)) {
            im.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 获取应用最大内存
     */
    private void getMemoryCache() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memoryCache = manager.getMemoryClass();
        int largeCache = manager.getLargeMemoryClass();
        Log.d("yh", "getMemoryClass == " + memoryCache + "\n"
                + "getLargeMemoryClass() == " + largeCache);
    }

    /**
     * FastJson测试用
     */
    private void testFastJson() {
        Map<String, Object> map1 = new HashMap<>(4);
        map1.put("one", "哈哈");
        map1.put("two", "卧槽");
        map1.put("three", "lily");
        map1.put("four", "煞笔");
        String json1 = JSON.toJSONString(map1);
        Log.d("yh", "jsonTest1: string1 == " + json1);

        Map<String, Object> map2 = new HashMap<>(3);
        map2.put("闫昊", "老公啊");
        map2.put("李莉", "煞笔啊");
        map2.put("couple", 1314);
        String json2 = JSON.toJSONString(map2);
        Log.d("yh", "jsonTest2: string2 == " + json2);

        Map<String, Object> map3 = new HashMap<>(5);
        map3.put("province1", "江苏");
        map3.put("province2", "安徽");
        map3.put("province3", "浙江");
        map3.put("city1", "南京");
        map3.put("city2", "杭州");
        String json3 = JSON.toJSONString(map3);
        Log.d("yh", "jsonTest3: string == " + json3);

        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        String jsonList = JSON.toJSONString(mapList, true);
        Log.d("yh", "jsonTest4: string == " + jsonList);

        Map map = JSON.parseObject(json1, Map.class);
        Log.d("yh", "jsonTest5: " + map.get("one") + "\t" + map.get("two") + "\t" +
                map.get("three") + "\t" + map.get("four"));
        List<Map> list = JSON.parseArray(jsonList, Map.class);
        Log.d("yh", "jsonTest6: list.size() == " + list.size() + "\n" +
                list.get(2).get("city2"));
    }

    private void useRetrofit() {
        // TODO: 2018/6/3 0003 use of retrofit...
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Consts.BAIDU_PHONE_URL)
//                .build();
//        RequestServices requestServices = retrofit.create(RequestServices.class);
//        Call<ResponseBody> call = requestServices.getString("18061602102");
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String result = "";
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//                        result = response.body().toString();
//                    }
//                    Toast.makeText(DistributionActivity.this, "json: " + result,
//                            Toast.LENGTH_SHORT).show();
//                    Log.d("yh", "Distribution: onResponse: " + result);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(DistributionActivity.this,
//                        R.string.distribution_net_access_error, Toast.LENGTH_SHORT).show();
//                Log.d("yh", "Distribution: onFailure");
//            }
//        });
    }
}
