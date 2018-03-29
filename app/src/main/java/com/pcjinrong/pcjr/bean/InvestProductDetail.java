package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 投资产品详情
 * Created by mario on 2017/4/19.
 */

public class InvestProductDetail implements Serializable{
    @Expose
    private String name;
    @Expose
    private String amount;
    @Expose
    private long join_date;
    @Expose
    private int is_preview_repayment;
    @Expose
    private int repayment;
    @Expose
    private String pay_interest_day;
    @Expose
    private String min_repayment_date;
    @Expose
    private int finish_preview_repayment;
    @Expose
    private long preview_repayment_date;
    @Expose
    private String month;
    @Expose
    private long pub_date;
    @Expose
    private long value_date;
    @Expose
    private long deadline;
    @Expose
    private int status;
    @Expose
    private String year_income;
    @Expose
    private String interest_year_income;
    @Expose
    private String estimated_amount;
    @Expose
    private String actual_amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getIs_preview_repayment() {
        return is_preview_repayment;
    }

    public void setIs_preview_repayment(int is_preview_repayment) {
        this.is_preview_repayment = is_preview_repayment;
    }

    public String getPay_interest_day() {
        return pay_interest_day;
    }

    public void setPay_interest_day(String pay_interest_day) {
        this.pay_interest_day = pay_interest_day;
    }

    public String getMin_repayment_date() {
        return min_repayment_date;
    }

    public void setMin_repayment_date(String min_repayment_date) {
        this.min_repayment_date = min_repayment_date;
    }

    public int getFinish_preview_repayment() {
        return finish_preview_repayment;
    }

    public void setFinish_preview_repayment(int finish_preview_repayment) {
        this.finish_preview_repayment = finish_preview_repayment;
    }

    public long getPreview_repayment_date() {
        return preview_repayment_date;
    }

    public void setPreview_repayment_date(long preview_repayment_date) {
        this.preview_repayment_date = preview_repayment_date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getValue_date() {
        return value_date;
    }

    public void setValue_date(long value_date) {
        this.value_date = value_date;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getYear_income() {
        return year_income;
    }

    public void setYear_income(String year_income) {
        this.year_income = year_income;
    }

    public String getInterest_year_income() {
        return interest_year_income;
    }

    public void setInterest_year_income(String interest_year_income) {
        this.interest_year_income = interest_year_income;
    }

    public String getEstimated_amount() {
        return estimated_amount;
    }

    public void setEstimated_amount(String estimated_amount) {
        this.estimated_amount = estimated_amount;
    }

    public String getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(String actual_amount) {
        this.actual_amount = actual_amount;
    }

    public int getRepayment() {
        return repayment;
    }

    public void setRepayment(int repayment) {
        this.repayment = repayment;
    }

    public long getPub_date() {
        return pub_date;
    }

    public void setPub_date(long pub_date) {
        this.pub_date = pub_date;
    }
}
