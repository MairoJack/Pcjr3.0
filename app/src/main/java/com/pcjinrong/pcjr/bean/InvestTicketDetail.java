package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 投资券详情
 * Created by Mario on 2016/6/2.
 */
public class InvestTicketDetail {
    @Expose
    private String id;
    @Expose
    private String introduction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
