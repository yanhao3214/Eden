package com.gogo.haobutler.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.WebViewActivity;
import com.gogo.haobutler.adapter.MoocAdapter;
import com.gogo.haobutler.model.mooc.MoocData;
import com.gogo.haobutler.fragment.base.BaseFragment;
import com.gogo.haobutler.utils.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 闫昊
 * @date: 2018/5/8
 * @function: ListView、AsyncTask、CardView、json、
 */
public class MoocFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private View view;
    private ListView mListView;
    private MoocAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mooc_layout, container, false);
        initData();
        initView();
        return view;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        mListView = view.findViewById(R.id.lv_mooc);
        mSwipeRefreshLayout = view.findViewById(R.id.srl_mooc);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mSwipeRefreshLayout.setRefreshing(false));
        new MoocAsyncTask().execute(Consts.IMOOC_URL);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "开启你的慕课网学习之旅吧");
        bundle.putString("url", "https://www.icourse163.org/");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class MoocAsyncTask extends AsyncTask<String, Void, List<MoocData>> {

        @Override
        protected List<MoocData> doInBackground(String... strings) {
            String json = getJsonFromURL(strings[0]);
            return getDataFromJson(json);
        }

        @Override
        protected void onPostExecute(List<MoocData> moocBeans) {
            super.onPostExecute(moocBeans);
            mAdapter = new MoocAdapter(getContext(), moocBeans);
            mListView.setAdapter(mAdapter);
        }
    }

    private String getJsonFromURL(String url) {
        String result = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            InputStream is = new URL(url).openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<MoocData> getDataFromJson(String json) {
        List<MoocData> moocBeans = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                MoocData moocBean = new MoocData();
                JSONObject object = jsonArray.getJSONObject(i);
                moocBean.setIcon(object.getString("picSmall"));
                moocBean.setTitle(object.getString("name"));
                moocBean.setContent(object.getString("description"));
                moocBeans.add(moocBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moocBeans;
    }
}
