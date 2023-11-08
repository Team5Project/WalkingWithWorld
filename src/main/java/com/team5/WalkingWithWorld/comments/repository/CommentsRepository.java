package com.team5.WalkingWithWorld.comments.repository;

import com.team5.WalkingWithWorld.comments.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findTop5ByWalkingPathsIdOrderByCreatedAtDesc(int id);
    Page<Comments> findAllByWalkingPathsIdOrderByCreatedAtDesc(int walkingPathsId,
                                                               PageRequest scrollRequest);
    void deleteById(Long id);
}