package com.team5.WalkingWithWorld.walkingPaths.repository;

import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface WalkingPathsRepository extends JpaRepository<WalkingPaths, Integer> {
    //Optional<WalkingPathsMapDTO1> findById(int id);
    // 전체 리스트 최신순
    List<WalkingPaths> findAllByOrderByCreatedAtDesc();
    // keyword로 찾기
    List<WalkingPaths> findByTitleContainingOrAddrContaining(String keyword1, String keyword2);
}