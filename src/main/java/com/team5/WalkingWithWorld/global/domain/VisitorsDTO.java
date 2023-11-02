package com.team5.WalkingWithWorld.global.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class VisitorsDTO {
    private int id;
    private String name;
    private String content;
    private LocalDateTime createdAt;
    private String password;
}


