package com.team5.WalkingWithWorld.walkingPaths.repository;

import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalkingPathsRepository extends JpaRepository<WalkingPaths, Integer>,
        JpaSpecificationExecutor<WalkingPaths> {
    // 전체 리스트 최신순
    Page<WalkingPaths> findAllByOrderByCreatedAtDesc(Pageable pageable);
    // keyword로 찾기
    Page<WalkingPaths> findByTitleContainingOrAddrContaining(String keyword1, String keyword2, Pageable pageable);

    // long id로 읽어오기
    Optional<WalkingPaths> findById(long id);

    // 조회수
    @Modifying
    @Query("update WalkingPaths w set w.view = w.view + 1 where w.id = :id")
    int updateView(@Param("id") Long id);

//    // 삭제예정
//    List<WalkingPaths> findAllByOrderByCreatedAtDesc();
//    List<WalkingPaths> findByTitleContainingOrAddrContaining(String keyword1, String keyword2);
}