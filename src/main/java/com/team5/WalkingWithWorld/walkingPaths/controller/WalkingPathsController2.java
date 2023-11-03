package com.team5.WalkingWithWorld.walkingPaths.controller;

import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import com.team5.WalkingWithWorld.walkingPaths.service.WalkingPathService2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test/walking-path")
public class WalkingPathsController2 {
    private WalkingPathsRepository walkingPathsRepository;
    private WalkingPathService2 walkingPathService;

    WalkingPathsController2(WalkingPathsRepository walkingPathsRepository, WalkingPathService2 walkingPathService) {
        this.walkingPathsRepository = walkingPathsRepository;
        this.walkingPathService = walkingPathService;
    }

    // 전체 리스트
    @GetMapping
    public Page<WalkingPaths> getAll(@PageableDefault(size = 2) Pageable pageable) {
        return walkingPathsRepository.findAll(pageable);
    }
    // 산책로 하나 조회
    @GetMapping("/{id}")
    public WalkingPaths getWalkingPath(@PathVariable(value = "id") int id) {
        WalkingPaths walkingPaths = walkingPathsRepository.findById(id).orElseThrow();
        return walkingPaths;
    }
    // 산책로 검색 필터 이용(searchDTO)
    // 산책로 검색
    // 산책로 작성 폼으로 이동
    // 산책로 작성
    @PostMapping
    public WalkingPaths writeWalkingPath(@RequestBody RequestWalkingPathDTO requestWalkingPath,
                                         @Login UsersDTO usersDTO) {
        return walkingPathService.createWalkingPath(requestWalkingPath,usersDTO);
    }
    // 산책로 수정 폼으로 이동(walking-path-id 참조)
    // 산책로 수정
    // 산책로 삭제
}
