package com.team5.WalkingWithWorld.global.repository;

import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Integer> {
    Map findByWalkingPaths(WalkingPaths walkingPaths);
//    Map findTop1ByWalkingPaths(WalkingPaths walkingPaths);

    void deleteAllByWalkingPaths(WalkingPaths walkingPaths);

}