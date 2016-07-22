package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 产品类
 */
public class Product implements Serializable{
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String year_income;
    @Expose
    private String amount;
    @Expose
    private String month;
    @Expose
    private int repayment;
    @Expose
    private int status;
    @Expose
    private long pub_date;
    @Expose
    private String series;
    @Expose
    private int is_preview_repayment;
    @Expose
    private int rate;
    @Expose
    private String guarantors_id;
    @Expose
    private String threshold_amount;
    @Expose
    private String increasing_amount;
    @Expose
    private String product_no;
    @Expose
    private long repayment_date;
    @Expose
    private long value_date;
    @Expose
    private String guarantors_name;
    @Expose
    private String intro;
    @Expose
    private String risk_control;
    @Expose
    private String pay_interest_day;
    @Expose
    private String min_repayment_date;
    @Expose
    private int finish_preview_repayment;
    @Expose
    private String product_amount;
    @Expose
    private String borrower_intro;
    @Expose
    private String estimate_interest;
    @Expose
    private long preview_repayment_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear_income() {
        return year_income;
    }

    public void setYear_income(String year_income) {
        this.year_income = year_income;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getRepayment() {
        return repayment;
    }

    public void setRepayment(int repayment) {
        this.repayment = repayment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getPub_date() {
        return pub_date;
    }

    public void setPub_date(long pub_date) {
        this.pub_date = pub_date;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getIs_preview_repayment() {
        return is_preview_repayment;
    }

    public void setIs_preview_repayment(int is_preview_repayment) {
        this.is_preview_repayment = is_preview_repayment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getGuarantors_id() {
        return guarantors_id;
    }

    public void setGuarantors_id(String guarantors_id) {
        this.guarantors_id = guarantors_id;
    }

    public String getThreshold_amount() {
        return threshold_amount;
    }

    public void setThreshold_amount(String threshold_amount) {
        this.threshold_amount = threshold_amount;
    }

    public String getIncreasing_amount() {
        return increasing_amount;
    }

    public void setIncreasing_amount(String increasing_amount) {
        this.increasing_amount = increasing_amount;
    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public long getRepayment_date() {
        return repayment_date;
    }

    public void setRepayment_date(long repayment_date) {
        this.repayment_date = repayment_date;
    }

    public long getValue_date() {
        return value_date;
    }

    public void setValue_date(long value_date) {
        this.value_date = value_date;
    }

    public String getGuarantors_name() {
        return guarantors_name;
    }

    public void setGuarantors_name(String guarantors_name) {
        this.guarantors_name = guarantors_name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getRisk_control() {
        return risk_control;
    }

    public void setRisk_control(String risk_control) {
        this.risk_control = risk_control;
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

    public String getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(String product_amount) {
        this.product_amount = product_amount;
    }

    public String getBorrower_intro() {
        return borrower_intro;
    }

    public void setBorrower_intro(String borrower_intro) {
        this.borrower_intro = borrower_intro;
    }

    public String getEstimate_interest() {
        return estimate_interest;
    }

    public void setEstimate_interest(String estimate_interest) {
        this.estimate_interest = estimate_interest;
    }

    public long getPreview_repayment_date() {
        return preview_repayment_date;
    }

    public void setPreview_repayment_date(long preview_repayment_date) {
        this.preview_repayment_date = preview_repayment_date;
    }
}