package com.team5.WalkingWithWorld.global.domain;

import com.team5.WalkingWithWorld.global.entity.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class MapDTO {
    private Long id;
    private Long walkingPathsId;
    private Long time;
    private String distance;

    public static MapDTO from(Map map){
        return MapDTO.builder()
                .id(map.getId())
                .walkingPathsId(map.getWalkingPaths().getId())
                .time(map.getTime())
                .distance(map.getDistance())
                .build();
    }
}
