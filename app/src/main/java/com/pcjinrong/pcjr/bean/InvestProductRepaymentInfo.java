package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 回款详情
 * Created by mario on 2017/4/19.
 */
public class InvestProductRepaymentInfo implements Serializable{
    @Expose
    private String term;
    @Expose
    private long estimated_time;
    @Expose
    private String estimated_capital;
    @Expose
    private String estimated_interest;
    @Expose
    private long actual_time;
    @Expose
    private String actual_capital;
    @Expose
    private String actual_interest;
    @Expose
    private int status;
    @Expose
    private int type;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public long getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(long estimated_time) {
        this.estimated_time = estimated_time;
    }

    public String getEstimated_interest() {
        return estimated_interest;
    }

    public void setEstimated_interest(String estimated_interest) {
        this.estimated_interest = estimated_interest;
    }

    public long getActual_time() {
        return actual_time;
    }

    public void setActual_time(long actual_time) {
        this.actual_time = actual_time;
    }

    public String getActual_capital() {
        return actual_capital;
    }

    public void setActual_capital(String actual_capital) {
        this.actual_capital = actual_capital;
    }

    public String getActual_interest() {
        return actual_interest;
    }

    public void setActual_interest(String actual_interest) {
        this.actual_interest = actual_interest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEstimated_capital() {
        return estimated_capital;
    }

    public void setEstimated_capital(String estimated_capital) {
        this.estimated_capital = estimated_capital;
    }
}
