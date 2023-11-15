package com.team5.WalkingWithWorld.global.domain;

import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import lombok.*;

@Getter
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Setter
@Builder
public class PhotosDTO {
    private Long id;
    private Long reviewsId;
    private Long walkingPathsId;
    private String imgName;
    private String imgPath;

    public PhotosDTO() {

    }

    public static PhotosDTO from(Photos photos){
        return PhotosDTO.builder()
                .id(photos.getId())
                .reviewsId(photos.getReviews().getId())
                .walkingPathsId(photos.getWalkingPaths().getId())
                .imgName(photos.getImgName())
                .imgPath(photos.getImgPath())
                .build();
    }
    public Photos toEntity(Reviews reviews, WalkingPaths walkingPaths){
        return Photos.of(
                id,
                reviews,
                walkingPaths,
                imgName,
                imgPath
        );
    }
}
