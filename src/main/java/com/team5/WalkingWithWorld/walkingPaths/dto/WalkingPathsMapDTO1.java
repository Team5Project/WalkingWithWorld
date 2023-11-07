package com.team5.WalkingWithWorld.walkingPaths.dto;

import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class WalkingPathsMapDTO1 {
    private Long id;
    private Long usersId;
    private String title;
    private String addr;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private List<Photos> photosList;
    private List<Map> mapList;

    public static WalkingPathsMapDTO1 from(WalkingPaths walkingPaths,List<Photos> photos,List<Map> map){
        return WalkingPathsMapDTO1.builder()
                .id(walkingPaths.getId())
                .usersId(walkingPaths.getUsers().getId())
                .title(walkingPaths.getTitle())
                .addr(walkingPaths.getAddr())
                .createdAt(walkingPaths.getCreatedAt())
                .createdBy(walkingPaths.getCreatedBy())
                .modifiedAt(walkingPaths.getModifiedAt())
                .modifiedBy(walkingPaths.getModifiedBy())
                .photosList(photos)
                .mapList(map)
                .build();
    }
}
