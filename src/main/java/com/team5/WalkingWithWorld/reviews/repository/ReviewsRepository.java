package com.team5.WalkingWithWorld.reviews.repository;

import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
}