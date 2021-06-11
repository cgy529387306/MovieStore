package com.android.mb.movie.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.mb.movie.R;
import com.android.mb.movie.base.BaseWebViewActivity;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.utils.ImageUtils;
import com.android.mb.movie.utils.NavigationHelper;
import com.android.mb.movie.utils.ProjectHelper;

public class AdvertDialogFragment extends DialogFragment {

    private ImageView mIvAdvert,mIvClose;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_advert, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String imageUrl = getArguments().getString("imageUrl");
        String redirectUrl = getArguments().getString("redirectUrl");
        mIvAdvert = view.findViewById(R.id.iv_advert);
        mIvClose = view.findViewById(R.id.iv_close);
        ImageUtils.loadImageUrlLight(mIvAdvert,imageUrl);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mIvAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(ProjectConstants.KEY_WEB_DETAIL_URL,redirectUrl);
                NavigationHelper.startActivity(getActivity(), BaseWebViewActivity.class,bundle,false);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.CENTER;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
        getDialog().setCanceledOnTouchOutside(false);
    }

    public static AdvertDialogFragment newInstance(String imageUrl, String redirectUrl){
        AdvertDialogFragment fragment = new AdvertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", imageUrl);
        bundle.putString("redirectUrl", redirectUrl);
        fragment.setArguments(bundle);
        return fragment;
    }
}
