package com.team5.WalkingWithWorld.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team5.WalkingWithWorld.domain.PhotosDTO;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity

public class Photos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonBackReference
    @ManyToOne
    private Reviews reviews;
    @JsonBackReference
    @ManyToOne
    private WalkingPaths walkingPaths;
    private String imgName;
    private String imgPath;


    public static Photos of(int id, Reviews reviews,WalkingPaths walkingPaths,String imgName,String imgPath){
        return new Photos(id, reviews, walkingPaths, imgName, imgPath);
    }
}
