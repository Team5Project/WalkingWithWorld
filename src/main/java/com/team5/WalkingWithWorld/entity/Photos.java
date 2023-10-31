package com.team5.WalkingWithWorld.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Entity
public class Photos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Reviews reviews;
    @ManyToOne
    private WalkingPaths walkingPaths;
    private String imgName;
    private String imgPath;
}
