package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.comments.controller.CommentsController;
import com.team5.WalkingWithWorld.comments.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentsController.class)
class CommentsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    void list() {
    }

    @Test
    void writeComment() {
    }

    @Test
    void delete() {
    }
}