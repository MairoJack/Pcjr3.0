package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 产品投资记录
 * Created by Mario on 2016/6/6.
 */
public class ProductTradingRecord {
    @Expose
    private String member_name;
    @Expose
    private String amount;
    @Expose
    private long join_date;

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getJoin_date() {
        return join_date;
    }

    public void setJoin_date(long join_date) {
        this.join_date = join_date;
    }
}
