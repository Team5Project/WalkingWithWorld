package com.team5.WalkingWithWorld.walkingPaths.controller;

import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.global.dto.RequestMapDTO;
import com.team5.WalkingWithWorld.global.dto.RequestPhotosDTO;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDetailDTO;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import com.team5.WalkingWithWorld.walkingPaths.service.WalkingPathService2;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


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
    public List<ResponseWalkingPathDTO> getAll() {
        return walkingPathService.readAll();
    }
    // 산책로 하나 조회
    @GetMapping("/{id}")
    public ResponseWalkingPathDetailDTO getWalkingPath(@PathVariable(value = "id") int id){
        ResponseWalkingPathDetailDTO dto = null;
        try {
            dto = walkingPathService.readWalkingPath(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dto;
    }
    // 산책로 검색 필터 이용(searchDTO)
    // 산책로 검색
    @PostMapping("/search")
    public List<ResponseWalkingPathDTO> searchWalkingPaths(String keyword) {
        return  walkingPathService.searchByKeyword(keyword);
    }
    // 산책로 작성 폼으로 이동
    // 산책로 작성
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public int writeWalkingPath(@RequestPart RequestDTO requestDTO,
                                @RequestPart List<MultipartFile> files) { // @Login UsersDTO usersDTO
        try {
            return walkingPathService.createWalkingPath(requestDTO.getRequestWalkingPathDTO(), requestDTO.getRequestMapDTO(),files);
        } catch (IOException e) {
            System.out.println("파일 생성중 오류 발생");
        }
        return 0;
    }
    // 산책로 수정 폼으로 이동(walking-path-id 참조)
    // 산책로 수정
    @PutMapping("/{id}")
    public WalkingPaths modifyWalkingPath(@RequestBody RequestWalkingPathDTO requestWalkingPathDTO,
                                          @PathVariable(value = "id") int id) { // @Login UsersDTO usersDTO로 확인하기
        try {
            return walkingPathService.modifyWalkingPath(requestWalkingPathDTO, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    // 산책로 삭제
    @DeleteMapping("/{id}")
    public void deleteWalkingPath(@PathVariable(value = "id") int id) {
        boolean result = walkingPathService.deleteWalkingPath(id);
        if(!result)
            System.out.println("삭제 중 오류 발생");
    }
}
