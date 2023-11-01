package com.team5.WalkingWithWorld.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    //숫자, 알파벳, 특수문자(!@#$%^&*) 포함 8자 이상 20자 이하
    private String password;

    private String email;

    private String addr;



    public static Users of(int id,
                           String name,
                           String password,
                           String email,
                           String addr){
        return new Users(id,name, password, email, addr);
    }



}


