package com.team5.WalkingWithWorld.global.repository;

import com.team5.WalkingWithWorld.global.entity.Coordinate;
import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
    List<Coordinate> findByWalkingPaths(WalkingPaths walkingPaths);
}