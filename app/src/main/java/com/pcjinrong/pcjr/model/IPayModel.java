package com.pcjinrong.pcjr.model;

import com.pcjinrong.pcjr.bean.PayBean;

import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public interface IPayModel {

    Observable<PayBean> bind_card(String memberID, String cardNo, String phone);

    Observable<PayBean> confirm_bind_card(String requestId, String validatecode);

    Observable<PayBean> pay(String memberID, String cardID, String amount);

    Observable<PayBean> send_verify_code(String memberID, String orderNo);

    Observable<PayBean> confirm_pay(String memberID, String orderNo, String validateCode);

    Observable<PayBean> check_card(String cardNo);

}
