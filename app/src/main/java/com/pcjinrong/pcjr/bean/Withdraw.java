package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 提现信息
 * Created by Mario on 2016/5/31.
 */
public class Withdraw implements Serializable{
    @Expose
    private String available_balance;
    @Expose
    private String free_withdraw;
    @Expose
    private String realname;
    @Expose
    private String mobile;

    public String getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(String available_balance) {
        this.available_balance = available_balance;
    }

    public String getFree_withdraw() {
        return free_withdraw;
    }

    public void setFree_withdraw(String free_withdraw) {
        this.free_withdraw = free_withdraw;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
