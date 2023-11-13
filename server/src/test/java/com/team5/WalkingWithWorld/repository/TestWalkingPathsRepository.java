package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestWalkingPathsRepository {
    @Autowired
    WalkingPathsRepository walkingPathsRepository;

    @Test
    void findAllByOrderByCreatedAtDesc() {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findAllByOrderByCreatedAtDesc();
        walkingPathsList.forEach(System.out::println);
    }
    @Test
    void findByTitleContainingOrAddrContaining() {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findByTitleContainingOrAddrContaining("새", "새");
        walkingPathsList.forEach(System.out::println);
    }
}
