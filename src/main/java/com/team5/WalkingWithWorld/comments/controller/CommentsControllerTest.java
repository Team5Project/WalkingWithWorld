package com.team5.WalkingWithWorld.comments.controller;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.repository.CommentsRepository;
import com.team5.WalkingWithWorld.comments.service.impl.CommentServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments/tested")
public class CommentsControllerTest {
    private CommentServiceImpl commentServiceImpl;

    public CommentsControllerTest(CommentsRepository commentsRepository, CommentServiceImpl commentServiceImpl){
        this.commentServiceImpl = commentServiceImpl;
    }

    //무한스크롤 페이지네이션 소스 테스트
    @GetMapping("{warking-path-id}")
    public Page<CommentsDTO> listEntities(@RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "5") int size,
                                          @PathVariable("warking-path-id") int walkingPathId) {
        PageRequest scrollRequest = PageRequest.of(page, size);
        return commentServiceImpl.findAllByWalkingPathsIdOrderByCreatedAtDesc(walkingPathId, scrollRequest);
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
