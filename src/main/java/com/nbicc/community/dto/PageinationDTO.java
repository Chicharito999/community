package com.nbicc.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageinationDTO<T> {
    private List<T> data;
    private Integer totalPage;
    private Integer currentPage;
    private List<Integer> pages;
    private Boolean hasPre;
    private Boolean hasNext;
    private Boolean hasFirst;
    private Boolean hasLast;

    public void setTotalPage(Integer count, Integer size) {
        if (count % size == 0) {
            this.totalPage = count / size;
        } else {
            this.totalPage = count / size + 1;
        }
    }

    public void set(Integer page) {
        this.currentPage = page;
        this.pages = new ArrayList<>();
        this.pages.add(page);
        this.hasFirst = true;
        this.hasLast = true;
        for (int i = 1; i <= 3; i++) {
            if (page + i <= totalPage) {
                this.pages.add(page + i);
            }
            if (page - i >= 1) {
                this.pages.add(0, page - i);
            }
        }
        if (page == 1) {
            this.hasPre = false;
        } else {
            this.hasPre = true;
        }
        if (page == this.totalPage) {
            this.hasNext = false;
        } else {
            this.hasNext = true;
        }
        if (this.pages.contains(1)) {
            this.hasFirst = false;
        }
        if (this.pages.contains(this.totalPage)) {
            this.hasLast = false;
        }
    }

}
