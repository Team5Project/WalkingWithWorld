package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotosRepository extends JpaRepository<Photos, Integer> {
}