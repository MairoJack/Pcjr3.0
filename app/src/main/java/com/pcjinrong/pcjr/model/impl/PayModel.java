package com.pcjinrong.pcjr.model.impl;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.api.RetrofitManager;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.model.IPayModel;
import com.pcjinrong.pcjr.utils.MD5Utils;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
    public Observable<PayBean> bind_card(String memberID, String cardNo, String phone){
        Map<String,String> map = new TreeMap<>();
        map.put("memberID",memberID);
        map.put("cardNo",cardNo);
        map.put("phone",phone);
        return RetrofitManager.getInstance().getPayService().bind_card(memberID, cardNo, phone,getToken(map));
    }

    @Override
    public Observable<PayBean> confirm_bind_card(String requestId, String validatecode) {
        Map<String,String> map = new TreeMap<>();
        map.put("requestId",requestId);
        map.put("validateCode",validatecode);
        return RetrofitManager.getInstance().getPayService().confirm_bind_card(requestId, validatecode,getToken(map));
    }

    @Override
    public Observable<PayBean> pay(String memberID, String cardID,String amount) {
        Map<String,String> map = new TreeMap<>();
        map.put("memberID",memberID);
        map.put("cardID",cardID);
        map.put("amount",amount);
        return RetrofitManager.getInstance().getPayService().pay(memberID, cardID,amount,getToken(map));
    }

    @Override
    public Observable<PayBean> send_verify_code(String memberID, String orderNo) {
        Map<String,String> map = new TreeMap<>();
        map.put("memberID",memberID);
        map.put("orderNo",orderNo);
        return RetrofitManager.getInstance().getPayService().send_verify_code(memberID, orderNo,getToken(map));
    }

    @Override
    public Observable<PayBean> confirm_pay(String memberID, String orderNo, String validateCode) {
        Map<String,String> map = new TreeMap<>();
        map.put("memberID",memberID);
        map.put("orderNo",orderNo);
        map.put("validateCode",validateCode);
        return RetrofitManager.getInstance().getPayService().confirm_pay(memberID, orderNo,validateCode,getToken(map));
    }

    @Override
    public Observable<PayBean> check_card(String cardNo) {
        Map<String,String> map = new TreeMap<>();
        map.put("cardNo",cardNo);
        return RetrofitManager.getInstance().getPayService().check_card(cardNo,getToken(map));
    }

    private String getToken(Map<String,String> map){
        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        String str = "";
        while (iterator.hasNext()) {
            String key = iterator.next();
            str += map.get(key);
        }
        str += ApiConstant.SUFFIX;
        return MD5Utils.MD5(str).toLowerCase();
    }
}
