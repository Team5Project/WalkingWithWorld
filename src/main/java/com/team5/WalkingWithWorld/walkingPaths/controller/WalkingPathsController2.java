package com.team5.WalkingWithWorld.walkingPaths.controller;

import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDetailDTO;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import com.team5.WalkingWithWorld.walkingPaths.service.WalkingPathService2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return walkingPathService.readWalkingPath(id);
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
    public int writeWalkingPath(@RequestPart RequestWalkingPathDTO requestDTO,
                                @RequestPart List<MultipartFile> files) { // @Login UsersDTO usersDTO
        return walkingPathService.createWalkingPath(requestDTO, files);
    }
    // 산책로 수정 폼으로 이동(walking-path-id 참조)
    // 산책로 수정
    @PutMapping("/{id}")
    public int modifyWalkingPath(@RequestBody RequestWalkingPathDTO requestWalkingPathDTO,
                                          @PathVariable(value = "id") int id) { // @Login UsersDTO usersDTO로 확인하기
        walkingPathService.modifyWalkingPath(requestWalkingPathDTO, id);
        return id;
    }
    // 산책로 삭제
    @DeleteMapping("/{id}")
    public void deleteWalkingPath(@PathVariable(value = "id") int id) {
       walkingPathService.deleteWalkingPath(id);
    }
}
