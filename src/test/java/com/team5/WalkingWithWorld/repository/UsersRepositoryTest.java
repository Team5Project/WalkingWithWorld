package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.users.dto.LoginDto;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class UsersRepositoryTest {
    @MockBean
    private UsersRepository usersRepository;
    @Mock
    Users users;
    @Mock
    LoginDto loginDto;

    @Test
    @Order(1)
    @DisplayName("로그인 유저 확인")
    void loginUser(){
        usersRepository.findUsersByEmailAndPassword("test@gmail.com",
                "1iqX8ic/8pptQ6PoGXpaGg==")
                .orElseThrow(()-> new EntityNotFoundException("유저가 없습니다."));
    }

    @Test
    @Order(2)
    @DisplayName("모든 유저 확인")
    void findAll(){
        usersRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Order(3)
    @DisplayName("유저 ID(PK)로 유저 가져오기")
    void findById(){
        Users user = usersRepository.findById(1).orElseThrow(() -> new EntityNotFoundException("유저가 없습니다"));
        System.out.println(user);
    }

    @Test
    @Order(4)
    @DisplayName("회원가입")
    @Transactional
    void insertUsers(){
        Users user = Users.of(
                1,
                "테스트",
                "password123",
                "email@gmail.com",
                "인천"
        );
        usersRepository.save(user);
    }

}