package com.android.mb.movie.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.presenter.ChangePwdPresenter;
import com.android.mb.movie.presenter.FeedbackPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.ToastHelper;
import com.android.mb.movie.view.interfaces.IChangePwdView;
import com.android.mb.movie.view.interfaces.IFeedbackView;
import com.android.mb.movie.widget.BottomMenuDialog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈
 * Created by cgy on 2018\8\20 0020.
 */

public class FeedbackActivity extends BaseMvpActivity<FeedbackPresenter,IFeedbackView> implements IFeedbackView, View.OnClickListener{

    private EditText mEtContent;
    private TextView mTvRest;
    private ImageView mIvImage;
    private BottomMenuDialog mPickDialog;
    private String mTempFilePath = AppHelper.getBaseCachePath()
            .concat(String.valueOf(System.currentTimeMillis())).concat(".png");

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initTitle() {
        setTitleText("意见反馈");
    }

    @Override
    protected void bindViews() {
        mEtContent = findViewById(R.id.et_content);
        mTvRest = findViewById(R.id.tv_rest);
        mIvImage = findViewById(R.id.iv_image);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        mEtContent.addTextChangedListener(myTextWatcher);
        mIvImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_confirm){
            doConfirm();
        }else if (id == R.id.iv_image){
            pickPhoto();
        }
    }

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void afterTextChanged(Editable editable) {
            int restCount = 100 - mEtContent.getText().toString().length();
            mTvRest.setText(restCount+"/100");
        }
    };



    private void doConfirm(){
        String content = mEtContent.getText().toString().trim();
        if (Helper.isEmpty(content)){
            ToastHelper.showToast("请输入问题描述");
            return;
        }
        showProgressDialog();
        File file = new File(mTempFilePath);
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("content",content);
        mPresenter.feedback(file,requestMap);
    }

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter();
    }


    @Override
    public void confirmSuccess(Object result) {
        ToastHelper.showToast("反馈成功");
        finish();
    }

    private void pickPhoto() {
        if (mPickDialog == null) {
            mPickDialog = new BottomMenuDialog.Builder(mContext)
                    .addMenu("拍照", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImageFromCamera();
                            mPickDialog.dismiss();
                        }
                    }).addMenu("我的相册选择", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImageFromGallery();
                            mPickDialog.dismiss();
                        }
                    }).create();
        }
        mPickDialog.show();
    }

    /**
     * 相册图片选取
     */
    private void pickImageFromGallery() {
        try {
            Intent intentFromGallery = new Intent();
            // 设置文件类型
            intentFromGallery.setType("image/*");
            intentFromGallery.setAction(Intent.ACTION_PICK);
            startActivityForResult(intentFromGallery, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照获取图片
     */
    private void pickImageFromCamera() {
        try {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            // 图片缓存的地址
            mTempFilePath = AppHelper.getBaseCachePath()
                    .concat(String.valueOf(System.currentTimeMillis()))
                    .concat(".png");
            File file = new File(mTempFilePath);
            Uri uri = Uri.fromFile(file);
            // 设置图片的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                // 相册
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    mTempFilePath = ImageUtils.saveBitMapToFile(mContext,"myPic.png",bitmap);
                    mIvImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                // 拍照
                File file = new File(mTempFilePath);
                if (!file.exists()) {
                    return;
                }
                try {
                    Bitmap bitmap = ImageUtils.getYaSuoBitmapFromImagePath(mTempFilePath,400,400);
                    if (bitmap!=null){
                        mIvImage.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
