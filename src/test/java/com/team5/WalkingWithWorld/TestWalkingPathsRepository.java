package com.team5.WalkingWithWorld;

import com.team5.WalkingWithWorld.entity.WalkingPaths;
import com.team5.WalkingWithWorld.repository.WalkingPathsRepository;
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

    //@Autowired
    //WalkingPathsMapper walkingPathsMapper;

    @Test
    void findAll() {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findAll();
        walkingPathsList.forEach(System.out::println);
    }
//    @Test
//    void findByTitleContainingOrAddrContaining() {
//        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findByTitleContainingOrAddrContaining("송파", "송파");
//        walkingPathsList.stream().forEach(System.out::println);
//        //assertThat(walkingPathsList).isEqualTo(walkingPathsMapper.searchWalkingPathByKeyword("송파"));
//    }
}
