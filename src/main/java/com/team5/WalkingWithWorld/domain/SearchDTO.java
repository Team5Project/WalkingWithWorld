package com.team5.WalkingWithWorld.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDTO {
    private String location;
    private int minTime;
    private int maxTime;
    private int minDistance;
    private int maxDistance;
    private String keyword;

    private int pageNum;
    private int pageSize;
}
