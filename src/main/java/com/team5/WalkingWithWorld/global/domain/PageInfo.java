package com.team5.WalkingWithWorld.global.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageInfo {
    private int pageNum;
    private int size;

    public PageInfo(){
        this(1,5);
    }
    public PageInfo(int pageNum, int size) {
        this.pageNum = pageNum;
        this.size = size;
    }

    public int getSkip(){
        return this.pageNum = (pageNum - 1) * size;
    }
}
