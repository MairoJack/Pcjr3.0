package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 银行卡
 * Created by Mario on 2016/5/31.
 */
public class BankCard {
    @Expose
    private String id;
    @Expose
    private String card_no;
    @Expose
    private String bank;
    @Expose
    private String name;
    @Expose
    private String card_top;
    @Expose
    private String card_last;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_top() {
        return card_top;
    }

    public void setCard_top(String card_top) {
        this.card_top = card_top;
    }

    public String getCard_last() {
        return card_last;
    }

    public void setCard_last(String card_last) {
        this.card_last = card_last;
    }
}
