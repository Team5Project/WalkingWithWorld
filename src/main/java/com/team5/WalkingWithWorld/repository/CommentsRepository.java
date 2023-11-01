package com.team5.WalkingWithWorld.repository;


import com.team5.WalkingWithWorld.domain.CommentsDTO;
import com.team5.WalkingWithWorld.entity.Comments;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {

    Comments save(Comments comments);
}
