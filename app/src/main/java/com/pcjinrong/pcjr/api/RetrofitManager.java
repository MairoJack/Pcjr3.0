package com.pcjinrong.pcjr.api;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitManager
 * Created by Mario on 2016/7/25.
 */
public class RetrofitManager {

    private static RetrofitManager mInstance;

    private ApiService apiService;
    private OAuthService authService;
    private PayService payService;

    public static RetrofitManager getInstance() {
        if (mInstance == null) mInstance = new RetrofitManager();
        return mInstance;
    }

    private RetrofitManager() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(10, TimeUnit.SECONDS);
        client.readTimeout(10, TimeUnit.SECONDS);
        client.addInterceptor(new RequestHeaderInterceptor());
        Retrofit apiRetrofit = new Retrofit.Builder().baseUrl(ApiConstant.BASE_URL)
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        Retrofit payRetrofit = new Retrofit.Builder().baseUrl(ApiConstant.PAY_URL)
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        client.interceptors().clear();
        client.addInterceptor(new RequestInterceptor());

        Retrofit authRetrofit = new Retrofit.Builder().baseUrl(ApiConstant.BASE_URL)
                .addCallAdapterFactory(
                        RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();



        this.apiService = apiRetrofit.create(ApiService.class);
        this.authService = authRetrofit.create(OAuthService.class);
        this.payService = payRetrofit.create(PayService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

    public OAuthService getAuthService() {
        return authService;
    }

    public PayService getPayService() { return payService;}
}
