package com.team5.WalkingWithWorld.walkingPaths.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team5.WalkingWithWorld.global.entity.Coordinate;
import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ResponseWalkingPathDetailDTO {
    private Long id;
    @JsonIgnore
    private Users users;
    private String title;
    private String addr;
    private Map map;
    private List<Coordinate> coordinateList;
    private List<Photos> photosList;

    public static ResponseWalkingPathDetailDTO from(WalkingPaths walkingPaths,
                                                                     Map map,
                                                                     List<Coordinate> coordinateList,
                                                                     List<Photos> photosList) {
        return new ResponseWalkingPathDetailDTO(walkingPaths.getId(), walkingPaths.getUsers(), walkingPaths.getTitle(),
                walkingPaths.getAddr(), map, coordinateList, photosList);
    }
}
