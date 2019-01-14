package com.android.mb.movie.service;

import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.LoginData;
import com.android.mb.movie.retrofit.cache.transformer.CacheTransformer;
import com.android.mb.movie.retrofit.http.RetrofitHttpClient;

import java.util.Map;

import rx.Observable;

/**
 * @Created by cgy on 2017/6/27
 */
@SuppressWarnings("unchecked")
public class ScheduleMethods extends BaseHttp {

    private ScheduleMethods(){}

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
        return getService().userLogin(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }

    public Observable userRegister(Map<String,Object> requestMap){
        return getService().userRegister(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }

    public Observable getCode(Map<String,Object> requestMap){
        return getService().getCode(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<LoginData>());
    }

    public Observable bindWx(Map<String,Object> requestMap){
        return getService().bindWx(requestMap)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }


}
