package com.pcjinrong.pcjr.bean;

/**
 * 回款计划
 * Created by Mario on 2016/5/27.
 */
public class PaymentPlan {

    private String product_name;
    private long estimated_time;
    private long actual_time;
    private String estimated_capital;
    private String estimated_interest;
    private int status;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public long getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(long estimated_time) {
        this.estimated_time = estimated_time;
    }

    public long getActual_time() {
        return actual_time;
    }

    public void setActual_time(long actual_time) {
        this.actual_time = actual_time;
    }

    public String getEstimated_capital() {
        return estimated_capital;
    }

    public void setEstimated_capital(String estimated_capital) {
        this.estimated_capital = estimated_capital;
    }

    public String getEstimated_interest() {
        return estimated_interest;
    }

    public void setEstimated_interest(String estimated_interest) {
        this.estimated_interest = estimated_interest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
