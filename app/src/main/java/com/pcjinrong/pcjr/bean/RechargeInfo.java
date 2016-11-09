package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 充值信息
 * Created by Mario on 2016/5/31.
 */
public class RechargeInfo implements Serializable{
    @Expose
    private String available_balance;
    @Expose
    private String member_id;
    @Expose
    private String realname;
    @Expose
    private String mobile;
    @Expose
    private Integer finish_assessment;
    @Expose
    private BankInfo bank_info;

    public String getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(String available_balance) {
        this.available_balance = available_balance;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
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

    public Integer isFinish_assessment() {
        return finish_assessment;
    }

    public void setFinish_assessment(Integer finish_assessment) {
        this.finish_assessment = finish_assessment;
    }

    public BankInfo getBank_info() {
        return bank_info;
    }

    public void setBank_info(BankInfo bank_info) {
        this.bank_info = bank_info;
    }
}

