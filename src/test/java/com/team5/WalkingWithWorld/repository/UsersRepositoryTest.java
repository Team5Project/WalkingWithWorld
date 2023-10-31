package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.entity.Users;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class UsersRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;
    @Mock
    Users users;
    @Mock
    LoginDto loginDto;

    @Test
    @Order(1)
    @DisplayName("로그인 유저 확인")
    void loginUser(){
        usersRepository.findUsersByEmailAndPassword(loginDto.getEmail(),
                loginDto.getPassword())
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
        usersRepository.findById(users.getId()).orElseThrow(() -> new EntityNotFoundException("유저가 없습니다"));
    }

    @Test
    @Order(4)
    @DisplayName("회원가입")
    @Transactional
    void insertUsers(){
        Users user = new Users();
//        user.setEmail("email@gmail.com");
//        user.setName("유저");
//        user.setPassword("!password1234");
//        user.setAddr("노원구");
        usersRepository.save(user);
    }

}