package com.team5.WalkingWithWorld.comments.repository;

import com.team5.WalkingWithWorld.comments.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    List<Comments> findAllByWalkingPathsId(int id);
}