package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.Visitors;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class VisitorsRepositoryTest {
    @Autowired
    private VisitorsRepository repository;

    @Test
    @Order(1)
    @DisplayName("visitorslist")
    void findAll(){

        repository.findAll().forEach(System.out::println);
    }
    @Test
    @Order(2)
    @DisplayName("insertvisitors")
    void insertVisitors(){
        Visitors visitor = new Visitors();
        visitor.setId(10);
        visitor.setName("test10");
        visitor.setContent("테스트10");
        visitor.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        visitor.setPassword("qwer");
        repository.save(visitor);
        repository.findAll().forEach(System.out::println);
    }
    @Test
    @Order(3)
    @DisplayName("deletevisitors")
    void deleteVisitors(){
        repository.deleteByIdAndPassword(2, "qwerqwer");
        repository.findAll().forEach(System.out::println);

    }

}