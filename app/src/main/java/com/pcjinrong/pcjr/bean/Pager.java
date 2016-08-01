package com.pcjinrong.pcjr.bean;

import com.google.gson.annotations.Expose;

/**
 * 分页实体类
 * Created by Mario on 2016/5/26.
 */
public class Pager {
    @Expose
    private int page;
    @Expose
    private int pageSize;
    @Expose
    private int total;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
