package com.team5.WalkingWithWorld.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //@ManyToOne
    //@JoinColumn(name = "walking_paths_id")
    private Integer walkingPathsId;
    private int time;
    private int distance;
    private String coordinate_x;
    private String coordinate_y;
}
