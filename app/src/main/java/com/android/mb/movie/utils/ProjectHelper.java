package com.android.mb.movie.utils;

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

}