package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 公告
 * Created by Mario on 2016/5/25.
 */
public class Announce {
    @Expose
    private String title;
    @Expose
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
