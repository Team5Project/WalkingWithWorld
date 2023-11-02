package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Integer> {
}