package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 加息券
 * Created by Mario on 2016/12/5.
 */
public class AvailableInterest implements Serializable{
    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String rate;
    @Expose
    private String series;
    @Expose
    private String start_amount;
    @Expose
    private String end_amount;
    @Expose
    private String start_day;
    @Expose
    private String end_day;
    @Expose
    private String start_time;
    @Expose
    private String end_time;


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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getStart_amount() {
        return start_amount;
    }

    public void setStart_amount(String start_amount) {
        this.start_amount = start_amount;
    }

    public String getEnd_amount() {
        return end_amount;
    }

    public void setEnd_amount(String end_amount) {
        this.end_amount = end_amount;
    }

    public String getStart_day() {
        return start_day;
    }

    public void setStart_day(String start_day) {
        this.start_day = start_day;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
