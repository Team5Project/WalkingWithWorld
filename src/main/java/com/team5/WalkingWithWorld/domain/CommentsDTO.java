package com.team5.WalkingWithWorld.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentsDTO  {
    private int id;
    private int walking_paths_id;
    private int users_id;
    private String name;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
}
