package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.domain.WalkingPathsDTO;
import com.team5.WalkingWithWorld.domain.WalkingPathsMapDTO;
import com.team5.WalkingWithWorld.domain.WalkingPathsMapDTO1;
import com.team5.WalkingWithWorld.entity.Map;
import com.team5.WalkingWithWorld.entity.Photos;
import com.team5.WalkingWithWorld.entity.Users;
import com.team5.WalkingWithWorld.entity.WalkingPaths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class WalkingPathsRepositoryTest {
    @Autowired
    WalkingPathsRepository walkingPathsRepository;

    @Test
    void findAll() {
        walkingPathsRepository.findAll().forEach(System.out::println);
    }

    //단방향
    @Test
    @DisplayName("DTO 활용")
    void findById() {
        WalkingPaths walkingPaths = walkingPathsRepository.getReferenceById(1);
        List<Photos> photos = new ArrayList<>();
        List<Map> maps = new ArrayList<>();
        WalkingPathsMapDTO1 walkingPathsMapDTO = WalkingPathsMapDTO1.builder()
                .id(1)
                .usersId(1)
                .addr(walkingPaths.getAddr())
                .title(walkingPaths.getTitle())
                .photosList(photos)
                .mapList(maps)
                .build();
        System.out.println(walkingPathsMapDTO);
    }


    @Test
    @DisplayName("Entity 활용")
    void findById2(){
        WalkingPaths walkingPaths = walkingPathsRepository.getReferenceById(1);
        Users user = Users.of(
                1,
                "테스트",
                "password123",
                "email@gmail.com",
                "인천"
        );
        List<Photos> photos = new ArrayList<>();
        List<Map> maps = new ArrayList<>();
        WalkingPaths walkingPathsMapDTO = WalkingPaths.builder()
                .id(1)
                .users(user)
                .addr(walkingPaths.getAddr())
                .title(walkingPaths.getTitle())
                .build();
        System.out.println(walkingPathsMapDTO);
    }
}