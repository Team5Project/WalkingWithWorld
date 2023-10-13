package com.team5.WalkingWithWorld.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDto {
    private int id;
    private String email;
    private String password;
}
