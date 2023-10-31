package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.domain.MapDTO;
import com.team5.WalkingWithWorld.entity.Map;
import com.team5.WalkingWithWorld.entity.WalkingPaths;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapRepository extends JpaRepository<Map, Integer> {
    List<Map> findByWalkingPaths(WalkingPaths walkingPaths);
}