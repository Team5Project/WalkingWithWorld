package com.team5.WalkingWithWorld.comments.controller;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.repository.CommentsRepository;
import com.team5.WalkingWithWorld.comments.service.impl.CommentServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments/tested")
public class CommentsControllerTest {
    private CommentServiceImpl commentServiceImpl;

    public CommentsControllerTest(CommentsRepository commentsRepository, CommentServiceImpl commentServiceImpl){
        this.commentServiceImpl = commentServiceImpl;
    }

    @GetMapping("{warking-path-id}")
    public List<CommentsDTO> findTop5ByWalkingPathsIdOrderByCreatedAtDesc(@PathVariable("warking-path-id")int id){
        return commentServiceImpl.findTop5ByWalkingPathsIdOrderByCreatedAtDesc(id);
    }

    @PostMapping("/")
    public CommentsDTO createComment(CommentsDTO dto, int userId, int walkingPathsId){
        commentServiceImpl.createComment(dto,userId,walkingPathsId);
        return dto;
    }

    @DeleteMapping("{id}")
    public Long deleteComment(@PathVariable("id") Long id){
        commentServiceImpl.deleteComment(id);
        return id;
    }
}
