package com.team5.WalkingWithWorld.global.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RequestMapDTO {
    private Long time;
    private String distance;
    private List<String> coordinateX;
    private List<String> coordinateY;
}
