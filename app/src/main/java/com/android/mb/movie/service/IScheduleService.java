package com.android.mb.movie.service;

import com.android.mb.movie.entity.LoginData;
import com.android.mb.movie.retrofit.http.entity.HttpResult;

import java.util.Map;

import retrofit2.http.GET;
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
    Observable<HttpResult<LoginData>> getCode(@QueryMap Map<String,Object> requestMap);

    /**
     * avatar:头像文件流
     * @param requestMap
     * @return
     */
    @POST("/api/1.0/user/uploadAvatar")
    Observable<HttpResult<LoginData>> uploadAvatar(@Part Map<String,Object> requestMap);

    /**
     * userId:用户id
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/getInfo")
    Observable<HttpResult<LoginData>> getUserInfo(@Part Map<String,Object> requestMap);

    /**
     * userId:用户id
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/updateInfo")
    Observable<HttpResult<LoginData>> updateInfo(@Part Map<String,Object> requestMap);

    /**
     * account:手机号或者用户名  password:密码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/login")
    Observable<HttpResult<LoginData>> userLogin(@QueryMap Map<String,Object> requestMap);

    /**
     * account:手机号或者用户名  password:密码 code:验证码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/register")
    Observable<HttpResult<LoginData>> userRegister(@QueryMap Map<String,Object> requestMap);


    /**
     * phone:手机号 newPassword:新密码 valcode:验证码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/forgetPassword")
    Observable<HttpResult<LoginData>> forgetPassword(@QueryMap Map<String,Object> requestMap);

    /**
     * newPassword:新密码 newPassword:旧密码
     * @param requestMap
     * @return
     */
    @GET("/api/1.0/user/updatePassword")
    Observable<HttpResult<LoginData>> updatePassword(@QueryMap Map<String,Object> requestMap);



    @POST("/api/1.0/app/user/bindWx")
    Observable<HttpResult<Object>> bindWx(@QueryMap Map<String,Object> requestMap);

}
