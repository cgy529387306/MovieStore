package com.android.mb.movie.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseWebViewActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.entity.Advert;
import com.android.mb.movie.entity.CateVideo;
import com.android.mb.movie.rxbus.RxBus;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.view.DetailActivity;
import com.android.mb.movie.view.VideoListActivity;
import com.android.mb.movie.widget.GlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;


/**
 * Created by necer on 2017/6/7.
 */
public class MovieGridAdapter extends BaseQuickAdapter<CateVideo, BaseViewHolder> {

    public MovieGridAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CateVideo item) {
        helper.setText(R.id.tv_type,item.getCateName());
        GridView gridView = helper.getView(R.id.gridMovie);
        Banner mBanner = helper.getView(R.id.bannerView);
        if (Helper.isNotEmpty(item.getAdvert())){
            List<Advert> mAdvertList = item.getAdvert();
            mBanner.setVisibility(View.VISIBLE);
            mBanner.setImageLoader(new GlideImageLoader());
            mBanner.setImages(mAdvertList);
            mBanner.start();
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (Helper.isNotEmpty(mAdvertList) && mAdvertList.size()>position){
                        Advert advert = mAdvertList.get(position);
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
            });
        } else {
            mBanner.setVisibility(View.GONE);
        }
        gridView.setAdapter(new MovieAdapter(mContext,item.getVideos()));
        helper.setOnClickListener(R.id.tv_type, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = item.getCateName();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("cateId",item.getCateId());
                NavigationHelper.startActivity((Activity) mContext, VideoListActivity.class,bundle,false);
            }
        });
    }


}




