package com.pcjinrong.pcjr.model;

import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Empty;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.ProductTradingRecord;
import com.pcjinrong.pcjr.bean.RechargeDifficult;
import com.pcjinrong.pcjr.bean.Token;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public interface IApiModel {
    Observable<Token> getAccessToken(String username, String password);

    Observable<BaseBean> register(String name, String password, String recommend_person);

    Observable<Token> refreshToken(String refresh_token);

    Observable<BaseBean<List<Product>>> getIndexProductList();

    Observable<IndexFocusInfo> getIndexFocusInfo();

    Observable<BaseBean<List<Product>>> getInvestProductList(int type, int page, int page_size);

    Observable<BaseBean<Product>> getProductDetail(String id);

    Observable<BaseBean<List<ProductTradingRecord>>> getProductTradingRecordList(String id, int page, int page_size);

    Observable<BaseBean<List<BankCard>>> getBankCardList();

    Observable<RechargeDifficult> getRechargeDifficult();

    Observable<Response<Empty>> getCurrentTime();

}
