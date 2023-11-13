package com.team5.WalkingWithWorld.walkingPaths.controller;

import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDetailDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.WalkingPathsMapDTO;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.service.impl.WalkingPathServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/walking-path")
public class WalkingPathsController {
    private final WalkingPathServiceImpl walkingPathService;

    WalkingPathsController(WalkingPathServiceImpl walkingPathService) {
        this.walkingPathService = walkingPathService;
    }
    // 전체 리스트 페이지
    @GetMapping("/page")
    public PageResponseDto<ResponseWalkingPathDTO> getWalkingPathsPage(@PageableDefault Pageable pageable) {
        return walkingPathService.getPage(pageable);
    }
    // 산책로 검색 페이지
    @PostMapping("/search/page")
    public PageResponseDto<ResponseWalkingPathDTO> serachWalkingPathsPage(@PageableDefault Pageable pageable, String keyword) {
        return walkingPathService.getSearchPage(pageable, keyword);
    }
    // 전체 리스트
    @GetMapping
    public List<ResponseWalkingPathDTO> getAll() {
        return walkingPathService.readAll();
    }
    // 산책로 하나 조회
    @GetMapping("/{id}")
    public ResponseEntity getWalkingPath(@PathVariable(value = "id") int id){
        ResponseWalkingPathDetailDTO responseWalkingPathDetailDTO = walkingPathService.readWalkingPath(id);
        return new ResponseEntity(responseWalkingPathDetailDTO, HttpStatus.OK
        );
    }
    // 산책로 검색 필터 이용(searchDTO)
    // 산책로 검색
    @GetMapping("/search")
    public List<ResponseWalkingPathDTO> searchWalkingPaths(String keyword) {
        return  walkingPathService.searchByKeyword(keyword);
    }
    // 산책로 작성 폼으로 이동
    // 산책로 작성
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity writeWalkingPath(@RequestPart RequestWalkingPathDTO requestDTO,
                                           @RequestPart List<MultipartFile> files) { // @Login UsersDTO usersDTO
        WalkingPathsMapDTO walkingPaths = walkingPathService.createWalkingPath(requestDTO, files);
        return new ResponseEntity(walkingPaths, HttpStatus.CREATED);
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
