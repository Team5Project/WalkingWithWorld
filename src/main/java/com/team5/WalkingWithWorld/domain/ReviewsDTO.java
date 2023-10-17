package com.team5.WalkingWithWorld.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ReviewsDTO {
    private int id;
    private int usersId; // 세션에서 로그인한 유저의 id를 가져와서 넣어주면 됍니다
    private int walkingPathsId; // 위 PathVariable("walking-paths-id") -> /walking-paths/ 1
    private String content;
    private LocalDateTime createdAt; // LocalDate.now()
    private String createdBy; // userMapper.getUserById(id).getName();
    private LocalDateTime modifiedAt; //null
    private String modifiedBy; //null
    private List<PhotosDTO> photosList;
}
