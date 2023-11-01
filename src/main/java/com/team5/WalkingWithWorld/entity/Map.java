package com.team5.WalkingWithWorld.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Entity
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonBackReference
    @ManyToOne
    private WalkingPaths walkingPaths;
    private Long time;
    private String distance;
    private String coordinateX;
    private String coordinateY;
}
