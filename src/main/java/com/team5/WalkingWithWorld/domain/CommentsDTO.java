package com.team5.WalkingWithWorld.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentsDTO  {
    private int walkingPathId;
    private int usersId;
    private String name;
    private String content;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
