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
}
