package com.gogo.haobutler.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gogo.haobutler.application.BaseApplication;

/**
 * @author: 闫昊
 * @date: 2018/4/12
 * @function:
 */

public abstract class BaseFragment extends Fragment{
    public Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    /**
     * Fragment第一次创建，绘制用户界面时，系统回调的方法
     * @param inflater 加载器/布局填充器、将xml文件转化为View对象
     * @param container 当前Fragment插入的Activity的布局视图对象
     * @param savedInstanceState 存储上一个Fragment的信息
     * @return 当前加载的Fragment视图，如果不提供视图也可返回null
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication.mRefWatcher.watch(this);

    }

    /**
     * 初始化数据
     */
    public void initData() {}

    /**
     * 初始化界面
     */
    public abstract void initView();
}
