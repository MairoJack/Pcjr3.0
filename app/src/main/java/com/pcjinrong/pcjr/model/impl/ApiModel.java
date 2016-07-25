package com.pcjinrong.pcjr.model.impl;

import com.pcjinrong.pcjr.api.RetrofitManager;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.model.IApiModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public class ApiModel implements IApiModel {

    private static final ApiModel mInstance = new ApiModel();


    public static ApiModel getInstance() {
        return mInstance;
    }


    private ApiModel() {}


    @Override
    public Observable<BaseBean<List<Product>>> getIndexProductList() {
        return RetrofitManager.getInstance().getApiService().getIndexProductList();
    }
}
