package com.team5.WalkingWithWorld.walkingPaths.repository;

import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.querydsl.WalkingPathsRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkingPathsRepository extends JpaRepository<WalkingPaths, Integer>, WalkingPathsRepositoryCustom {
    //Optional<WalkingPathsMapDTO1> findById(int id);
    // 전체 리스트 최신순
    List<WalkingPaths> findAllByOrderByCreatedAtDesc();
    // keyword로 찾기
    List<WalkingPaths> findByTitleContainingOrAddrContaining(String keyword1, String keyword2);
    // page
    Page<WalkingPaths> findAllBy(Pageable pageable);
    Page<WalkingPaths> findByTitleContainingOrAddrContaining(Pageable pageable, String keyword1, String keyword2);
}