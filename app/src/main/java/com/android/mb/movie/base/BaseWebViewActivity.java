package com.android.mb.movie.base;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.mb.movie.R;
import com.android.mb.movie.constants.ProjectConstants;
import com.android.mb.movie.utils.Helper;
import com.android.mb.movie.utils.NetworkHelper;


/**
 * webview基类
 * @author cgy
 *
 */
public class BaseWebViewActivity extends BaseActivity{
	/**
	 * WebView
	 */
	private WebView mWebView;
	private String mTitle = "详情页";
	private String mUrl;

	@Override
	protected void loadIntent() {
		mUrl = getIntent().getStringExtra(ProjectConstants.KEY_WEB_DETAIL_URL);
		mTitle = getIntent().getStringExtra(ProjectConstants.KEY_WEB_DETAIL_TITLE);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.common_web_detail;
	}

	@Override
	protected void initTitle() {
	}

	@Override
	protected void bindViews() {
        mWebView = findViewById(R.id.web_detail);
        if (NetworkHelper.isNetworkAvailable(this)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //点击链接由自己处理，而不是新开Android的系统browser中响应该链接。
        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl(mUrl);
	}

	@Override
	protected void processLogic(Bundle savedInstanceState) {

	}

	@Override
	protected void setListener() {

	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
		}else {
			finish();
		}
	}



    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (Helper.isNotEmpty(view.getTitle())){
                setTitleText(view.getTitle());
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            mWebView.loadUrl("about:blank");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return TextUtils.isEmpty(url) || super.shouldOverrideUrlLoading(mWebView, url);
        }
    };
}
