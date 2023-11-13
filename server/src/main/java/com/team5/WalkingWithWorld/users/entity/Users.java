package com.team5.WalkingWithWorld.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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



    public static Users of(Long id,
                           String name,
                           String password,
                           String email,
                           String addr){
        return new Users(null,name, password, email, addr);
    }
}


