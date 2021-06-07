package com.android.mb.movie.view;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseWebViewActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.AdData;
import com.android.mb.movie.entity.Advert;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.NavigationHelper;
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
    private ImageView mIvLoading;
    private TextView mTvSkip;
    private MyCountDownTimer mCountDownTimer;
    private Advert mAdvert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去除信号栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mIvLoading = findViewById(R.id.iv_loading);
        mTvSkip = findViewById(R.id.tv_skip);
        mTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CurrentUser.getInstance().isLogin()){
                    NavigationHelper.startActivity(LoadingActivity.this, MainActivity.class, null, true);
                }
            }
        });
        findViewById(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdvert!=null){
                    Bundle bundle = new Bundle();
                    bundle.putString(ProjectConstants.KEY_WEB_DETAIL_URL,mAdvert.getRedirectUrl());
                    NavigationHelper.startActivity(LoadingActivity.this, BaseWebViewActivity.class,bundle,false);
                }
            }
        });
        doRegister();
        getAdvert();
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture
         *      表示以「 毫秒 」为单位倒计时的总数
         *      例如 millisInFuture = 1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick()
         *      例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         *
         */

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        public void onFinish() {
            NavigationHelper.startActivity(LoadingActivity.this, MainActivity.class, null, true);
        }

        public void onTick(long millisUntilFinished) {
            String hint = millisUntilFinished / 1000 > 0 ? millisUntilFinished / 1000 + "跳过" : "跳过";
            mTvSkip.setText(hint);
        }

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
            }
        });
    }


    private void getAdvert(){
        Observable observable = ScheduleMethods.getInstance().getAdvert();
        toSubscribe(observable,  new Subscriber<AdData>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onNext(AdData result) {
                if (Helper.isNotEmpty(result.getAppStartAdvert())){
                    Advert advert = result.getAppStartAdvert().get(0);
                    mAdvert = advert;
                    ImageUtils.loadImageUrlLight(mIvLoading,advert.getCoverUrl());
                    mTvSkip.setText(String.format("%d跳过", advert.getSeconds()));
                    mCountDownTimer = new MyCountDownTimer(advert.getSeconds()*1000, 1000);
                    mCountDownTimer.start();
                }
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

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCompositeSubscription.clear();
        super.onDestroy();
    }


}
