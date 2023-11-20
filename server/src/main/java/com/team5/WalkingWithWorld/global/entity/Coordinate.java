package com.team5.WalkingWithWorld.global.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne(optional = false)
    private WalkingPaths walkingPaths;
    @Column(name = "coordinate_x")
    private String coordinateX;
    @Column(name="coordinate_y")
    private String coordinateY;

    public static Coordinate of(WalkingPaths walkingPaths, String x, String y) {
        return new Coordinate(null, walkingPaths, x, y);
    }
}
