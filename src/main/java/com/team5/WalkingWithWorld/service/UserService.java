package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.UserMapper;
import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.UsersDto;
import com.team5.WalkingWithWorld.global.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersDto getUserInfo(LoginDto loginDto){
        LoginDto encryptLogin;
        encryptLogin = loginDto;
        encryptLogin.setPassword(passwordEncoder.encrypt(loginDto.getEmail(), loginDto.getPassword()));
        System.out.println(encryptLogin.getPassword());

        return userMapper.getUser(encryptLogin);
    }

    public List<UsersDto> getAllUsers(){
        return userMapper.getUsers();
    }

    public boolean createUser(UsersDto usersDto){
        UsersDto encryptUser;
        encryptUser = usersDto;
        String password = passwordEncoder.encrypt(usersDto.getEmail(),usersDto.getPassword());
        encryptUser.setPassword(password);

        System.out.println(password);

        return userMapper.createUser(encryptUser);
    }
}
