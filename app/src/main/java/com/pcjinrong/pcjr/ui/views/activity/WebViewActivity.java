package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pcjinrong.pcjr.App;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.JsInterface;
import com.pcjinrong.pcjr.utils.SPUtils;

import butterknife.BindView;

/**
 * WebView
 * Created by Mario on 2016/5/24.
 */
public class WebViewActivity extends BaseToolbarActivity {
    @BindView(R.id.web_view) WebView webView;
    private ProgressDialog dialog;
    private String url;
    private JsInterface jsInterface = new JsInterface();

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
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings
                .setUserAgentString(webSettings.getUserAgentString() + "NewpcjrApp");
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.addJavascriptInterface(jsInterface,"jsInterface");

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(".pcjr.com","access_token="+ SPUtils.getToken(App.getContext()).getAccess_token());
        if(intent.getIntExtra("assessType",0) == 1){
            cookieManager.setCookie(".pcjr.com","type=1");
        }else{
            cookieManager.setCookie(".pcjr.com","type=0");
        }

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, request);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
                webView.loadUrl("javascript:appdeletehead()");
                jsInterface.setWvClientClickListener(new webViewClick());
                CookieManager cookieManager = CookieManager.getInstance();
                String c = cookieManager.getCookie(".pcjr.com");
                System.out.println("oldCookie:"+c);
            }

        });


        url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        if(null == title || title.equals("")){
            title = "皮城金融";
        }
        setTitle(title);
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

    class webViewClick implements JsInterface.WvClientClickListener{

        @Override
        public void wvHasClickEnvent() {
            finish();
        }
    }
}
