package com.android.mb.movie.view;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseMvpActivity;
import com.android.mb.movie.entity.Avatar;
import com.android.mb.movie.entity.CurrentUser;
import com.android.mb.movie.entity.UserBean;
import com.android.mb.movie.presenter.AccountPresenter;
import com.android.mb.movie.utils.AppHelper;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.view.interfaces.IAccountView;
import com.android.mb.movie.widget.BottomMenuDialog;
import com.android.mb.movie.widget.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 * Created by cgy on 2018\8\20 0020.
 */

public class AccountActivity extends BaseMvpActivity<AccountPresenter,IAccountView> implements IAccountView, View.OnClickListener{

    private TextView mTvNickname;
    private TextView mTvSex;
    private TextView mTvPhone;
    private CircleImageView mIvAvatar;
    private BottomMenuDialog pickDialog;
    private String mTempFilePath = AppHelper.getBaseCachePath()
            .concat(String.valueOf(System.currentTimeMillis())).concat(".png");

    @Override
    protected void loadIntent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_manage;
    }

    @Override
    protected void initTitle() {
        setTitleText("账号管理");
    }

    @Override
    protected void bindViews() {
        mIvAvatar = findViewById(R.id.iv_avatar);
        mTvNickname = findViewById(R.id.tv_nickname);
        mTvSex = findViewById(R.id.tv_sex);
        mTvPhone = findViewById(R.id.tv_phone);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initUserInfo();
    }

    @Override
    protected void setListener() {
        findViewById(R.id.btn_head).setOnClickListener(this);
        findViewById(R.id.btn_nickname).setOnClickListener(this);
        findViewById(R.id.btn_sex).setOnClickListener(this);
        findViewById(R.id.btn_phone).setOnClickListener(this);
        findViewById(R.id.btn_change_pwd).setOnClickListener(this);
        findViewById(R.id.tv_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_head){
            pickPhoto();
        }else if (id == R.id.btn_nickname){
            new MaterialDialog.Builder(mContext).title("请输入昵称").inputType(InputType.TYPE_CLASS_TEXT)
                    .inputRangeRes(2, 20, R.color.base_brown).input("请输入昵称", "", new MaterialDialog.InputCallback() {
                @Override
                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                }
            }).positiveText("确定").negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (dialog.getInputEditText()!=null && Helper.isNotEmpty(dialog.getInputEditText().getText().toString())){
                        String nickName = dialog.getInputEditText().getText().toString();
                        mTvNickname.setText(nickName);
                        Map<String, Object> requestMap = new HashMap<>();
                        requestMap.put("nickname",nickName);
                        requestMap.put("userid",CurrentUser.getInstance().getUserid());
                        mPresenter.updateInfo(requestMap);
                    }
                }
            }).show();
        }else if (id == R.id.btn_sex){
            List<String> titleList = new ArrayList<>();
            titleList.add("男");
            titleList.add("女");
            new MaterialDialog.Builder(this)
                    .title("请选择性别")
                    .items(titleList)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            mTvSex.setText(text);
                            Map<String, Object> requestMap = new HashMap<>();
                            requestMap.put("sexText",text);
                            requestMap.put("userid",CurrentUser.getInstance().getUserid());
                            mPresenter.updateInfo(requestMap);
                        }
                    })
                    .show();
        }else if (id == R.id.btn_phone){
            new MaterialDialog.Builder(mContext).title("请输入手机号").inputType(InputType.TYPE_CLASS_TEXT)
                    .inputRangeRes(11, 11, R.color.base_brown).input("请输入手机号", "", new MaterialDialog.InputCallback() {
                @Override
                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                }
            }).positiveText("确定").negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (dialog.getInputEditText()!=null && Helper.isNotEmpty(dialog.getInputEditText().getText().toString())){
                        String phone = dialog.getInputEditText().getText().toString();
                        mTvPhone.setText(phone);
                        Map<String, Object> requestMap = new HashMap<>();
                        requestMap.put("phone",phone);
                        requestMap.put("userid",CurrentUser.getInstance().getUserid());
                        mPresenter.updateInfo(requestMap);
                    }
                }
            }).show();
        }else if (id == R.id.btn_change_pwd){

        }else if (id == R.id.tv_exit){
            new MaterialDialog.Builder(mContext).title("提示").content("确定要退出当前账号?")
                    .positiveText("确定").negativeText("取消").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    CurrentUser.getInstance().loginOut();
                    finish();
                }
            }).show();
        }
    }


    private void initUserInfo(){
        if (CurrentUser.getInstance()!=null && CurrentUser.getInstance().isLogin()){
            mTvNickname.setText(CurrentUser.getInstance().getNickname());
            mTvPhone.setText(CurrentUser.getInstance().getPhone());
            mTvSex.setText(CurrentUser.getInstance().getSexText());
            ImageUtils.displayAvatar(mContext,CurrentUser.getInstance().getPhone(),mIvAvatar);
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
                cropPickedImage(data.getData());
                break;
            case 2:
                // 拍照
                File file = new File(mTempFilePath);
                if (!file.exists()) {
                    return;
                }
                cropPickedImage(Uri.parse("file://".concat(mTempFilePath)));
                break;
            case 3:
                // 上传图片
                File avatarFile = new File(mTempFilePath);
                mPresenter.uploadAvatar(avatarFile);
                showProgressDialog();
                break;
            default:
                break;
        }
    }

    private void pickPhoto() {
        if (pickDialog == null) {
            pickDialog = new BottomMenuDialog.Builder(mContext)
                    .addMenu("拍照", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImageFromCamera();
                            pickDialog.dismiss();
                        }
                    }).addMenu("我的相册选择", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImageFromGallery();
                            pickDialog.dismiss();
                        }
                    }).create();
        }
        pickDialog.show();
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

    /**
     * 裁剪选取的图片
     *
     * @param uri
     */
    private void cropPickedImage(Uri uri) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");// 可裁剪
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("scale", true);

            // 图片缓存的地址
            mTempFilePath = AppHelper.getBaseCachePath()
                    .concat(String.valueOf(System.currentTimeMillis()))
                    .concat(".png");
            File file = new File(mTempFilePath);
            Uri uriCache = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriCache);
            intent.putExtra("outputFormat", "JPEG");// 图片格式
            intent.putExtra("noFaceDetection", true);// 取消人脸识别
            intent.putExtra("return-data", false);
            startActivityForResult(intent, 3);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected AccountPresenter createPresenter() {
        return new AccountPresenter();
    }

    @Override
    public void getSuccess(UserBean result) {

    }

    @Override
    public void updateInfo(UserBean result) {

    }

    @Override
    public void uploadAvatar(Avatar result) {
        if (result!=null){
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("avatar_url",result.getAvatar_org());
            requestMap.put("userid",CurrentUser.getInstance().getUserid());
            mPresenter.updateInfo(requestMap);
        }
    }


}
