package com.team5.WalkingWithWorld.walkingPaths.repository.querydsl;

import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalkingPathsRepositoryCustom {
    Page<WalkingPaths> filterWalkingPaths(String keyword, List<String> location, int minTime, int maxTime, String minDistance, String maxDistance, Pageable pageable);
}
