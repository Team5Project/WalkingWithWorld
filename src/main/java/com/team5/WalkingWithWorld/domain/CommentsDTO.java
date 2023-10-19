package com.team5.WalkingWithWorld.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentsDTO  {
    private int id;
    private int walking_paths_id;
    private int users_id;
    private String name;

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
