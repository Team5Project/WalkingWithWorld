package com.team5.WalkingWithWorld.visitors.dto;

import lombok.Getter;

@Getter
public class VisitorsResponseDTO {
    private Long id;
    private String name;
    private String password;
    private String content;

    public VisitorsResponseDTO(String content){
        this.content = content;

    }
}
