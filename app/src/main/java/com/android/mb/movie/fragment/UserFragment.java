package com.android.mb.movie.fragment;

import android.view.View;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseFragment;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.view.LoginActivity;

/**
 * Created by cgy on 19/1/19.
 */

public class UserFragment extends BaseFragment implements View.OnClickListener{

    @Override
    protected int getLayoutId() {
        return R.layout.frg_user;
    }

    @Override
    protected void bindViews(View view) {

    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
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
        }
    }
}
