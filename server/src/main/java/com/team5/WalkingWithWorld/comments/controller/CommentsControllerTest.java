package com.team5.WalkingWithWorld.comments.controller;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.repository.CommentsRepository;
import com.team5.WalkingWithWorld.comments.service.CommentService;
import com.team5.WalkingWithWorld.comments.service.impl.CommentServiceImpl;
import com.team5.WalkingWithWorld.global.config.auth.CustomPrincipal;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("comments/tested")
public class CommentsControllerTest {

    @Inject
    private final CommentService commentService;

    public CommentsControllerTest(CommentService commentService) {
        this.commentService = commentService;
    }

    //무한스크롤 페이지네이션 소스 테스트
    @GetMapping("{walking-path-id}")
    public PageResponseDto<CommentsDTO> listEntities(@PageableDefault(size=5) Pageable pageable,
                                                     @PathVariable("walking-path-id") Long walkingPathId) {
        return commentService.findAllByWalkingPathsIdOrderByCreatedAtDesc(walkingPathId, pageable);
    }

    @PostMapping
    public CommentsDTO createComment(CommentsDTO dto, int walkingPathsId){ //  int userId,
//        commentServiceImpl.createComment(dto,1,walkingPathsId);
        return dto;
    }

    @PutMapping("/{comments-id}")
    public void modifyComment(@RequestBody CommentsDTO dto, @PathVariable("comments-id") Long commentsId) {
//        commentServiceImpl.updateComment(dto, 1, commentsId);
    }


    @DeleteMapping("{comments-id}")
    public void deleteComment(@PathVariable("comments-id") Long commentId,
                              @AuthenticationPrincipal CustomPrincipal customPrincipal){

    }
}
