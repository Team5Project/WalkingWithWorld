package com.team5.WalkingWithWorld.comments.service;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    List<CommentsDTO> findTop5ByWalkingPathsIdOrderByCreatedAtDesc(int walkingPathsId);
    Page<CommentsDTO> findAllByWalkingPathsIdOrderByCreatedAtDesc(int walkingPathsId,
                                                                  PageRequest scrollRequest);
    Comments createComment(CommentsDTO dto, int userId, int walkingPathsId);
    void deleteComment(Long id);
}
