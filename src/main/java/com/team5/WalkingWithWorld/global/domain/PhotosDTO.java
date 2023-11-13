package com.team5.WalkingWithWorld.global.domain;

import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import lombok.*;

@Getter
@Setter
@ToString
public class PhotosDTO {
    private Long id;
    private Long reviewsId;
    private Long walkingPathsId;
    private String imgName;
    private String imgPath;

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
