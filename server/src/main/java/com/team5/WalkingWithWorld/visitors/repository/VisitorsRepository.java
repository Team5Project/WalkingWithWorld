package com.team5.WalkingWithWorld.visitors.repository;

import com.team5.WalkingWithWorld.visitors.entity.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {
    void deleteByIdAndPassword(Long id, String password);

}