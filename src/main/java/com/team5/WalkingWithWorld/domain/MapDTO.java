package com.team5.WalkingWithWorld.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MapDTO {
    private int id;
    private int walkingPathsId;
    private int time;
    private int distance;
    private String coordinateX;
    private String coordinateY;
}
