package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.UserMapper;
import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.Users;
import com.team5.WalkingWithWorld.domain.UsersDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Users getUserInfo(LoginDto loginDto){
        return userMapper.getUser(loginDto);
    }

    public List<UsersDto> getAllUsers(){
        return userMapper.getUsers();
    }

    public boolean createUser(UsersDto usersDto){
        return userMapper.createUser(usersDto);
    }
}
