package com.gogo.haobutler.callback;

/**
 * @author: 闫昊
 * @date: 2018/7/5 0005
 * @function: 网络请求回调接口
 */
public interface EdenNetCallback {

    /**
     * 网络请求成功
     * @param json 返回的json字符串
     */
    void ok(String json);

    /**
     * 网络请求失败
     * @param eMsg 错误码信息
     */
    void error(String eMsg);
}
