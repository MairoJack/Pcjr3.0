package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 *  提现取消
 *  Created by Mario on 2017/6/21上午8:49
 */
public class WithdrawCancel implements Serializable{
    @Expose
    private String id;
    @Expose
    private String fee;
    @Expose
    private String actual_amount;
    @Expose
    private long join_date;
    @Expose
    private String binkid;
    @Expose
    private BankCard bank_card;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(String actual_amount) {
        this.actual_amount = actual_amount;
    }

    public long getJoin_date() {
        return join_date;
    }

    public void setJoin_date(long join_date) {
        this.join_date = join_date;
    }

    public String getBinkid() {
        return binkid;
    }

    public void setBinkid(String binkid) {
        this.binkid = binkid;
    }


    public BankCard getBank_card() {
        return bank_card;
    }

    public void setBank_card(BankCard bank_card) {
        this.bank_card = bank_card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
