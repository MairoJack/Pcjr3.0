package com.pcjinrong.pcjr.api;

import com.google.gson.JsonObject;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.ProductTradingRecord;
import com.pcjinrong.pcjr.bean.RechargeDifficult;
import com.pcjinrong.pcjr.bean.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 不需要auth认证 api
 * Created by Mario on 2016/7/25.
 */
public interface ApiService {

    /**
     * 获取访问token(用户登录)
     *
     * @param grant_type
     * @param username
     * @param password
     * @param client_id
     * @param client_secret
     * @return
     */
    @FormUrlEncoded
    @POST("oauth/access_token")
    Observable<Token> getAccessToken(@Field("grant_type") String grant_type, @Field("username") String username, @Field("password") String password, @Field("client_id") String client_id, @Field("client_secret") String client_secret);


    /**
     * 刷新token
     *
     * @param grant_type
     * @param refresh_token
     * @param client_id
     * @param client_secret
     * @return
     */
    @FormUrlEncoded
    @POST("oauth/access_token")
    Observable<Token> refreshToken(@Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token, @Field("client_id") String client_id, @Field("client_secret") String client_secret);


    /**
     * 获取首页焦点图和公告
     *
     * @return
     */
    @GET("index_focus_info")
    Observable<IndexFocusInfo> getIndexFocusInfo();

    /**
     * 获取首页产品信息
     *
     * @return
     */
    @GET("index_product_list")
    Observable<BaseBean<List<Product>>> getIndexProductList();

    /**
     * 获取产品列表
     *
     * @param type:     0 全部；1 销售中；2 销售结束；3 项目结束
     * @param page
     * @param page_size
     * @return
     */
    @GET("invest_product_list")
    Observable<BaseBean<List<Product>>> getInvestProductList(@Query("type") int type, @Query("page") int page, @Query("page_size") int page_size);

    /**
     * 获取产品详情
     *
     * @param id
     * @return
     */
    @GET("product_detail")
    Observable<BaseBean<Product>> getProductDetail(@Query("id") String id);

    /**
     * 获取产品投资记录
     *
     * @param id
     * @return
     */
    @GET("product_trading_record_list")
    Observable<BaseBean<List<ProductTradingRecord>>> getProductTradingRecordList(@Query("id") String id, @Query("page") int page, @Query("page_size") int page_size);

    /**
     * 用户注册
     *
     * @param name
     * @param password
     * @param recommend_person
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Observable<BaseBean> register(@Field("name") String name, @Field("password") String password, @Field("recommend_person") String recommend_person);


    /**
     * 获取银行卡信息
     */
    @GET("bank_card_list")
    Observable<BaseBean<List<BankCard>>> getBankCardList();

    /**
     * 获取版本信息
     */
    @GET("androidVersion/getNewstVersion")
    Call<JsonObject> getNewstVersion();

    /**
     * 获取充值难度
     */
    @GET("recharge_difficult")
    Observable<RechargeDifficult> getRechargeDifficult();

}
