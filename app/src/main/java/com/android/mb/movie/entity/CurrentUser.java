package com.android.mb.movie.entity;

import android.util.Log;

import com.android.mb.movie.utils.JsonHelper;
import com.android.mb.movie.utils.PreferencesHelper;


/**
 * 作者：cgy on 16/12/26 22:53
 * 邮箱：529387306@qq.com
 */
public class CurrentUser extends UserBean{

    //region 单例
    private static final String TAG = CurrentUser.class.getSimpleName();
    private static final String USER = "CurrentUser";
    private static CurrentUser me;
    /**
     * 单例
     * @return 当前用户对象
     */
    public static CurrentUser getInstance() {
        if (me == null) {
            me = new CurrentUser();
        }
        return me;
    }

    /**
     * 出生
     * <p>尼玛！终于出生了！！！</p>
     * <p>调用此方法查询是否登录过</p>
     * @return 出生与否
     */
    public boolean isLogin() {
        String json = PreferencesHelper.getInstance().getString(USER);
        me = JsonHelper.fromJson(json, CurrentUser.class);
        return me != null;
    }

    public boolean login(UserBean entity) {
        boolean born = false;
        String json = "";
        if (entity != null) {
            me.setAccesstoken(entity.getAccesstoken());
            me.setUserid(entity.getUserid());
            me.setPhone(entity.getPhone());
            me.setSex(entity.getSex());
            me.setSexText(entity.getSexText());
            me.setUserType(entity.getUserType());
            me.setManager(entity.isManager());
            me.setCreate_time(entity.getCreate_time());
            me.setAvatar_url(entity.getAvatar_url());
            me.setNickname(entity.getNickname());
            json = JsonHelper.toJson(me);
            born = me != null;
        }
        // 生完了
        if (!born) {
            Log.e(TAG, "尼玛，流产了！！！");
        } else {
            PreferencesHelper.getInstance().putString(USER,json);
        }
        return born;
    }

    // endregion 单例

    /**
     * 退出登录清理用户信息
     */
    public void loginOut() {
        me = null;
        PreferencesHelper.getInstance().putString(USER, "");
    }
}
