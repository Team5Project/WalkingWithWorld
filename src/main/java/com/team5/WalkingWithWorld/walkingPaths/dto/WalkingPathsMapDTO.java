package com.team5.WalkingWithWorld.walkingPaths.dto;

import com.team5.WalkingWithWorld.global.domain.MapDTO1;
import com.team5.WalkingWithWorld.global.domain.PhotosDTO1;
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
public class WalkingPathsMapDTO {
    private Long id;
    private Long usersId;
    private String title;
    private String addr;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private List<PhotosDTO1> photosList;
    private List<MapDTO1> mapList;

    public static WalkingPathsMapDTO from(WalkingPaths walkingPaths) {
        return WalkingPathsMapDTO.builder()
                .id(walkingPaths.getId())
                .usersId(walkingPaths.getUsers().getId())
                .title(walkingPaths.getTitle())
                .addr(walkingPaths.getAddr())
                .createdAt(walkingPaths.getCreatedAt())
                .createdBy(walkingPaths.getCreatedBy())
                .modifiedAt(walkingPaths.getModifiedAt())
                .modifiedBy(walkingPaths.getModifiedBy())
                .build();
    }

}
