package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 优惠券
 * Created by Mario on 2016/5/26.
 */
public class Coupon implements Serializable{
    @Expose
    private int invest_ticket_num;
    @Expose
    private int interest_ticket_num;
    @Expose
    private int red_packet_num;

    public int getInvest_ticket_num() {
        return invest_ticket_num;
    }

    public void setInvest_ticket_num(int invest_ticket_num) {
        this.invest_ticket_num = invest_ticket_num;
    }

    public int getRed_packet_num() {
        return red_packet_num;
    }

    public void setRed_packet_num(int red_packet_num) {
        this.red_packet_num = red_packet_num;
    }

    public int getInterest_ticket_num() {
        return interest_ticket_num;
    }

    public void setInterest_ticket_num(int interest_ticket_num) {
        this.interest_ticket_num = interest_ticket_num;
    }
}
