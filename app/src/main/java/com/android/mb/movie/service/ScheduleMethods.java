package com.android.mb.movie.service;

import android.util.Base64;

import com.android.mb.movie.entity.AdData;
import com.android.mb.movie.entity.Avatar;
import com.android.mb.movie.entity.CommentListData;
import com.android.mb.movie.entity.CountData;
import com.android.mb.movie.entity.HomeData;
import com.android.mb.movie.entity.InviteBean;
import com.android.mb.movie.entity.QQBean;
import com.android.mb.movie.entity.SpecialData;
import com.android.mb.movie.entity.Tag;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.entity.VersionBean;
import com.android.mb.movie.entity.Video;
import com.android.mb.movie.entity.VideoData;
import com.android.mb.movie.entity.VideoListData;
import com.android.mb.movie.retrofit.cache.transformer.CacheTransformer;
import com.android.mb.movie.retrofit.http.RetrofitHttpClient;
import com.android.mb.movie.utils.JsonHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.QueryMap;
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

    public Observable getUserInfo(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getUserInfo(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable updateInfo(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().updateInfo(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<UserBean>());
    }

    public Observable forgetPassword(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().forgetPassword(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable updatePassword(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().updatePassword(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }


    public Observable uploadAvatar(File file){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part requestBody =
                MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        return getService().uploadAvatar(requestBody)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Avatar>());
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

    public Observable getFindData(@QueryMap Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getFindData(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<VideoListData>());
    }

    public Observable getTags(){
        return getService().getTags()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<List<Tag>>());
    }

    public Observable queryVideos(@QueryMap Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().queryVideos(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<VideoListData>());
    }

    public Observable comment(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().comment(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getVideoDetail(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getVideoDetail(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<VideoData>());
    }

    public Observable getVideoComments(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getVideoComments(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<CommentListData>());
    }

    public Observable praise(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().praise(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable watch(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().watch(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getHistory(@QueryMap Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getHistory(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<VideoListData>());
    }

    public Observable getLike(@QueryMap Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().getLike(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<VideoListData>());
    }

    public Observable delHistory(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().delHistory(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable delLike(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().delLike(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getCountData(){
        return getService().getCountData()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<CountData>());
    }

    public Observable getPromoCode(){
        return getService().getPromoCode()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<InviteBean>());
    }

    public Observable feedback(Map<String,Object> requestMap){
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().feedback(requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable feedback1(File file,Map<String,Object> requestMap){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part requestBody =
                MultipartBody.Part.createFormData("images", file.getName(), requestFile);
        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("params", Base64.encodeToString(JsonHelper.toJson(requestMap).getBytes(),Base64.DEFAULT));
        return getService().feedback1(requestBody,requestParams)
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<Object>());
    }

    public Observable getQQGroupNo(){
        return getService().getQQGroupNo()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<QQBean>());
    }

    public Observable getAppVersion(){
        return getService().getAppVersion()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<VersionBean>());
    }

    public Observable getAdvert(){
        return getService().getAdvert()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<AdData>());
    }

    public Observable getRecommendVideo(){
        return getService().getRecommendVideo()
                .compose(CacheTransformer.emptyTransformer())
                .map(new HttpCacheResultFunc<List<Video>>());
    }

}
