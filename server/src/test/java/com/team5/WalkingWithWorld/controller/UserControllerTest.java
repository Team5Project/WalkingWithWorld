package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest()
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void loginIndex() {
    }

    @Test
    void logoutIndex() {
    }

    @Test
    void loginUser() {
    }

//    @Test
//    void index() throws Exception {
//        List<Users> usersList = List.of(Users.of(null, "AAA", "AAABBB123", "AAA@gmail.com", "AAA"));
//        mockMvc.perform(get("/users"))
//                .andExpect(status().is(200));
//    }

    @Test
    void signUpUser() {
    }
}