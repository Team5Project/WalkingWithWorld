package com.team5.WalkingWithWorld.comments.service.impl;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.entity.Comments;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.comments.repository.CommentsRepository;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import com.team5.WalkingWithWorld.comments.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final UsersRepository usersRepository;
    private final WalkingPathsRepository walkingPathsRepository;
    private final CommentsRepository commentsRepository;

    public CommentServiceImpl(UsersRepository usersRepository,
                              WalkingPathsRepository walkingPathsRepository,
                              CommentsRepository commentsRepository) {
        this.usersRepository = usersRepository;
        this.walkingPathsRepository = walkingPathsRepository;
        this.commentsRepository = commentsRepository;
    }

    @Override
    public List<CommentsDTO> getAllCommentsByWalkingPathsId(Long walkingPathsId){
        return commentsRepository.findAllByWalkingPathsId(walkingPathsId).stream().map(CommentsDTO::from).collect(Collectors.toList());
    }
    @Override
    public Comments createComment(CommentsDTO dto, Long userId, Long walkingPathsId){
        Users users = usersRepository.findById(Math.toIntExact(userId)).orElseThrow(()->new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        WalkingPaths walkingPaths = walkingPathsRepository.findById(Math.toIntExact(walkingPathsId)).orElseThrow(()->new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));
        return commentsRepository.save(dto.toEntity(users, walkingPaths));
    }

    @Override
    public void deleteComment(Long id){
        commentsRepository.deleteById(Math.toIntExact(id));
    }
}
