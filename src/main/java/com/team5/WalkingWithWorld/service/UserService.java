package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.UserMapper;
import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.UsersDTO;
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

    public UsersDTO getUserInfo(LoginDto loginDto){
        LoginDto encryptLogin;
        encryptLogin = loginDto;
        encryptLogin.setPassword(passwordEncoder.encrypt(loginDto.getEmail(), loginDto.getPassword()));
        System.out.println(encryptLogin.getPassword());

        return userMapper.getUser(encryptLogin);
    }

    public List<UsersDTO> getAllUsers(){
        return userMapper.getUsers();
    }

    public boolean createUser(UsersDTO usersDto){
        String password = passwordEncoder.encrypt(usersDto.getEmail(),usersDto.getPassword());
        usersDto.setPassword(password);
        return userMapper.createUser(usersDto);
    }
}
