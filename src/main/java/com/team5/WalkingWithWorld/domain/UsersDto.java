package com.team5.WalkingWithWorld.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsersDto {
    private int id;
    private String name;
    private String password;
    private String email;
    private String addr;
}
