package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.domain.WalkingPathsDTO;
import com.team5.WalkingWithWorld.domain.WalkingPathsMapDTO;
import com.team5.WalkingWithWorld.domain.WalkingPathsMapDTO1;
import com.team5.WalkingWithWorld.entity.Map;
import com.team5.WalkingWithWorld.entity.Photos;
import com.team5.WalkingWithWorld.entity.WalkingPaths;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class WalkingPathsRepositoryTest {
    @Autowired
    MapRepository mapRepository;
    @Autowired
    PhotosRepository photosRepository;
    @Autowired
    WalkingPathsRepository walkingPathsRepository;

    @Test
    void findAll() {
        walkingPathsRepository.findAll().forEach(System.out::println);
    }

    //단방향
    @Test
    void findById() {
        WalkingPaths walkingPaths = walkingPathsRepository.getReferenceById(1);
        List<Photos> photos = photosRepository.findByWalkingPaths(walkingPaths);
        List<Map> maps = mapRepository.findByWalkingPaths(walkingPaths);
        WalkingPathsMapDTO1 walkingPathsMapDTO = WalkingPathsMapDTO1.builder()
                .id(1)
                .addr(walkingPaths.getAddr())
                .title(walkingPaths.getTitle())
                .photosList(photos)
                .mapList(maps)
                .build();
        System.out.println(walkingPathsMapDTO);
    }

    //양방향
    @Test
    void findById2(){
        List<Map> map = walkingPathsRepository.findById(1).get().getMapList();
        System.out.println(map);
    }
}