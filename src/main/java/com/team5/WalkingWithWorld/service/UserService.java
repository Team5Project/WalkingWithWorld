package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.UsersDTO;
import com.team5.WalkingWithWorld.entity.Users;
import com.team5.WalkingWithWorld.global.PasswordEncoder;
import com.team5.WalkingWithWorld.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UsersDTO getUserById(int id){
        return UsersDTO.from(usersRepository.getReferenceById(id));
    }

    public UsersDTO getUserInfo(LoginDto loginDto) {
        LoginDto encryptLogin;
        encryptLogin = loginDto;
        encryptLogin.setPassword(passwordEncoder.encrypt(loginDto.getEmail(), loginDto.getPassword()));
        System.out.println(encryptLogin.getPassword());
        Optional<Users> users = usersRepository.findUsersByEmailAndPassword(encryptLogin.getEmail(), encryptLogin.getPassword());

        return UsersDTO.from(users.get());
    }

    public List<UsersDTO> getAllUsers() {
        List<UsersDTO> userList = usersRepository.findAll().stream().map(UsersDTO::from).collect(Collectors.toList());
        return userList;
    }

    public Users createUser(UsersDTO usersDto) {
        String password = passwordEncoder.encrypt(usersDto.getEmail(), usersDto.getPassword());
        usersDto.setPassword(password);
        return usersRepository.save(usersDto.toEntity());
    }
}
