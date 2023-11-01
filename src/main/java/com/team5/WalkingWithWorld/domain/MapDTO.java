package com.team5.WalkingWithWorld.domain;

import com.team5.WalkingWithWorld.entity.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MapDTO {
    private int id;
    private int walkingPathsId;
    private Long time;
    private String distance;
    private String coordinateX;
    private String coordinateY;
}
