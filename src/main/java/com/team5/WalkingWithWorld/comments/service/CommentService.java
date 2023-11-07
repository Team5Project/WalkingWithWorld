package com.team5.WalkingWithWorld.comments.service;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.entity.Comments;

import java.util.List;

public interface CommentService {
    List<CommentsDTO> getAllCommentsByWalkingPathsId(Long walkingPathsId);

    Comments createComment(CommentsDTO dto, Long userId, Long walkingPathsId);

    void deleteComment(Long id);
}
