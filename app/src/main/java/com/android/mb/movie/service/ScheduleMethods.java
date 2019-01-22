package com.android.mb.movie.service;

import android.util.Base64;

import com.android.mb.movie.entity.HomeData;
import com.android.mb.movie.entity.SpecialData;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.retrofit.cache.transformer.CacheTransformer;
import com.android.mb.movie.retrofit.http.RetrofitHttpClient;
import com.android.mb.movie.utils.JsonHelper;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * @Created by cgy on 2017/6/27
 */
@SuppressWarnings("unchecked")
public class ScheduleMethods extends BaseHttp {


    private static class SingletonHolder {
        private static final ScheduleMethods INSTANCE = new ScheduleMethods();
    }

    //获取单例
    public static ScheduleMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private IScheduleService getService() {
        return new RetrofitHttpClient.Builder()
                .baseUrl(getServerHost())
                .addHeader(getHead())
                .addDotNetDeserializer(false)
                .addCache(false)
                .addLog(true)
                .build()
                .retrofit()
                .create(IScheduleService.class);
    }



    public Observable getHotList(){
        return getService().getHotList()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable userLogin(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().userLogin(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable userRegister(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().userRegister(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable getCode(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getCode(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable getHomeData(){
        return getService().getHomeData()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<HomeData>());
    }

    public Observable getSpecialData(){
        return getService().getSpecialData()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<SpecialData>());
    }

}
