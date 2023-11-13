package com.team5.WalkingWithWorld.comments.repository;

import com.team5.WalkingWithWorld.comments.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    List<Comments> findAllByWalkingPathsId(Long id);

    Page<Comments>findAllByWalkingPathsIdOrderByCreatedAtDesc(Long walkingPathsId, Pageable pageable);
}