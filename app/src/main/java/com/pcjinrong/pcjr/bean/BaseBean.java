package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Mario on 2016/7/25.
 */
public class BaseBean<T> implements Serializable {
    @Expose
    private boolean success;
    @Expose
    private String message;
    @Expose
    private long current_time;
    @Expose
    private String realName;
    @Expose
    private int invest_ticket_num;

    @Expose
    private T data;

    @Expose
    private T info;

    @Expose
    private Pager pager;

    public long getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(long current_time) {
        this.current_time = current_time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getInvest_ticket_num() {
        return invest_ticket_num;
    }

    public void setInvest_ticket_num(int invest_ticket_num) {
        this.invest_ticket_num = invest_ticket_num;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
