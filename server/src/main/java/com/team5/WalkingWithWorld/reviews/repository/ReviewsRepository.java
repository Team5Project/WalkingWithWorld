package com.team5.WalkingWithWorld.reviews.repository;

import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    Page<Reviews> findAllByWalkingPathsId(Long walkingPathsId, Pageable pageable);

    void deleteReviewsByIdAndUsersEmail(Long id, String email);
}