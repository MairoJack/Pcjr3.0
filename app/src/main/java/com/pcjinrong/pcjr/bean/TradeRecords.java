package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 交易记录实体类
 * Created by Mario on 2016/5/26.
 */
public class TradeRecords {
    @Expose
    private int type;
    @Expose
    private String remark;
    @Expose
    private int direction;
    @Expose
    private String amount;
    @Expose
    private String balance;
    @Expose
    private String fee;
    @Expose
    private long join_date;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public long getJoin_date() {
        return join_date;
    }

    public void setJoin_date(long join_date) {
        this.join_date = join_date;
    }

}
