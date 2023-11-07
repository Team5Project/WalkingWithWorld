package com.team5.WalkingWithWorld.walkingPaths.repository;

import com.team5.WalkingWithWorld.walkingPaths.dto.WalkingPathsMapDTO1;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalkingPathsRepository extends JpaRepository<WalkingPaths, Integer> {
    Optional<WalkingPathsMapDTO1> findById(int id);

    Page<WalkingPaths> findAllBy(Pageable pageable);
}