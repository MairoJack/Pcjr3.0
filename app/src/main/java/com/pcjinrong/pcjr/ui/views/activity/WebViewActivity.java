package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;

import butterknife.BindView;

/**
 * WebView
 * Created by Mario on 2016/5/24.
 */
public class WebViewActivity extends BaseToolbarActivity {
    @BindView(R.id.web_view) WebView webView;
    private ProgressDialog dialog;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.web_view;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在加载...");
        dialog.show();
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings
                .setUserAgentString(webSettings.getUserAgentString() + "NewpcjrApp");
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
                webView.loadUrl("javascript:appdeletehead()");
            }
        });
        url = intent.getStringExtra("url");
        setTitle(intent.getStringExtra("title"));
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }
        return false;
    }
}
