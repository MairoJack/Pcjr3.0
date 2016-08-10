package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 红包
 * Created by Mario on 2016/6/2.
 */
public class RedPacket {
    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String amount;
    @Expose
    private String is_used;
    @Expose
    private long join_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIs_used() {
        return is_used;
    }

    public void setIs_used(String is_used) {
        this.is_used = is_used;
    }

    public long getJoin_date() {
        return join_date;
    }

    public void setJoin_date(long join_date) {
        this.join_date = join_date;
    }
}
