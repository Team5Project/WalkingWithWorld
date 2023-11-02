package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.WalkingPaths;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkingPathsRepository extends JpaRepository<WalkingPaths, Integer> {
    // 키워드로 찾기
    List<WalkingPaths> findByTitleContainingOrAddrContaining(String keyword1, String keyword2);
    // searchDTO로 찾기

}