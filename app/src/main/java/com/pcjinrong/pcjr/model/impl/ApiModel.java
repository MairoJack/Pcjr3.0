package com.pcjinrong.pcjr.model.impl;

import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.api.RetrofitManager;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Empty;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.ProductTradingRecord;
import com.pcjinrong.pcjr.bean.RechargeDifficult;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.model.IApiModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public class ApiModel implements IApiModel {

    private static final ApiModel mInstance = new ApiModel();


    public static ApiModel getInstance() {
        return mInstance;
    }


    private ApiModel() {
    }


    @Override
    public Observable<Token> getAccessToken(String username, String password) {
        return RetrofitManager.getInstance().getApiService().getAccessToken("password", username, password, ApiConstant.CLIENT_ID, ApiConstant.CLIENT_SECRET);
    }

    @Override
    public Observable<BaseBean> register(String name, String password, String recommend_person) {
        return RetrofitManager.getInstance().getApiService().register(name, password, recommend_person);
    }

    @Override
    public Observable<Token> refreshToken(String refresh_token) {
        return RetrofitManager.getInstance().getApiService().refreshToken("refresh_token", refresh_token, ApiConstant.CLIENT_ID, ApiConstant.CLIENT_SECRET);
    }

    @Override
    public Observable<BaseBean<List<Product>>> getIndexProductList() {
        return RetrofitManager.getInstance().getApiService().getIndexProductList();
    }

    @Override
    public Observable<IndexFocusInfo> getIndexFocusInfo() {
        return RetrofitManager.getInstance().getApiService().getIndexFocusInfo();
    }

    @Override
    public Observable<BaseBean<List<Product>>> getInvestProductList(int type, int page, int page_size) {
        return RetrofitManager.getInstance().getApiService().getInvestProductList(type, page, page_size);
    }

    @Override
    public Observable<BaseBean<Product>> getProductDetail(String id) {
        return RetrofitManager.getInstance().getApiService().getProductDetail(id);
    }

    @Override
    public Observable<BaseBean<List<ProductTradingRecord>>> getProductTradingRecordList(String id, int page, int page_size) {
        return RetrofitManager.getInstance().getApiService().getProductTradingRecordList(id, page, page_size);
    }

    @Override
    public Observable<BaseBean<List<BankCard>>> getBankCardList() {
        return RetrofitManager.getInstance().getApiService().getBankCardList();
    }

    @Override
    public Observable<RechargeDifficult> getRechargeDifficult() {
        return RetrofitManager.getInstance().getApiService().getRechargeDifficult();
    }

    @Override
    public Observable<Response<Empty>> getCurrentTime() {
        return RetrofitManager.getInstance().getApiService().getCurrentTime();
    }


}
