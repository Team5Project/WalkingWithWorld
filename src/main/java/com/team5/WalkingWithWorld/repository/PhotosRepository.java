package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.Photos;
import com.team5.WalkingWithWorld.entity.WalkingPaths;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotosRepository extends JpaRepository<Photos, Integer> {
    List<Photos> findByWalkingPaths(WalkingPaths walkingPaths);
}