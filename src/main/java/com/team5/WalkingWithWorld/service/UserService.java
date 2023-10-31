package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.UserMapper;
import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.UsersDTO;
import com.team5.WalkingWithWorld.entity.Users;
import com.team5.WalkingWithWorld.global.PasswordEncoder;
import com.team5.WalkingWithWorld.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersDTO getUserInfo(LoginDto loginDto) {
        LoginDto encryptLogin;
        encryptLogin = loginDto;
        encryptLogin.setPassword(passwordEncoder.encrypt(loginDto.getEmail(), loginDto.getPassword()));
        System.out.println(encryptLogin.getPassword());

        return userMapper.getUser(encryptLogin);
    }

    public List<UsersDTO> getAllUsers() {
        return userMapper.getUsers();
    }

    public boolean createUser(UsersDTO usersDto) {
        String password = passwordEncoder.encrypt(usersDto.getEmail(), usersDto.getPassword());
        usersDto.setPassword(password);
        Users user = usersRepository.save(usersDto.toEntity());
        return userMapper.createUser(usersDto);
    }
}
