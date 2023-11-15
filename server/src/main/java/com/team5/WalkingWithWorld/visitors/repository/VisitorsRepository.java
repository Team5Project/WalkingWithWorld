package com.team5.WalkingWithWorld.visitors.repository;

import com.team5.WalkingWithWorld.visitors.entity.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorsRepository extends JpaRepository<Visitors, Long> {
    public List<Visitors> deleteByIdAndPassword(long id, String password);
}