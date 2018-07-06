package com.gogo.haobutler.utils.net;

/**
 * @author: 闫昊
 * @date: 2018/5/29 0029
 * @function: 网络请求回调接口
 */
public interface HttpCallback {

    /**
     * 网络请求成功后回调方法
     * @param response
     */
    void onSuccess(String response);

    /**
     * 网络请求失败后回调方法
     * @param e
     */
    void onFail(Exception e);
}
