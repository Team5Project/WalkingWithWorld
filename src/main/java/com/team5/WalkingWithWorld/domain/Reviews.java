package com.team5.WalkingWithWorld.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class Reviews {
    private int id;
    private int usersId;
    private int walkingPathsId;
    private String content;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime modified_at;
    private String modified_by;
}
