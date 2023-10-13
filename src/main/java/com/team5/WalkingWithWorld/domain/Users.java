package com.team5.WalkingWithWorld.domain;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Users {
    private int id;
    private String name;
    private String password;
    private String email;
    private String addr;
}
