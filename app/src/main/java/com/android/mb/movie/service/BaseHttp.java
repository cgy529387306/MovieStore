package com.android.mb.movie.service;


import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.retrofit.http.RetrofitHttpClient;
import com.android.mb.movie.retrofit.http.entity.HttpResult;
import com.android.mb.movie.retrofit.http.exception.ApiException;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.functions.Func1;

/**
 * @Description 基础
 * @created by cgy on 2017/6/19
 * @version v1.0
 *
 */

public class BaseHttp {

    public static final String BASE_URL = "http://39.96.68.92:8080";

    public String getServerHost() {
        return BASE_URL;
    }

    @SuppressWarnings("unchecked")
    static class HttpCacheResultFunc<T> implements Func1 {
        @Override
        public Object call(Object o) {
            HttpResult<T> httpResult;
            if (o instanceof HttpResult) {
                httpResult = (HttpResult<T>) o;
                if (httpResult.getCode() == 200){
                    return httpResult.getData();
                }else {
                    throw new ApiException(ApiException.REQUEST_FAIL, httpResult.getMessage());
                }
            }
            return null;
        }
    }

    /**
     * 获取头部信息
     *
     * @return Map
     */
    Map<String, String> getHead() {
        Map<String, String> cloudOfficeHeader = new HashMap<String, String>();
        if (CurrentUser.getInstance().isLogin()){
            cloudOfficeHeader.put("accessToken",CurrentUser.getInstance().getAccesstoken());
        }
        return cloudOfficeHeader;
    }

}
