package com.team5.WalkingWithWorld.users.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //숫자, 알파벳, 특수문자(!@#$%^&*) 포함 8자 이상 20자 이하
    private String password;

    private String email;

    private String addr;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();



    public static Users of(Long id,
                           String name,
                           String password,
                           String email,
                           String addr,
                           List<String> roles){
        return new Users(null,name, password, email, addr, roles);
    }

    public void updateRole(String roles){
        this.roles.add(roles);
    }
}


