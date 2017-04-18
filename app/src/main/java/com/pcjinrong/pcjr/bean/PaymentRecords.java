package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 回款记录
 * Created by Mario on 2017/4/14.
 */
public class PaymentRecords {
    @Expose
    private String product_name;
    @Expose
    private int repayment;
    @Expose
    private String amount;
    @Expose
    private String year_income;
    @Expose
    private String interest_total;
    @Expose
    private long join_date;
    @Expose
    private long deadline;
    @Expose
    private String extra_interest_total;
    @Expose
    private String interest_year_income;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getRepayment() {
        return repayment;
    }

    public void setRepayment(int repayment) {
        this.repayment = repayment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getYear_income() {
        return year_income;
    }

    public void setYear_income(String year_income) {
        this.year_income = year_income;
    }

    public String getInterest_total() {
        return interest_total;
    }

    public void setInterest_total(String interest_total) {
        this.interest_total = interest_total;
    }

    public long getJoin_date() {
        return join_date;
    }

    public void setJoin_date(long join_date) {
        this.join_date = join_date;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getExtra_interest_total() {
        return extra_interest_total;
    }

    public void setExtra_interest_total(String extra_interest_total) {
        this.extra_interest_total = extra_interest_total;
    }

    public String getInterest_year_income() {
        return interest_year_income;
    }

    public void setInterest_year_income(String interest_year_income) {
        this.interest_year_income = interest_year_income;
    }
}
