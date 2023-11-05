package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.WalkingPaths;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestWalkingPathsRepository {
    @Autowired
    WalkingPathsRepository walkingPathsRepository;
    @Mock
    Users users;

//    @Autowired
//    WalkingPathsMapper walkingPathsMapper;

    @Test
    void findAll() {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findAll();
        walkingPathsList.forEach(System.out::println);
    }
    @Test
    void findByTitleContainingOrAddrContaining() {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findByTitleContainingOrAddrContaining("송파", "송파");
        walkingPathsList.stream().forEach(System.out::println);
        //assertThat(walkingPathsList).isEqualTo(walkingPathsMapper.searchWalkingPathByKeyword("송파"));
    }
    @Test
    void findById() {
        WalkingPaths walkingPaths = walkingPathsRepository.findById(1).orElseThrow(() -> new EntityNotFoundException());
        System.out.println(walkingPaths);
    }
    @Test
    @Transactional
    void save() { // 생성
        WalkingPaths entity = new WalkingPaths();
        entity.setUsers(users);
        entity.setTitle("test");
        entity.setAddr("testtest");
        walkingPathsRepository.save(entity);
        walkingPathsRepository.findAll().forEach(System.out::println);
    }
    @Test
    @Transactional
    void modify() {// 수정

    }

    // 삭제
}
