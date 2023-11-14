package com.team5.WalkingWithWorld.walkingPaths.service.impl;

import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.global.dto.RequestMapDTO;
import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.global.repository.MapRepository;
import com.team5.WalkingWithWorld.global.repository.PhotosRepository;
import com.team5.WalkingWithWorld.global.service.PageService;
import com.team5.WalkingWithWorld.global.service.FileUpload;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDetailDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.WalkingPathsMapDTO;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import com.team5.WalkingWithWorld.walkingPaths.service.WalkingPathsService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WalkingPathServiceImpl implements WalkingPathsService {
    private final WalkingPathsRepository walkingPathsRepository;
    private final UsersRepository usersRepository;
    private final MapRepository mapRepository;
    private final PhotosRepository photosRepository;
    private final FileUpload fileUpload;
    private final PageService pageService;


    public WalkingPathServiceImpl(WalkingPathsRepository walkingPathsRepository,
                                  UsersRepository usersRepository,
                                  MapRepository mapRepository,
                                  PhotosRepository photosRepository,
                                  FileUpload fileUpload,
                                  PageService pageService) {
        this.walkingPathsRepository = walkingPathsRepository;
        this.usersRepository = usersRepository;
        this.mapRepository = mapRepository;
        this.photosRepository = photosRepository;
        this.fileUpload = fileUpload;
        this.pageService = pageService;

    }

    // 페이지 - 전체 가져오기, 검색, 조건 필터
    @Override
    public PageResponseDto<ResponseWalkingPathDTO> getPage(Pageable pageable) {
        Page<WalkingPaths> walkingPaths = walkingPathsRepository.findAllBy(pageable);
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = walkingPaths.stream().map(walkingPath -> ResponseWalkingPathDTO.from(walkingPath, mapRepository.findTop1ByWalkingPaths(walkingPath), photosRepository.findTop1ByWalkingPaths(walkingPath))).collect(Collectors.toList());
        List<Integer> barNumber = pageService.getPaginationBarNumbers(pageable.getPageNumber(), walkingPaths.getTotalPages());
        return new PageResponseDto<>(responseWalkingPathDTOList, walkingPaths, barNumber);
    }
    @Override
    public PageResponseDto<ResponseWalkingPathDTO> getSearchPage(Pageable pageable, String keyword) {
        Page<WalkingPaths> walkingPaths = walkingPathsRepository.findByTitleContainingOrAddrContaining(pageable, keyword, keyword);
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = walkingPaths.stream().map(walkingPath -> ResponseWalkingPathDTO.from(walkingPath, mapRepository.findTop1ByWalkingPaths(walkingPath), photosRepository.findTop1ByWalkingPaths(walkingPath))).toList();
        List<Integer> barNumber = pageService.getPaginationBarNumbers(pageable.getPageNumber(), walkingPaths.getTotalPages());
        return new PageResponseDto<>(responseWalkingPathDTOList, walkingPaths, barNumber);
    }
    // 전체 리스트
    @Override
    public List<ResponseWalkingPathDTO> readAll() {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findAllByOrderByCreatedAtDesc();
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = new ArrayList<>();
        for(WalkingPaths walkingPaths : walkingPathsList) {
            responseWalkingPathDTOList.add(ResponseWalkingPathDTO.from(walkingPaths, mapRepository.findTop1ByWalkingPaths(walkingPaths), photosRepository.findTop1ByWalkingPaths(walkingPaths)));
        }
        return responseWalkingPathDTOList;
    }
    // 산책로 하나 조회
    @Override
    public ResponseWalkingPathDetailDTO readWalkingPath(int id){
        WalkingPaths walkingPaths = walkingPathsRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));
        ResponseWalkingPathDetailDTO dto = ResponseWalkingPathDetailDTO.from(walkingPaths, mapRepository.findByWalkingPaths(walkingPaths), photosRepository.findByWalkingPaths(walkingPaths));
        return dto;
    }
    // 산책로 검색
    @Override
    public List<ResponseWalkingPathDTO> searchByKeyword(String keyword) {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findByTitleContainingOrAddrContaining(keyword, keyword);
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = new ArrayList<>();
        for(WalkingPaths walkingPaths : walkingPathsList) {
            responseWalkingPathDTOList.add(ResponseWalkingPathDTO.from(walkingPaths, mapRepository.findTop1ByWalkingPaths(walkingPaths), photosRepository.findTop1ByWalkingPaths(walkingPaths)));
        }
        return responseWalkingPathDTOList;
    }
    // 산책로 조건 필터
    // 산책로 작성
    //TODO 산책로 작성 시 FileIOException이 발생하여도 트랜잭션이 발동하지 않음
    @Override
    @Transactional
    public WalkingPathsMapDTO createWalkingPath(RequestWalkingPathDTO requestDTO, List<MultipartFile> files){ // , UsersDTO usersDTO
        Users users = usersRepository.getReferenceById(1);
        WalkingPaths entity = WalkingPaths.builder()
                .users(users)
                .title(requestDTO.getTitle())
                .addr(requestDTO.getAddr())
                .build();
        WalkingPaths walkingPaths = walkingPathsRepository.save(entity);
        // 지도
        for(RequestMapDTO dto : requestDTO.getRequestMapDTO()) {
            Map map = Map.from(walkingPaths, dto);
            mapRepository.save(map);
        }
        // 사진
        FileVo fileVo = new FileVo(files);
        java.util.Map<String, String> filesName = null;
        try {
            filesName = fileUpload.upload(fileVo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(java.util.Map.Entry<String, String> entry : filesName.entrySet()) {
            Photos photos = Photos.builder()
                    .walkingPaths(walkingPaths)
                    .imgName(entry.getKey())
                    .imgPath(entry.getValue())
                    .build();
            photosRepository.save(photos);
        }
        return WalkingPathsMapDTO.from(walkingPaths);
    }
    // 산책로 수정
    public void modifyWalkingPath(RequestWalkingPathDTO requestWalkingPathDTO, int walkingPathsId){
        WalkingPaths walkingPaths = walkingPathsRepository.findById(walkingPathsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));

        Optional.ofNullable(requestWalkingPathDTO.getTitle()).ifPresent(walkingPaths::updateTitle);
        Optional.ofNullable(requestWalkingPathDTO.getAddr()).ifPresent(walkingPaths::updateAddr);

        walkingPathsRepository.save(walkingPaths);
    }
    // 산책로 삭제
    @Override
    @Transactional
    public void deleteWalkingPath(int id){
        WalkingPaths walkingPaths = walkingPathsRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));
        //
        mapRepository.deleteAllByWalkingPaths(walkingPaths);
        photosRepository.deleteAllByWalkingPaths(walkingPaths);
        walkingPathsRepository.deleteById(id);
        //
    }
    @Override
    public Page<WalkingPaths> getQ(){
        String keyword = "";
        List<String> location = new ArrayList<>();
        location.add("서울");
        int minTime = 0;
        int maxTime = 100;
        String minDistance = "";
        String maxDistance = "";
        Pageable pageable = PageRequest.of(0, 1);
        return walkingPathsRepository.filterWalkingPaths(keyword,location,minTime,maxTime,minDistance,maxDistance,pageable);
    }
}
