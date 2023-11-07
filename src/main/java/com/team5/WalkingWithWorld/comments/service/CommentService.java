package com.team5.WalkingWithWorld.comments.service;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.entity.Comments;

import java.util.List;

public interface CommentService {
    List<CommentsDTO> findTop5ByWalkingPathsIdOrderByCreatedAtDesc(int walkingPathsId);

    Comments createComment(CommentsDTO dto, int userId, int walkingPathsId);
    void deleteComment(Long id);
}
