package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.walkingPaths.controller.WalkingPathsController;
import com.team5.WalkingWithWorld.walkingPaths.service.WalkingPathService2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WalkingPathsController.class)
class TestWalkingPathsController {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalkingPathService2 walkingPathService;

    @Test
    public void getWalkingPath() throws Exception{
    }
}
