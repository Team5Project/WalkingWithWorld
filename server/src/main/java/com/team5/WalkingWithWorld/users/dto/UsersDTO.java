package com.team5.WalkingWithWorld.users.dto;

import com.team5.WalkingWithWorld.users.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@Builder(access = AccessLevel.PROTECTED)
public class UsersDTO {
    private Long id;
    @NotBlank(message = "아이디 입력은 필수입니다.")
    @Length(max = 30, message = "아이디 길이는 30자 를 넘으면 안됩니다.")
    private String name;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,20}$")
    //숫자, 알파벳, 특수문자(!@#$%^&*) 포함 8자 이상 20자 이하
    private String password;

    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Length(max = 30, message = "이메일 길이는 30자 를 넘으면 안됩니다.")
    @Email(message = "이메일 형식을 지켜야 합니다.")
    private String email;

    @NotBlank(message = "주소 입력은 필수입니다.")
    @Length(max = 100, message = "주소 길이는 100자 를 넘으면 안됩니다.")
    private String addr;

    public static UsersDTO from(Users users){
        return UsersDTO.builder()
                .id(users.getId())
                .name(users.getName())
                .password(users.getPassword())
                .email(users.getEmail())
                .addr(users.getAddr())
                .build();
    }


    public Users toEntity(){
        return Users.of(
                id,
                name,
                password,
                email,
                addr
        );
    }
}
