package com.gogo.haobutler.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author: 闫昊
 * @date: 2018/5/24 0024
 * @function:
 */
public interface RequestServices {

    /**
     * 请求参数
     * @param phone
     * @return
     */
    @GET("tel")
    Call<ResponseBody> getString(@Path("tel") String phone);
}
