package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 支付返回结果
 * Created by Mario on 2016/9/27.
 */
public class PayBean implements Serializable {
    @Expose
    private boolean success;
    @Expose
    private String message;
    @Expose
    private String codesender;
    @Expose
    private String merchantaccount;
    @Expose
    private String requestid;
    @Expose
    private String sign;
    @Expose
    private String order_no;
    @Expose
    private String amount;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodesender() {
        return codesender;
    }

    public void setCodesender(String codesender) {
        this.codesender = codesender;
    }

    public String getMerchantaccount() {
        return merchantaccount;
    }

    public void setMerchantaccount(String merchantaccount) {
        this.merchantaccount = merchantaccount;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PayBean{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", codesender='" + codesender + '\'' +
                ", merchantaccount='" + merchantaccount + '\'' +
                ", requestid='" + requestid + '\'' +
                ", sign='" + sign + '\'' +
                ", order_no='" + order_no + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
