package com.team5.WalkingWithWorld.global.domain;

import lombok.*;

@Getter
@Setter
@ToString
public class PhotosDTO {
    private int id;
    private int reviewsId;
    private int walkingPathsId;
    private String imgName;
    private String imgPath;

}
