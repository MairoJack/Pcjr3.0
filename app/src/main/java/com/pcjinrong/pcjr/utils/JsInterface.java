package com.pcjinrong.pcjr.utils;

import android.webkit.JavascriptInterface;

/**
 * Created by Mario on 2016/10/25.
 */

public class JsInterface {
    public interface WvClientClickListener{
        void wvHasClickEnvent();
    }

    private WvClientClickListener wvEnventPro = null;

    public void setWvClientClickListener(WvClientClickListener listener){
        wvEnventPro = listener;
    }

    @JavascriptInterface
    public void javaFunction(){
        if(wvEnventPro != null){
            wvEnventPro.wvHasClickEnvent();
        }
    }
}
