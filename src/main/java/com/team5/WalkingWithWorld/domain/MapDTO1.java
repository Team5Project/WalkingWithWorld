package com.team5.WalkingWithWorld.domain;

import com.team5.WalkingWithWorld.entity.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MapDTO1 {
    private int id;
    private int walkingPathsId;
    private Long time;
    private String distance;
    private String coordinateX;
    private String coordinateY;

    public static MapDTO1 from(Map map){
        return MapDTO1.builder()
                .id(map.getId())
                .walkingPathsId(map.getWalkingPaths().getId())
                .time(map.getTime())
                .distance(map.getDistance())
                .coordinateX(map.getCoordinateX())
                .coordinateY(map.getCoordinateY())
                .build();
    }
}
