package com.team5.WalkingWithWorld.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class WalkingPathsDTO {
    private int id;
    private int usersId;
    private String title;
    private String addr;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private List<PhotosDTO> photosList;
}
