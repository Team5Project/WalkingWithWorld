package com.team5.WalkingWithWorld.global.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team5.WalkingWithWorld.global.dto.RequestMapDTO;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private WalkingPaths walkingPaths;
    private Long time;
    private String distance;

    public Map(WalkingPaths walkingPaths, Long time, String distance) {
        this.walkingPaths = walkingPaths;
        this.time = time;
        this.distance = distance;
    }

    public static Map from(WalkingPaths walkingPaths, RequestMapDTO requestMapDTO) {
        return new Map(walkingPaths, requestMapDTO.getTime(), requestMapDTO.getDistance());
    }
}
