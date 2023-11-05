package com.team5.WalkingWithWorld.global.repository;

import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotosRepository extends JpaRepository<Photos, Integer> {
    List<Photos> findByWalkingPaths(WalkingPaths walkingPaths);
    Photos findTop1ByWalkingPaths(WalkingPaths walkingPaths);
}