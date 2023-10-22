package com.team5.WalkingWithWorld.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
