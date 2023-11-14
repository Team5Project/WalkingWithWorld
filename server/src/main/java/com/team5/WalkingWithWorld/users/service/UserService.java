package com.team5.WalkingWithWorld.users.service;

import com.team5.WalkingWithWorld.global.config.auth.CustomAuthorityUtils;
import com.team5.WalkingWithWorld.users.dto.LoginDto;
import com.team5.WalkingWithWorld.users.dto.RequestUsersDTO;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UsersRepository usersRepository;
    private final CustomAuthorityUtils customAuthorityUtils;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UsersRepository usersRepository, CustomAuthorityUtils customAuthorityUtils, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.customAuthorityUtils = customAuthorityUtils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UsersDTO getUserById(int id){
        Users users = usersRepository.findById(id).orElseThrow(()-> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return UsersDTO.from(users);
    }

    public UsersDTO getUserInfo(LoginDto loginDto) {
        LoginDto encryptLogin;
        encryptLogin = loginDto;
        encryptLogin.setPassword(bCryptPasswordEncoder.encode(loginDto.getPassword()));
        System.out.println(encryptLogin.getPassword());
        Optional<Users> users = usersRepository.findUsersByEmailAndPassword(encryptLogin.getEmail(), encryptLogin.getPassword());

        return UsersDTO.from(users.get());
    }

    public List<UsersDTO> getAllUsers() {
        List<UsersDTO> userList = usersRepository.findAll().stream().map(UsersDTO::from).collect(Collectors.toList());
        return userList;
    }

    public Users createUser(RequestUsersDTO usersDto) {
        String password = bCryptPasswordEncoder.encode(usersDto.getPassword());
        usersDto.setPassword(password);
        UsersDTO dto = RequestUsersDTO.toDTO(usersDto);
        List<String> roles = customAuthorityUtils.createRoles(dto.getEmail());
        Users users = dto.toEntity();
        users.updateRole(roles.get(0));
        System.out.println(users);
        return usersRepository.save(users);
    }
}
