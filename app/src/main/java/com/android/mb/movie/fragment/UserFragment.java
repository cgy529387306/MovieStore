package com.android.mb.movie.fragment;

import android.view.View;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseFragment;
import com.android.mb.movie.base.BaseMvpFragment;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.UserPresenter;
import com.android.mb.movie.rxbus.Events;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.view.LoginActivity;
import com.android.mb.movie.view.SettingActivity;
import com.android.mb.movie.view.interfaces.IUserView;
import com.android.mb.movie.widget.CircleImageView;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by cgy on 19/1/19.
 */

public class UserFragment extends BaseMvpFragment<UserPresenter,IUserView> implements IUserView, View.OnClickListener{

    private TextView mTvLogin;
    private TextView mTvName;
    private CircleImageView mIvAvatar;
    @Override
    protected int getLayoutId() {
        return R.layout.frg_user;
    }

    @Override
    protected void bindViews(View view) {
        mIvAvatar = view.findViewById(R.id.iv_avatar);
        mTvLogin = view.findViewById(R.id.tv_login);
        mTvName = view.findViewById(R.id.tv_userName);
    }

    @Override
    protected void processLogic() {
        initUserInfo();
    }

    @Override
    protected void setListener() {
        mTvLogin.setOnClickListener(this);
        mRootView.findViewById(R.id.tv_promote).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_feedback).setOnClickListener(this);
        mRootView.findViewById(R.id.tv_potato).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_history).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_cache).setOnClickListener(this);
        mRootView.findViewById(R.id.rl_favor).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_setting).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_promote){
            if (CurrentUser.getInstance().isLogin()){

            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.tv_feedback){
            if (CurrentUser.getInstance().isLogin()){

            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.tv_potato){
            if (CurrentUser.getInstance().isLogin()){

            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_history){
            if (CurrentUser.getInstance().isLogin()){

            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_cache){
            if (CurrentUser.getInstance().isLogin()){

            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.rl_favor){
            if (CurrentUser.getInstance().isLogin()){

            }else{
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }else if (id == R.id.btn_setting){
            //TODO
            NavigationHelper.startActivity(getActivity(), SettingActivity.class,null,false);
        }else if (id == R.id.tv_login){
            if (!CurrentUser.getInstance().isLogin()){
                NavigationHelper.startActivity(getActivity(), LoginActivity.class,null,false);
            }
        }
    }

    private void getUserInfo(){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("userid",CurrentUser.getInstance().getUserid());
        mPresenter.getUserInfo(requestMap);
    }

    private void initUserInfo(){
        if (CurrentUser.getInstance()!=null && CurrentUser.getInstance().isLogin()){
            mTvLogin.setText("Lv0小白");
            mTvName.setText(CurrentUser.getInstance().getNickname());
            ImageUtils.displayAvatar(getActivity(),CurrentUser.getInstance().getPhone(),mIvAvatar);
        }else{
            mTvName.setText("看官大人请登录");
        }
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    public void getSuccess(UserBean result) {
        if (result !=null){
            CurrentUser.getInstance().login(result);
            sendMsg(ProjectConstants.EVENT_UPDATE_USER_INFO,null);
        }
    }
}
