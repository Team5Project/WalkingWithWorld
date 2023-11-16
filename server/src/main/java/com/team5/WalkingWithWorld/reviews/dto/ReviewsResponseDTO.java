package com.team5.WalkingWithWorld.reviews.dto;

import com.team5.WalkingWithWorld.global.domain.PhotosDTO;
import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@Builder
public class ReviewsResponseDTO {
    private Long id;
    private Long usersId; // 세션에서 로그인한 유저의 id를 가져와서 넣어주면 됍니다
    private Long walkingPathsId; // 위 PathVariable("walking-paths-id") -> /walking-paths/ 1
    private String content;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<PhotosDTO> photosList;

    public static ReviewsResponseDTO from(Reviews reviews) {
        return ReviewsResponseDTO.builder()
                .id(reviews.getId())
                .usersId(reviews.getUsers().getId())
                .walkingPathsId(reviews.getWalkingPaths().getId())
                .content(reviews.getContent())
                .build();
    }

    public static ReviewsResponseDTO from(Reviews reviews,List<Photos> photos) {
        return ReviewsResponseDTO.builder()
                .id(reviews.getId())
                .usersId(reviews.getUsers().getId())
                .walkingPathsId(reviews.getWalkingPaths().getId())
                .content(reviews.getContent())
                .createdBy(reviews.getCreatedBy())
                .createdAt(reviews.getCreatedAt())
                .photosList(photos.stream().map(PhotosDTO::from).collect(Collectors.toList()))
                .build();
    }
}
