package com.team5.WalkingWithWorld.domain;

import com.team5.WalkingWithWorld.entity.Map;
import com.team5.WalkingWithWorld.entity.Photos;
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
    private int id;
    private int usersId;
    private String title;
    private String addr;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private List<Photos> photosList;
    private List<Map> mapList;

}
