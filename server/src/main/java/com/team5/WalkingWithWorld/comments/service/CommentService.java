package com.team5.WalkingWithWorld.comments.service;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.entity.Comments;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    List<CommentsDTO> getAllCommentsByWalkingPathsId(Long walkingPathsId);

    PageResponseDto<CommentsDTO> findAllByWalkingPathsIdOrderByCreatedAtDesc(Long walkingPathsId,
                                                                             Pageable pageable);

    Comments createComment(CommentsDTO dto, Long userId, Long walkingPathsId);

    Comments updateComment(Long id, CommentsDTO dto, String email);

    void deleteComment(Long id,String email);

}
