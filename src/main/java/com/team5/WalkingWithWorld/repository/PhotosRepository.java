package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.Photos;
import com.team5.WalkingWithWorld.entity.WalkingPaths;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotosRepository extends JpaRepository<Photos, Integer> {
    List<Photos> findByWalkingPaths(WalkingPaths walkingPaths);
}