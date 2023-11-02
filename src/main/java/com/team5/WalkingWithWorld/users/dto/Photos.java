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
    //@ManyToOne
    @Column(name = "reviews_id")
    private Integer reviewsId;
    //@ManyToOne
    //@JoinColumn(name = "walking_paths_id")
    private Integer walkingPathsId;
    private String imgName;
    private String imgPath;
}
