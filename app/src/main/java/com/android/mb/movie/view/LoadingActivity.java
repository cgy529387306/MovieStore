package com.android.mb.movie.view;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.mb.movie.R;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.DeviceHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;
import com.android.mb.movie.utils.WifiMacUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * 引导页
 * @author cgy
 */

public class LoadingActivity extends AppCompatActivity {
    private static final int LOADING_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去除信号栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        findViewById(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectHelper.openUrlWithIntent(LoadingActivity.this,"http://www.xf3838.com");
            }
        });
        doRegister();
    }

    private void doRegister(){
        String deviceId = WifiMacUtils.getMac(this);
        Log.d("deviceId", deviceId);
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("account", deviceId);
        requestMap.put("password","'123456'");
        requestMap.put("code","111111");
        Observable observable = ScheduleMethods.getInstance().userRegister(requestMap);
        toSubscribe(observable,  new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(UserBean result) {
                CurrentUser.getInstance().login(result);
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        NavigationHelper.startActivity(LoadingActivity.this, MainActivity.class, null, true);
                    }

                }, LOADING_TIME_OUT);
            }
        });
    }

    protected CompositeSubscription mCompositeSubscription;
    protected <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {

        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s));
    }


}
