package com.team5.WalkingWithWorld.walkingPaths.service;

import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import org.springframework.stereotype.Service;

@Service
public class WalkingPathService2 {
    private final WalkingPathsRepository walkingPathsRepository;
    private final UsersRepository usersRepository;

    public WalkingPathService2(WalkingPathsRepository walkingPathsRepository, UsersRepository usersRepository) {
        this.walkingPathsRepository = walkingPathsRepository;
        this.usersRepository = usersRepository;
    }

    public WalkingPaths createWalkingPath(RequestWalkingPathDTO requestWalkingPath, UsersDTO usersDTO) {
        Users users = usersRepository.getReferenceById(usersDTO.getId());
        WalkingPaths entity = WalkingPaths.builder()
                .users(users)
                .title(requestWalkingPath.getTitle())
                .addr(requestWalkingPath.getAddr())
                .build();
        WalkingPaths walkingPaths = walkingPathsRepository.save(entity);
        return walkingPaths;
    }
}
