package com.team5.WalkingWithWorld.comments.dto;

import com.team5.WalkingWithWorld.comments.entity.Comments;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentsDTO  {
    private int id;
    private int walkingPathsId;
    private int usersId;
    private String name;

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentsDTO from(Comments comments){
        return CommentsDTO.builder()
                .id(comments.getId())
                .walkingPathsId(comments.getWalkingPaths().getId())
                .usersId(comments.getUsers().getId())
                .name(comments.getUsers().getName())
                .content(comments.getContent())
                .createdAt(comments.getCreatedAt())
                .modifiedAt(comments.getModifiedAt())
                .build();
    }

    public  Comments toEntity(Users user, WalkingPaths walkingPaths){
        return Comments.of(
                id,
                user,
                walkingPaths,
                content,
                createdAt,
                name,
                null,
                null
        );
    }
}
