package com.team5.WalkingWithWorld.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Photos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @ManyToOne
//    private  reviewsId;
    @ManyToOne
    private WalkingPaths walkingPaths;
    private String imgName;
    private String imgPath;
}
