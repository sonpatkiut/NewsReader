package com.example.icsssd358.newsviewer.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.icsssd358.newsviewer.R;
import com.example.icsssd358.newsviewer.Utils.AppConstants;
import com.example.icsssd358.newsviewer.Utils.EventBus;
import com.example.icsssd358.newsviewer.Utils.NetworkUtils;
import com.example.icsssd358.newsviewer.databinding.ActivityWebBinding;
import com.example.icsssd358.newsviewer.listeners.BaseCallback;
import com.example.icsssd358.newsviewer.viewmodel.WebViewModel;
import com.example.icsssd358.newsviewer.BR;

import javax.inject.Inject;

public class WebActivity extends BaseActivity<ActivityWebBinding, WebViewModel> implements BaseCallback{

    @Inject
    WebViewModel mWebViewModel;

    @Inject
    EventBus eventBus;

    ActivityWebBinding mActivityWebBinding;

    String destURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityWebBinding = getViewDataBinding();
        mWebViewModel.setCallback(this);

        destURL = getIntent().getStringExtra(AppConstants.NEWS_URL);
        initWebView();
    }

    private void initWebView(){
        if(NetworkUtils.isNetworkConnected(this)){
            mActivityWebBinding.webView.getSettings().setSupportZoom(true);
            //mActivityWebBinding.webView.getSettings().setBuiltInZoomControls(true);
            mActivityWebBinding.webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mActivityWebBinding.webView.setScrollbarFadingEnabled(true);
            mActivityWebBinding.webView.getSettings().setLoadsImagesAutomatically(true);
            mActivityWebBinding.webView.getSettings().setDomStorageEnabled(true);
            mActivityWebBinding.webView.getSettings().setAppCacheEnabled(true);
            mActivityWebBinding.webView.getSettings().setAppCacheMaxSize(1024*1024*8);

            String appCachePath = this.getCacheDir().getAbsolutePath();
            mActivityWebBinding.webView.getSettings().setAppCachePath(appCachePath);
            mActivityWebBinding.webView.getSettings().setAllowFileAccess(true);

            mActivityWebBinding.webView.getSettings().setJavaScriptEnabled(true);

            mActivityWebBinding.webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            mActivityWebBinding.webView.setWebChromeClient(new WebChromeClient(){
                public void onProgressChanged(WebView view, int progress){

                }
            });
            mActivityWebBinding.webView.setWebViewClient(new MyWebViewClient());
            mActivityWebBinding.webView.loadUrl(destURL);
        }else{
            showSnackBar(this, "You are viewing in offline mode");
            mActivityWebBinding.webView.getSettings().setSupportZoom(true);
            //mActivityWebBinding.webView.getSettings().setBuiltInZoomControls(true);
            mActivityWebBinding.webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mActivityWebBinding.webView.setScrollbarFadingEnabled(true);
            mActivityWebBinding.webView.getSettings().setLoadsImagesAutomatically(true);
            mActivityWebBinding.webView.getSettings().setDomStorageEnabled(true);
            mActivityWebBinding.webView.getSettings().setAppCacheEnabled(true);
            mActivityWebBinding.webView.getSettings().setAppCacheMaxSize(1024*1024*8);

            String appCachePath = this.getCacheDir().getAbsolutePath();
            mActivityWebBinding.webView.getSettings().setAppCachePath(appCachePath);
            mActivityWebBinding.webView.getSettings().setAllowFileAccess(true);

            mActivityWebBinding.webView.getSettings().setJavaScriptEnabled(true);
            mActivityWebBinding.webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            mActivityWebBinding.webView.setWebViewClient(new MyWebViewClient());
            mActivityWebBinding.webView.loadUrl(destURL);
        }

    }

    public void showSnackBar(Activity activity, String message){
        View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public WebViewModel getViewModel() {
        return mWebViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        return R.layout.activity_web;
    }

    @Override
    public void makeRequest() {

    }

    @Override
    public void updateUIForFailedResponse(String msg) {

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void handleNetworkError() {

    }

    @Override
    public void navigateToNextActivity(Object object) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mActivityWebBinding.webView.canGoBack()) {
            mActivityWebBinding.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(NetworkUtils.isNetworkConnected(WebActivity.this)){
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }
    }
}
