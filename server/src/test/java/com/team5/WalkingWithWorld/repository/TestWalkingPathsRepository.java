package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestWalkingPathsRepository {
    @MockBean
    WalkingPathsRepository walkingPathsRepository;
    @Mock
    Users users;
    @Test
    @Order(2)
    void readAll() {
        Page<WalkingPaths> walkingPathsList = walkingPathsRepository.findAllByOrderByCreatedAtDesc(Pageable.ofSize(2).withPage(0));
        System.out.println(walkingPathsList);
    }

    @Test
    @Order(1)
    @Rollback(value = false)
    void insertWalkingPath() {
        WalkingPaths entity = WalkingPaths.of(null, users, "제목", "주소", 0, null);
        walkingPathsRepository.save(entity);
    }
}
