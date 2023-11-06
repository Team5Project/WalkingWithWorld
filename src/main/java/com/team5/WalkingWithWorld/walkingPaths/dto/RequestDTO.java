package com.team5.WalkingWithWorld.walkingPaths.dto;

import com.team5.WalkingWithWorld.global.dto.RequestMapDTO;
import com.team5.WalkingWithWorld.global.dto.RequestPhotosDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RequestDTO {
    private RequestWalkingPathDTO requestWalkingPathDTO;
    private List<RequestMapDTO> requestMapDTO;
}
