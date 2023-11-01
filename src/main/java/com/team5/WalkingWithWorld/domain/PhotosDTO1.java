package com.team5.WalkingWithWorld.domain;

import com.team5.WalkingWithWorld.entity.Photos;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
public class PhotosDTO1 {
    private int id;
    private int reviewsId;
    private int walkingPathsId;
    private String imgName;
    private String imgPath;

    public static PhotosDTO1 from(Photos photos){
        return PhotosDTO1.builder()
                .id(photos.getId())
                .reviewsId(photos.getReviews().getId())
                .walkingPathsId(photos.getWalkingPaths().getId())
                .imgName(photos.getImgName())
                .imgPath(photos.getImgPath())
                .build();
    }
}
