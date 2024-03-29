package com.android.mb.movie.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.adapter.MyFragmentPagerAdapter;
import com.android.mb.movie.base.BaseActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.AdData;
import com.android.mb.movie.entity.Advert;
import com.android.mb.movie.fragment.AdvertDialogFragment;
import com.android.mb.movie.fragment.FindFragment;
import com.android.mb.movie.fragment.MainFragment;
import com.android.mb.movie.fragment.SpecialFragment;
import com.android.mb.movie.fragment.UserFragment;
import com.android.mb.movie.rxbus.Events;
import com.android.mb.movie.service.ScheduleMethods;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.widget.FragmentViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private FragmentViewPager mFragmentViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void loadIntent() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mFragmentViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        initTabPager();
        getAdvert();
    }

    @Override
    protected void onRightAction() {
        super.onRightAction();
//        NavigationHelper.startActivity(mContext,DetailActivity.class,null,false);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        regiestEvent(ProjectConstants.EVENT_VISIT_ADVERT, new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                String advertId = (String) events.content;
                visitAdvert(advertId);
            }
        });
    }

    private void visitAdvert(String advertId){
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("advertId", advertId);
        Observable observable = ScheduleMethods.getInstance().visitAdvert(requestMap);
        toSubscribe(observable,  new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onNext(Object result) {
            }
        });
    }

    @Override
    protected void setListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initTabPager(){
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new MainFragment());
        mFragmentList.add(new SpecialFragment());
        mFragmentList.add(new FindFragment());
        mFragmentList.add(new UserFragment());
        mFragmentViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mFragmentViewPager.setOffscreenPageLimit(mFragmentList.size());
        mFragmentViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        String[] tabTitles = new String[]{"首页","频道","发现","我的"};
        int[] tabImages = new int[]{R.drawable.btn_tab_main,R.drawable.btn_tab_channel,R.drawable.btn_tab_discover,R.drawable.btn_tab_user};
        for (int i = 0; i < tabTitles.length; i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab,null);
            TextView tvTitle = view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitles[i]);
            ImageView imgTab =  view.findViewById(R.id.iv_tab);
            imgTab.setImageResource(tabImages[i]);
            tab.setCustomView(view);
            mTabLayout.addTab(tab);
        }
    }

    private static final long DOUBLE_CLICK_INTERVAL = 2000;
    private long mLastClickTimeMills = 0;
    @Override
    public void onBackPressed() {
        Fragment currentFragment = mFragmentList.get(mFragmentViewPager.getCurrentItem());
        if (currentFragment instanceof FindFragment){
            boolean isPlaying = ((FindFragment)currentFragment).onBackPressed();
            if (isPlaying){
                return;
            }
        }
        if (System.currentTimeMillis() - mLastClickTimeMills > DOUBLE_CLICK_INTERVAL) {
            ToastHelper.showToast("再按一次返回退出");
            mLastClickTimeMills = System.currentTimeMillis();
            return;
        }
        finish();
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
                if (Helper.isNotEmpty(result.getAppPopAdvert())){
                    Advert advert = result.getAppPopAdvert().get(0);
                    AdvertDialogFragment dialogFragment = AdvertDialogFragment.newInstance(advert);
                    dialogFragment.show(getFragmentManager(),"dialog");
                }
            }
        });
    }


}
