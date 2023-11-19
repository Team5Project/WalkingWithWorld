package com.team5.WalkingWithWorld.users.service;

import com.team5.WalkingWithWorld.global.config.auth.CustomAuthorityUtils;
import com.team5.WalkingWithWorld.global.config.auth.CustomPrincipal;
import com.team5.WalkingWithWorld.users.dto.LoginDto;
import com.team5.WalkingWithWorld.users.dto.RequestUsersDTO;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //프로필 조회용
    public UsersDTO getUserById(Long id){
        Users users = usersRepository.findUsersById(id).orElseThrow(()-> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return UsersDTO.from(users);
    }


    //이메일 인증
    @Transactional(readOnly = true)
    public void verifyExistEmail(String email){
        if(usersRepository.findUsersByEmail(email).isPresent()){
            throw new BusinessLogicException(ExceptionCode.USER_EXIST);
        }
    }


    public UsersDTO getUserInfo(LoginDto loginDto) {
        LoginDto encryptLogin;
        encryptLogin = loginDto;
        encryptLogin.setPassword(bCryptPasswordEncoder.encode(loginDto.getPassword()));
        System.out.println(encryptLogin.getPassword());
        Optional<Users> users = usersRepository.findUsersByEmailAndPassword(encryptLogin.getEmail(), encryptLogin.getPassword());

        return UsersDTO.from(users.get());
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
