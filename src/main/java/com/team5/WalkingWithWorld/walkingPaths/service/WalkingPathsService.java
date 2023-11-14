package com.team5.WalkingWithWorld.walkingPaths.service;

import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDetailDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.WalkingPathsMapDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WalkingPathsService {

    // 페이지 - 전체 가져오기, 검색, 조건 필터
    PageResponseDto<ResponseWalkingPathDTO> getPage(Pageable pageable);

    PageResponseDto<ResponseWalkingPathDTO> getSearchPage(String keyword,Pageable pageable);

    // 전체 리스트
//    List<ResponseWalkingPathDTO> readAll();

    // 산책로 하나 조회
    ResponseWalkingPathDetailDTO readWalkingPath(long id);

    // 산책로 검색
//    List<ResponseWalkingPathDTO> searchByKeyword(String keyword);

    // 산책로 조건 필터
    // 산책로 작성
    @Transactional
    WalkingPathsMapDTO createWalkingPath(RequestWalkingPathDTO requestDTO, List<MultipartFile> files);

    // 산책로 수정
    public void modifyWalkingPath(RequestWalkingPathDTO requestWalkingPathDTO, int walkingPathsId);

    // 산책로 삭제
    @Transactional
    void deleteWalkingPath(int id);

    // 조회수 업데이트
    @Transactional
    public void updateView(Long walkingPathsId);
}
