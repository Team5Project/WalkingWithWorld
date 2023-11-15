package com.team5.WalkingWithWorld.walkingPaths.dto;

import com.team5.WalkingWithWorld.global.domain.MapDTO;
import com.team5.WalkingWithWorld.global.domain.PhotosDTO;
import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<PhotosDTO> photosList;
    private List<MapDTO> mapList;

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
    public static WalkingPathsMapDTO from(WalkingPaths walkingPaths,List<Photos> photos,List<Map> maps) {
        return WalkingPathsMapDTO.builder()
                .id(walkingPaths.getId())
                .usersId(walkingPaths.getUsers().getId())
                .title(walkingPaths.getTitle())
                .addr(walkingPaths.getAddr())
                .createdAt(walkingPaths.getCreatedAt())
                .createdBy(walkingPaths.getCreatedBy())
                .modifiedAt(walkingPaths.getModifiedAt())
                .modifiedBy(walkingPaths.getModifiedBy())
                .photosList(photos.stream().map(PhotosDTO::from).collect(Collectors.toList()))
                .mapList(maps.stream().map(MapDTO::from).collect(Collectors.toList()))
                .build();
    }
}
