package com.team5.WalkingWithWorld.comments.service.impl;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.entity.Comments;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.comments.repository.CommentsRepository;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import com.team5.WalkingWithWorld.comments.service.CommentService;
import org.springframework.data.domain.Sort;
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
    public List<CommentsDTO> findTop5ByWalkingPathsIdOrderByCreatedAtDesc(int walkingPathsId){
        return commentsRepository.findTop5ByWalkingPathsIdOrderByCreatedAtDesc(walkingPathsId).stream().map(CommentsDTO::from).collect(Collectors.toList());
    }
    @Override
    public Comments createComment(CommentsDTO dto, int userId, int walkingPathsId){
        Users users = usersRepository.getReferenceById(userId);
        WalkingPaths walkingPaths = walkingPathsRepository.getReferenceById(walkingPathsId);
        return commentsRepository.save(dto.toEntity(users, walkingPaths));
    }

    @Override
    public void deleteComment(Long id){
        commentsRepository.deleteById(id);
    }
}
