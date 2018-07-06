package com.gogo.haobutler.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gogo.haobutler.R;
import com.gogo.haobutler.adapter.WeChatAdapter;
import com.gogo.haobutler.model.wechat.WeChatData;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.gogo.haobutler.activity.WebViewActivity;
import com.gogo.haobutler.utils.Consts;
import com.gogo.haobutler.utils.net.HttpCallback;
import com.gogo.haobutler.utils.net.HttpUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/4/12
 * @function:
 */

public class WeChatFragment extends BaseFragment {
    private View view;
    private ListView mListView;
    private List<WeChatData> mDataList = new ArrayList<>();
    private WeChatAdapter mAdapter;
    private WeChatHandler mHandler = new WeChatHandler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wechat_layout, container, false);
        initView();
        return view;
    }

    @Override
    public void initView() {
        mListView = view.findViewById(R.id.lv_wechat);
        mAdapter = new WeChatAdapter(context, mDataList);
        mListView.setAdapter(mAdapter);
        initListData();
    }

    /**
     * 请求网络数据，解析并更新UI
     * 为item增加点击事件，跳转到WebView
     */
    private void initListData() {
        String mUrl = Consts.JUHE_WECHAT_URL + "?key=" + Consts.JUHE_WECHAT_KEY + "&ps=50";
        HttpUtil.getRequest(mUrl, new HttpCallback() {
            @Override
            public void onSuccess(String response) {
                if (TextUtils.isEmpty(response)) {
                    Toast.makeText(context, getString(R.string.wechat_no_data), Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Message msg = Message.obtain();
                    msg.obj = parseJsonByFastJson(response);
                    mHandler.sendMessage(msg);
                }
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setOnItemClickListener((parent, view, position, id) ->
                showWeChatWeb(mDataList.get(position)));
    }

    /**
     * 跳转WeChatActivity，加载详情页面
     */
    private void showWeChatWeb(WeChatData data) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", data.getTitle());
        bundle.putString("url", data.getUrl());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 用FastJson解析获取的数据
     *
     * @param json
     * @return
     */
    private List<WeChatData> parseJsonByFastJson(String json) {
        JSONObject object = JSON.parseObject(json);
        JSONObject result = object.getJSONObject("result");
        JSONArray list = result.getJSONArray("list");
        Log.d("yh", "WeChatFragment: first title == " + list.toJavaList(WeChatData.class).get(0).getTitle());
        return list.toJavaList(WeChatData.class);
    }

    /**
     * 更新UI
     * 要真正改变原数据，仅仅改变mDataList的指向（mDataList = newList）无效
     */
    @SuppressLint("HandlerLeak")
    @SuppressWarnings("unchecked")
    private class WeChatHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDataList.clear();
            mDataList.addAll((Collection<? extends WeChatData>) msg.obj);
            mAdapter.notifyDataSetChanged();
        }
    }
}
