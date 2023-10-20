package com.team5.WalkingWithWorld.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentsDTO  {
    private int id;
    private int walkingPathsId;
    private int users_id;
    private String name;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
