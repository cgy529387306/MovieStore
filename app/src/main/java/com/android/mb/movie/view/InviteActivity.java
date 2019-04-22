package com.android.mb.movie.view;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.entity.InviteBean;
import com.android.mb.movie.presenter.InvitePresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.PreferencesHelper;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.IInviteView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class InviteActivity extends BaseMvpActivity<InvitePresenter,IInviteView> implements IInviteView, View.OnClickListener{

    private TextView mTvInviteCode;
    private ImageView mIvQrCode;
    private String mInviteText;
    private String mQrCode;

    @Override
    protected void loadIntent() {
        mQrCode = PreferencesHelper.getInstance().getString("qrCode");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    protected void initTitle() {
        hideActionbar();
    }

    @Override
    protected void bindViews() {
        mTvInviteCode = findViewById(R.id.tv_invite_code);
        mIvQrCode = findViewById(R.id.iv_qrCode);
        if (Helper.isNotEmpty(mQrCode)){
            Bitmap bitmap = ImageUtils.createQRImage(mQrCode,AppHelper.calDpi2px(160),AppHelper.calDpi2px(160));
            if (Helper.isNotEmpty(bitmap)){
                mIvQrCode.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter.getPromoCode();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.iv_back_black).setOnClickListener(this);
        findViewById(R.id.tv_download).setOnClickListener(this);
        findViewById(R.id.tv_copy).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back_black){
            finish();
        }else if (id == R.id.tv_download){
            mIvQrCode.setDrawingCacheEnabled(true);
            Bitmap bitmap = mIvQrCode.getDrawingCache();
            if (bitmap!=null){
                ImageUtils.saveBmp2Gallery(InviteActivity.this,bitmap,"qrCode");
                mIvQrCode.setDrawingCacheEnabled(false);
            }
        }else if(id == R.id.tv_copy){
            if (Helper.isNotEmpty(mInviteText)){
                AppHelper.copyToClipboard(InviteActivity.this,mInviteText);
                ToastHelper.showToast("复制成功");
            }
        }
    }



    @Override
    protected InvitePresenter createPresenter() {
        return new InvitePresenter();
    }

    @Override
    public void getSuccess(InviteBean result) {
        if (Helper.isNotEmpty(result)){
            mTvInviteCode.setText(result.getPromoCode());
            String downloadUrl = result.getDownloadUrl()+"?promoCode="+result.getPromoCode();
            mInviteText = result.getShareText().replace("{main}",downloadUrl);
            if (Helper.isEmpty(mQrCode) || !mQrCode.equals(downloadUrl)){
                PreferencesHelper.getInstance().putString("qrCode",downloadUrl);
                Bitmap bitmap = ImageUtils.createQRImage(downloadUrl,AppHelper.calDpi2px(160),AppHelper.calDpi2px(160));
                if (Helper.isNotEmpty(bitmap)){
                    mIvQrCode.setImageBitmap(bitmap);
                }
            }
        }
    }

}
