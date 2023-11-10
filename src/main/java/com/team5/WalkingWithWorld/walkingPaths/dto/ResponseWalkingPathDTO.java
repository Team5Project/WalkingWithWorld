package com.team5.WalkingWithWorld.walkingPaths.dto;

import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ResponseWalkingPathDTO {
    private int id;
    private String title;
    private String addr;
    private Long time;
    private String distance;
    private Photos photos;

    public ResponseWalkingPathDTO(int id, String title, String addr) {
        this.id = id;
        this.title = title;
        this.addr = addr;
    }

    public static ResponseWalkingPathDTO from(WalkingPaths walkingPaths, Map map, Photos photos) {
        Long time = (map == null)?null:map.getTime();
        String distance = (map == null)?null:map.getDistance();
        return new ResponseWalkingPathDTO(walkingPaths.getId(), walkingPaths.getTitle(), walkingPaths.getAddr(), time, distance, photos);
    }
}
