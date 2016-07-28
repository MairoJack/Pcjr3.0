package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Mario on 2016/7/28.
 */
public class MemberIndex implements Serializable {
    @Expose
    private String user_name;
    @Expose
    private String available_balance;
    @Expose
    private String interest;
    @Expose
    private String total;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(String available_balance) {
        this.available_balance = available_balance;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
