package com.team5.WalkingWithWorld.walkingPaths.repository;

import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkingPathsRepository extends JpaRepository<WalkingPaths, Integer> {
    //Optional<WalkingPathsMapDTO1> findById(int id);
    // keyword로 찾기
    List<WalkingPaths> findByTitleContainingOrAddrContaining(String keyword1, String keyword2);

}