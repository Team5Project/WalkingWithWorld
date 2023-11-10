package com.team5.WalkingWithWorld.comments.controller;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.repository.CommentsRepository;
import com.team5.WalkingWithWorld.comments.service.impl.CommentServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments/tested")
public class CommentsControllerTest {
    private final CommentServiceImpl commentServiceImpl;

    public CommentsControllerTest(CommentServiceImpl commentServiceImpl){
        this.commentServiceImpl = commentServiceImpl;
    }

    //무한스크롤 페이지네이션 소스 테스트
    @GetMapping("{walking-path-id}")
    public ResponseEntity<Page<CommentsDTO>> listEntities(@PageableDefault(size=5) Pageable pageable,
                                                    @PathVariable("walking-path-id") int walkingPathId) {
        Page<CommentsDTO> comments =  commentServiceImpl.findAllByWalkingPathsIdOrderByCreatedAtDesc(walkingPathId, pageable);
        if(comments.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(comments);
        }
    }

    @PostMapping
    public CommentsDTO createComment(CommentsDTO dto, int walkingPathsId){ //  int userId,
        commentServiceImpl.createComment(dto,1,walkingPathsId);
        return dto;
    }

    @PutMapping("/{comments-id}")
    public ResponseEntity<Void> modifyComment(@RequestBody CommentsDTO dto, @PathVariable("comments-id") Long commentsId) {
        commentServiceImpl.updateComment(dto, 1, commentsId);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }


    @DeleteMapping("{comments-id}")
    public void deleteComment(@PathVariable("comments-id") Long commentId){
        commentServiceImpl.deleteComment(commentId);
    }
}
