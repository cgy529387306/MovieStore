package com.android.mb.movie.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.mb.movie.base.BaseWebViewActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.Advert;
import com.android.mb.movie.rxbus.RxBus;
import com.android.mb.movie.view.DetailActivity;
import com.android.mb.movie.view.VideoListActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cgy on 19/1/12.
 */

public class ProjectHelper {

    /**
     * 手机验证
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    public static String getCommonText(String data){
        return data == null?"":data;
    }


    public static void openQQ(Context context,String qq){
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin="+qq;//uin是发送过去的qq号码
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"请检查是否安装QQ",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 用Intent打开url(即处理外部链接地址)
     *
     * @param context
     * @param url
     */
    public static void openUrlWithIntent(Context context, String url) {
        if (Helper.isNull(context) || Helper.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void getToAdvert(Advert advert, Context mContext){
        RxBus.getInstance().send(ProjectConstants.EVENT_VISIT_ADVERT,advert.getId());
        if (advert.getType()==1){
            Bundle bundle = new Bundle();
            bundle.putString("videoId",advert.getResId());
            NavigationHelper.startActivity((Activity) mContext, DetailActivity.class,bundle,false);
        }else if (advert.getType()==2){
            String name = advert.getDesc();
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("cateId",advert.getResId());
            NavigationHelper.startActivity((Activity) mContext, VideoListActivity.class,bundle,false);
        }else{
            Bundle bundle = new Bundle();
            bundle.putString(ProjectConstants.KEY_WEB_DETAIL_URL,advert.getRedirectUrl());
            NavigationHelper.startActivity((Activity) mContext, BaseWebViewActivity.class,bundle,false);
        }
    }
}