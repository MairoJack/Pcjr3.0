package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * 首页焦点图 公告
 * Created by Mario on 2016/5/25.
 */
public class IndexFocusInfo {
    @Expose
    private List<FocusImg> top_focus_img;

    @Expose
    private List<FocusImg> middle_focus_img;

    @Expose
    private List<Announce> announce;

    public List<FocusImg> getTop_focus_img() {
        return top_focus_img;
    }

    public void setTop_focus_img(List<FocusImg> top_focus_img) {
        this.top_focus_img = top_focus_img;
    }

    public List<FocusImg> getMiddle_focus_img() {
        return middle_focus_img;
    }

    public void setMiddle_focus_img(List<FocusImg> middle_focus_img) {
        this.middle_focus_img = middle_focus_img;
    }

    public List<Announce> getAnnounce() {
        return announce;
    }

    public void setAnnounce(List<Announce> announce) {
        this.announce = announce;
    }
}
