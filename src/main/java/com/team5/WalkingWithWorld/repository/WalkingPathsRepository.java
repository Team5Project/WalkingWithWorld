package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.domain.WalkingPathsMapDTO1;
import com.team5.WalkingWithWorld.entity.WalkingPaths;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalkingPathsRepository extends JpaRepository<WalkingPaths, Integer> {
    Optional<WalkingPathsMapDTO1> findById(int id);
}