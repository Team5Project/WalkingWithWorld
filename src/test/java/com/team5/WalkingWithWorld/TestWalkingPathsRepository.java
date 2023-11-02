package com.team5.WalkingWithWorld;

import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TestWalkingPathsRepository {
    @Autowired
    WalkingPathsRepository walkingPathsR;

    @Test
    void findAll() {
        List<WalkingPaths> walkingPathsList = walkingPathsR.findAll();
        walkingPathsList.forEach(System.out::println);
    }

}
