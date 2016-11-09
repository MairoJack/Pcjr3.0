package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class BankInfo implements Serializable{
    @Expose
    private String id;
    @Expose
    private String card_top;
    @Expose
    private String card_last;
    @Expose
    private Integer bank;
    @Expose
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
