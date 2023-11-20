package com.team5.WalkingWithWorld.walkingPaths.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.global.pagination.PaginationService;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDetailDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.WalkingPathsMapDTO;
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
@CrossOrigin(origins = "http://localhost:5173") // 허용할 오리진을 지정
public class WalkingPathsController {
    private final WalkingPathServiceImpl walkingPathService;
    private final PaginationService paginationService;

    public WalkingPathsController(WalkingPathServiceImpl walkingPathService,
                                  PaginationService paginationService) {
        this.walkingPathService = walkingPathService;
        this.paginationService = paginationService;
    }

    // 전체 리스트(페이지)
    @GetMapping
    public ResponseEntity getWalkingPathsPage(@PageableDefault Pageable pageable) {
        PageResponseDto<ResponseWalkingPathDTO> pageResponseDto = walkingPathService.getPage(pageable);
        return new ResponseEntity(pageResponseDto, HttpStatus.OK);
    }

    // 산책로 하나 조회
    @GetMapping("/{id}")
    public ResponseEntity getWalkingPath(@PathVariable(value = "id") long id){
        walkingPathService.updateView(id);
        ResponseWalkingPathDetailDTO responseWalkingPathDetailDTO = walkingPathService.readWalkingPath(id);
        return new ResponseEntity(responseWalkingPathDetailDTO, HttpStatus.OK);
    }

    // 산책로 검색(페이지)
    @GetMapping("/search")
    public ResponseEntity serachWalkingPathsPage(@PageableDefault Pageable pageable, String keyword) {
        PageResponseDto<ResponseWalkingPathDTO> pageResponseDto = walkingPathService.getSearchPage(keyword,pageable);
        return new ResponseEntity(pageResponseDto, HttpStatus.OK);
    }
    // 산책로 검색 필터 이용 : 위랑 합치기?
    @GetMapping("/filter")
    public ResponseEntity searchConditionWalkingPathsPage(@PageableDefault Pageable pageable, String keyword, String filters) {
        PageResponseDto<ResponseWalkingPathDTO> pageResponseDto = walkingPathService.searchConditionPage(keyword, filters, pageable);
        return new ResponseEntity(pageResponseDto, HttpStatus.OK);
    }

    // 산책로 작성 폼으로 이동
    // 산책로 작성
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity writeWalkingPath(@RequestPart("requestDTO") RequestWalkingPathDTO requestDTO,
                                           @RequestPart(value = "files",required = false) List<MultipartFile> files) { // @Login UsersDTO usersDTO  @AuthenticationPrincipal CustomPrincipal customPrincipal
        System.out.println(requestDTO);
        System.out.println(files);

        WalkingPathsMapDTO walkingPaths = walkingPathService.createWalkingPath(requestDTO, files);
        return new ResponseEntity(walkingPaths, HttpStatus.CREATED);
    }

    // 산책로 수정 폼으로 이동(walking-path-id 참조)
    // 산책로 수정
    @PutMapping("/{id}")
    public ResponseEntity modifyWalkingPath(@RequestBody RequestWalkingPathDTO requestWalkingPathDTO,
                                          @PathVariable(value = "id") int id) { // @AuthenticationPrincipal CustomPrincipal customPrincipal
        walkingPathService.modifyWalkingPath(requestWalkingPathDTO, id);
        return new ResponseEntity(id, HttpStatus.RESET_CONTENT);
    }

    // 산책로 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteWalkingPath(@PathVariable(value = "id") int id) { // @AuthenticationPrincipal CustomPrincipal customPrincipal
        walkingPathService.deleteWalkingPath(id);
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }

//    //Test queryDSL
//    @GetMapping("/test")
//    public PageResponseDto<WalkingPathsMapDTO> getQ(@RequestParam(required = false) String keyword) {
//        return walkingPathService.getQ(keyword);
//    }
}
