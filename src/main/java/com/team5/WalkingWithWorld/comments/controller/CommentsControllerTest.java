package com.team5.WalkingWithWorld.comments.controller;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.repository.CommentsRepository;
import com.team5.WalkingWithWorld.comments.service.impl.CommentServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping("{walking-path-id}")
    public Page<CommentsDTO> listEntities(@PageableDefault(size=5) Pageable pageable,
                                          @PathVariable("walking-path-id") int walkingPathId) {
        return commentServiceImpl.findAllByWalkingPathsIdOrderByCreatedAtDesc(walkingPathId, pageable);
    }

    @PostMapping
    public CommentsDTO createComment(CommentsDTO dto, int walkingPathsId){ //  int userId,
        commentServiceImpl.createComment(dto,1,walkingPathsId);
        return dto;
    }

    @PutMapping("/{comments-id}")
    public void modifyComment(@RequestBody CommentsDTO dto, @PathVariable("comments-id") Long commentsId) {
        commentServiceImpl.updateComment(dto, 1, commentsId);
    }


    @DeleteMapping("{id}")
    public Long deleteComment(@PathVariable("id") Long id){
        commentServiceImpl.deleteComment(id);
        return id;
    }
}
