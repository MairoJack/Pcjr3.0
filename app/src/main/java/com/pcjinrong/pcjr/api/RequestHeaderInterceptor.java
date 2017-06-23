package com.pcjinrong.pcjr.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Mario on 2016/7/29.
 */
public class RequestHeaderInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        Request authorised = originalRequest.newBuilder()
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("User-Agent", "Android-" + currentApiVersion)
                .build();
        return chain.proceed(authorised);
    }
}
