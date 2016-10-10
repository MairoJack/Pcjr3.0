package com.pcjinrong.pcjr.model.impl;

import com.pcjinrong.pcjr.api.RetrofitManager;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.model.IPayModel;
import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public class PayModel implements IPayModel {

    private static final PayModel mInstance = new PayModel();


    public static PayModel getInstance() {
        return mInstance;
    }


    private PayModel() {
    }


    @Override
    public Observable<PayBean> bind_card(String memberID, String cardNo, String phone) {
        return RetrofitManager.getInstance().getPayService().bind_card(memberID, cardNo, phone);
    }

    @Override
    public Observable<PayBean> confirm_bind_card(String requestId, String validatecode) {
        return RetrofitManager.getInstance().getPayService().confirm_bind_card(requestId, validatecode);
    }

    @Override
    public Observable<PayBean> pay(String memberID, String cardID,String amount) {
        return RetrofitManager.getInstance().getPayService().pay(memberID, cardID,amount);
    }

    @Override
    public Observable<PayBean> send_verify_code(String memberID, String orderNo) {
        return RetrofitManager.getInstance().getPayService().send_verify_code(memberID, orderNo);
    }

    @Override
    public Observable<PayBean> confirm_pay(String memberID, String orderNo, String validateCode) {
        return RetrofitManager.getInstance().getPayService().confirm_pay(memberID, orderNo,validateCode);
    }

    @Override
    public Observable<PayBean> check_card(String cardNo) {
        return RetrofitManager.getInstance().getPayService().check_card(cardNo);
    }
}
