package com.android.mb.movie.service;

import com.android.mb.movie.entity.Avatar;
import com.android.mb.movie.entity.CommentListData;
import com.android.mb.movie.entity.CountData;
import com.android.mb.movie.entity.CurrentUser;
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
import com.android.mb.movie.retrofit.http.entity.HttpResult;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @created by cgy on 2017/6/19
 */
public interface IScheduleService {
    @GET("/api/skill/hot/list")
    Observable<HttpResult<Object>> getHotList();

    /**
     * phone:手机号  type:类型，0:用户注册 1:忘记密码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/getCode")
    Observable<HttpResult<UserBean>> getCode(@QueryMap Map<String,Object> requestMap);

    /**
     * avatar:头像文件流
     * @return
     */
    @POST("/api/1.0/user/uploadAvatar")
    @Multipart
    Observable<HttpResult<Avatar>> uploadAvatar(@Part MultipartBody.Part file);

    /**
     * userId:用户id
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/getInfo")
    Observable<HttpResult<UserBean>> getUserInfo(@QueryMap Map<String,Object> requestMap);

    /**
     * userId:用户id
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/updateInfo")
    Observable<HttpResult<UserBean>> updateInfo(@QueryMap Map<String,Object> requestMap);

    /**
     * account:手机号或者用户名  password:密码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/login")
    Observable<HttpResult<UserBean>> userLogin(@QueryMap Map<String,Object> requestMap);

    /**
     * account:手机号或者用户名  password:密码 code:验证码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/register")
    Observable<HttpResult<CurrentUser>> userRegister(@QueryMap Map<String,Object> requestMap);


    /**
     * phone:手机号 newPassword:新密码 valcode:验证码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/forgetPassword")
    Observable<HttpResult<Object>> forgetPassword(@QueryMap Map<String,Object> requestMap);

    /**
     * newPassword:新密码 newPassword:旧密码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/updatePassword")
    Observable<HttpResult<Object>> updatePassword(@QueryMap Map<String,Object> requestMap);


    /**
     * @return
     */
    @GET("/api/1.0/common/getHomeData")
    Observable<HttpResult<HomeData>> getHomeData();

    /**
     * @return
     */
    @GET("/api/1.0/common/getSpecialData")
    Observable<HttpResult<SpecialData>> getSpecialData();


    /**
     * @return
     */
    @GET("/api/1.0/common/getTags")
    Observable<HttpResult<List<Tag>>> getTags();


    /**
     * @return
     */
    @GET("/api/1.0/common/getFindData")
    Observable<HttpResult<VideoListData>> getFindData(@QueryMap Map<String,Object> requestMap);

    /**
     * @return
     */
    @GET("/api/1.0/common/queryVideos")
    Observable<HttpResult<VideoListData>> queryVideos(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * @return
     */
    @GET("/api/1.0/common/praise")
    Observable<HttpResult<Object>> praise(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * @return
     */
    @GET("/api/1.0/common/watch")
    Observable<HttpResult<Object>> watch(@QueryMap Map<String,Object> requestMap);


    /**
     * @return
     */
    @GET("/api/1.0/common/getHistorys")
    Observable<HttpResult<VideoListData>> getHistory(@QueryMap Map<String,Object> requestMap);

    /**
     * @return
     */
    @GET("/api/1.0/common/getLikeVideos")
    Observable<HttpResult<VideoListData>> getLike(@QueryMap Map<String,Object> requestMap);

    /**
     * @return
     */
    @GET("/api/1.0/common/delHistorys")
    Observable<HttpResult<Object>> delHistory(@QueryMap Map<String,Object> requestMap);

    /**
     * @return
     */
    @GET("/api/1.0/common/delLikeVideos")
    Observable<HttpResult<Object>> delLike(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * content
     * @return
     */
    @GET("/api/1.0/common/getVideoDetail")
    Observable<HttpResult<VideoData>> getVideoDetail(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * content
     * @return
     */
    @GET("/api/1.0/common/comment")
    Observable<HttpResult<Object>> comment(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * @return
     */
    @GET("/api/1.0/common/getVideoComments")
    Observable<HttpResult<CommentListData>> getVideoComments(@QueryMap Map<String,Object> requestMap);

    /**
     * videoId
     * @return
     */
    @GET("/api/1.0/common/getCountData")
    Observable<HttpResult<CountData>> getCountData();


    /**
     * videoId
     * @return
     */
    @GET("/api/1.0/common/getPromoCode")
    Observable<HttpResult<InviteBean>> getPromoCode();

    /**
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/common/feedback")
    Observable<HttpResult<Object>> feedback(@QueryMap Map<String,Object> requestMap);


    /**
     * avatar:头像文件流
     * @return
     */
    @POST("/api/1.0/common/feedback")
    @Multipart
    Observable<HttpResult<Avatar>> feedback1(@Part MultipartBody.Part file,@QueryMap Map<String,Object> requestMap);

    /**
     * @return
     */
    @GET("/api/1.0/common/getQQGroupNo")
    Observable<HttpResult<QQBean>> getQQGroupNo();

    /**
     * @return
     */
    @GET("/api/1.0/common/getAppVersion")
    Observable<HttpResult<VersionBean>> getAppVersion();
}
