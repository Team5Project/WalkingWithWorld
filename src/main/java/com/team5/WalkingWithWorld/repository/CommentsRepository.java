package com.team5.WalkingWithWorld.repository;


import com.team5.WalkingWithWorld.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {
}
