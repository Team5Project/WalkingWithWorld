package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorsRepository extends JpaRepository<Visitors, Long> {
    public List<Visitors> deleteByIdAndPassword(int id, String password);
}