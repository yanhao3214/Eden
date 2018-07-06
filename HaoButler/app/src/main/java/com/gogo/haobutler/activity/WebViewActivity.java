package com.gogo.haobutler.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gogo.haobutler.R;
import com.gogo.haobutler.activity.base.BaseActivity;

/**
 * @author: 闫昊
 * @date: 2018/6/15 0015
 * @function: 网页展示Activity
 */
public class WebViewActivity extends BaseActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_web_layout);
        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mWebView = findViewById(R.id.web_view);
        mProgressBar = findViewById(R.id.progress_bar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        @SuppressWarnings("ConstantConditions")
        String title = bundle.getString("title");
        final String url = bundle.getString("url");
        //设置标题
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(title);
        configWebView(mWebView, url);
        mWebView.loadUrl(url);
    }

    /**
     * 配置WebView参数
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void configWebView(WebView webView, String url) {
        WebSettings settings = webView.getSettings();
        //允许使用JS
        settings.setJavaScriptEnabled(true);
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //显示缩放按钮
        settings.setDisplayZoomControls(true);
        //不使用缓存，仅从网络加载
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WeChatWebViewClient(url));
        webView.setWebChromeClient(new WeChatWebChromeClient());
    }

    private class WeChatWebViewClient extends WebViewClient {
        private String url;

        WeChatWebViewClient(String url) {
            this.url = url;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }

        /**
         * 截获动作，本地显示，而不跳转到浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(url);
            return true;
        }
    }

    private class WeChatWebChromeClient extends WebChromeClient {

        /**
         * 实时更新进度条
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
        }
    }

    /**
     * 支持网页返回
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
    }
}
