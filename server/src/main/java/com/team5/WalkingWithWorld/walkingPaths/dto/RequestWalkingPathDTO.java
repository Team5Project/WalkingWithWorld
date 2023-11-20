package com.team5.WalkingWithWorld.walkingPaths.dto;

import com.team5.WalkingWithWorld.global.dto.RequestMapDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RequestWalkingPathDTO {
    private String title;
    private String addr;
    private RequestMapDTO requestMapDTO;
}
