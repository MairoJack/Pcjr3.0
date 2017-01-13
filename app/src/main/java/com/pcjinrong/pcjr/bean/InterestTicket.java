package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 加息券
 * Created by Mario on 2016/12/5.
 */
public class InterestTicket implements Serializable,Comparable<InterestTicket>{
    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String rate;
    @Expose
    private int series;
    @Expose
    private String start_amount;
    @Expose
    private String end_amount;
    @Expose
    private int start_day;
    @Expose
    private int end_day;
    @Expose
    private long start_time;
    @Expose
    private long end_time;
    @Expose
    private InterestTicketDetail activity;

    private Boolean selectable = false;

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

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
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

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public InterestTicketDetail getActivity() {
        return activity;
    }

    public void setActivity(InterestTicketDetail activity) {
        this.activity = activity;
    }

    public int getStart_day() {
        return start_day;
    }

    public void setStart_day(int start_day) {
        this.start_day = start_day;
    }

    public int getEnd_day() {
        return end_day;
    }

    public void setEnd_day(int end_day) {
        this.end_day = end_day;
    }

    public Boolean getSelectable() {
        return selectable;
    }

    public void setSelectable(Boolean selectable) {
        this.selectable = selectable;
    }


    @Override
    public int compareTo(InterestTicket another) {
        if(this.getId().equals("00") || another.getId().equals("00")){
            return 0;
        }
        if(this.getSelectable()){
            return -1;
        }
        return 0;
    }
}
