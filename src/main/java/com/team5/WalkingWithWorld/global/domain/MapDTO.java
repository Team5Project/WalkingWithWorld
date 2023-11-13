package com.team5.WalkingWithWorld.global.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MapDTO {
    private Long id;
    private Long walkingPathsId;
    private Long time;
    private String distance;
    private String coordinateX;
    private String coordinateY;
}
