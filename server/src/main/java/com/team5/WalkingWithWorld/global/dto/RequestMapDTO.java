package com.team5.WalkingWithWorld.global.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestMapDTO {
    private Long time;
    private String distance;
    private String coordinateX;
    private String coordinateY;
}
