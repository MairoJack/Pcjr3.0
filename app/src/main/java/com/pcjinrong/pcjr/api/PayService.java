package com.pcjinrong.pcjr.api;

import com.pcjinrong.pcjr.bean.PayBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 快捷支付 api
 * Created by Mario on 2016/9/27.
 */
public interface PayService {

    /**
     * 绑卡
     *
     * @param memberID
     * @param cardNo
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("bind_card")
    Observable<PayBean> bind_card(@Field("memberID") String memberID, @Field("cardNo") String cardNo, @Field("phone") String phone, @Field("token") String token);

    /**
     * 确认绑卡
     *
     * @param requestId
     * @param validateCode
     * @return
     */
    @FormUrlEncoded
    @POST("confirm_bind_card")
    Observable<PayBean> confirm_bind_card(@Field("requestId") String requestId, @Field("validateCode") String validateCode, @Field("token") String token);

    /**
     * 支付
     *
     * @param memberID
     * @param cardID
     * @param amount
     * @return
     */
    @FormUrlEncoded
    @POST("pay")
    Observable<PayBean> pay(@Field("memberID") String memberID, @Field("cardID") String cardID, @Field("amount") String amount, @Field("token") String token);

    /**
     * 支付验证码
     *
     * @param memberID
     * @param orderNo
     * @return
     */
    @FormUrlEncoded
    @POST("send_verify_code")
    Observable<PayBean> send_verify_code(@Field("memberID") String memberID, @Field("orderNo") String orderNo, @Field("token") String token);

    /**
     * 确认支付
     *
     * @param memberID
     * @param orderNo
     * * @param validateCode
     * @return
     */
    @FormUrlEncoded
    @POST("confirm_pay")
    Observable<PayBean> confirm_pay(@Field("memberID") String memberID, @Field("orderNo") String orderNo, @Field("validateCode") String validateCode, @Field("token") String token);

    /**
     * 检查卡号
     *
     * @param cardNo
     * @return
     */
    @FormUrlEncoded
    @POST("check_card")
    Observable<PayBean> check_card(@Field("cardNo") String cardNo, @Field("token") String token);

}
