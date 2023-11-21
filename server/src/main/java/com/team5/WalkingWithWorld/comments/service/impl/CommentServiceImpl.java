package com.team5.WalkingWithWorld.comments.service.impl;

import com.team5.WalkingWithWorld.comments.dto.CommentsDTO;
import com.team5.WalkingWithWorld.comments.entity.Comments;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.global.pagination.PaginationService;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.comments.repository.CommentsRepository;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import com.team5.WalkingWithWorld.comments.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final UsersRepository usersRepository;
    private final WalkingPathsRepository walkingPathsRepository;
    private final CommentsRepository commentsRepository;
    private final PaginationService paginationService;

    public CommentServiceImpl(UsersRepository usersRepository,
                              WalkingPathsRepository walkingPathsRepository,
                              CommentsRepository commentsRepository,
                              PaginationService paginationService) {
        this.usersRepository = usersRepository;
        this.walkingPathsRepository = walkingPathsRepository;
        this.commentsRepository = commentsRepository;
        this.paginationService = paginationService;
    }

    @Override
    public List<CommentsDTO> getAllCommentsByWalkingPathsId(Long walkingPathsId){
        return commentsRepository.findAllByWalkingPathsId(walkingPathsId).stream().map(CommentsDTO::from).collect(Collectors.toList());
    }

    @Override
    public PageResponseDto<CommentsDTO> findAllByWalkingPathsIdOrderByCreatedAtDesc(Long walkingPathsId,
                                                                                    Pageable pageable){
        Page<CommentsDTO> commentsDTOS = commentsRepository.findAllByWalkingPathsIdOrderByCreatedAtDesc(walkingPathsId, pageable).map(CommentsDTO::from);
        List<CommentsDTO> commentsDTOList = commentsDTOS.getContent();
        List<Integer> barNumber = paginationService.getPaginationBarNumbers(commentsDTOS.getNumber(), commentsDTOS.getTotalPages());
        return new PageResponseDto<>(commentsDTOList, commentsDTOS, barNumber);
    }

    @Override
    public Comments createComment(CommentsDTO dto, Long userId, Long walkingPathsId){
        Users users = usersRepository.findById(Math.toIntExact(userId)).orElseThrow(()->new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        WalkingPaths walkingPaths = walkingPathsRepository.findById(Math.toIntExact(walkingPathsId)).orElseThrow(()->new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));
        return commentsRepository.save(dto.toEntity(users, walkingPaths));
    }

    @Override
    public Comments updateComment(Long commentId, CommentsDTO dto, String email){
        Users users = usersRepository.findUsersByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Comments comments = commentsRepository.findById(commentId.intValue()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENTS_NOT_FOUND));
        if(!Objects.equals(email, comments.getUsers().getEmail())){
            throw new RuntimeException("사용자가 다릅니다 확인해주세요");
        }
        return commentsRepository.save(dto.toEntity(users, comments.getWalkingPaths()));
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId,String email){
        // 이메일이 있는 사용자 인지 검증하고
        // 삭제 하자 그리고 그 이메일과 댓글 작성자의 이메일이 같은지 확인해서 삭제하자
        Users users = usersRepository.findUsersByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Comments comment = commentsRepository.findById(commentId.intValue()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENTS_NOT_FOUND));
        if(!Objects.equals(email, comment.getUsers().getEmail())){
            throw new RuntimeException("사용자가 다릅니다 확인해주세요");
        }
        commentsRepository.deleteByIdAndUsers(commentId,users);
    }
}
