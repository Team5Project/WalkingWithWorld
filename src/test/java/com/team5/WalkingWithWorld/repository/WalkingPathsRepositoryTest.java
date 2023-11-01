package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.domain.*;
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
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class WalkingPathsRepositoryTest {
    @MockBean
    WalkingPathsRepository walkingPathsRepository;

    @MockBean
    PhotosRepository photosRepository;
    @MockBean
    MapRepository mapRepository;

    @Test
    void findAll() {
        walkingPathsRepository.findAll().forEach(System.out::println);
    }

    //단방향
    @Test
    @DisplayName("DTO 활용")
    void findById() {
        WalkingPaths walkingPaths = walkingPathsRepository.getReferenceById(1);
        List<PhotosDTO1> photos = photosRepository.findByWalkingPaths(walkingPaths).stream().map(PhotosDTO1::from).collect(Collectors.toList());
        List<MapDTO1> maps = mapRepository.findByWalkingPaths(walkingPaths).stream().map(MapDTO1::from).collect(Collectors.toList());
        WalkingPathsMapDTO walkingPathsMapDTO = WalkingPathsMapDTO.builder()
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
    @DisplayName("DTO 양방향")
    void findById3(){
        WalkingPaths walkingPaths = walkingPathsRepository.getReferenceById(1);
        WalkingPathsMapDTO walkingPathsMapDTO = WalkingPathsMapDTO.from(walkingPaths);
        System.out.println(walkingPathsMapDTO);
    }

    @Test
    @DisplayName("엔티티 단방향")
    void findById4(){
        WalkingPaths walkingPaths = walkingPathsRepository.getReferenceById(1);
//        List<Photos> photos = photosRepository.findByWalkingPaths(walkingPaths);
//        List<Map> maps = mapRepository.findByWalkingPaths(walkingPaths);
//        WalkingPathsMapDTO1 walkingPathsMapDTO = WalkingPathsMapDTO1.from(walkingPaths);
//        System.out.println(walkingPathsMapDTO);
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