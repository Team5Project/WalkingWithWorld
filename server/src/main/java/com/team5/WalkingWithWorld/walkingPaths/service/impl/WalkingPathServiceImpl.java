package com.team5.WalkingWithWorld.walkingPaths.service.impl;

import com.team5.WalkingWithWorld.global.config.auth.CustomPrincipal;
import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.global.entity.Coordinate;
import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.global.repository.CoordinateRepository;
import com.team5.WalkingWithWorld.global.repository.MapRepository;
import com.team5.WalkingWithWorld.global.repository.PhotosRepository;
import com.team5.WalkingWithWorld.global.service.FileUpload;
import com.team5.WalkingWithWorld.global.service.PageService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WalkingPathServiceImpl implements WalkingPathsService {
    private final WalkingPathsRepository walkingPathsRepository;
    private final UsersRepository usersRepository;
    private final MapRepository mapRepository;
    private final PhotosRepository photosRepository;
    private final CoordinateRepository coordinateRepository;
    private final FileUpload fileUpload;
    private final PageService pageService;


    public WalkingPathServiceImpl(WalkingPathsRepository walkingPathsRepository,
                                  UsersRepository usersRepository,
                                  MapRepository mapRepository,
                                  PhotosRepository photosRepository,
                                  CoordinateRepository coordinateRepository,
                                  FileUpload fileUpload,
                                  PageService pageService) {
        this.walkingPathsRepository = walkingPathsRepository;
        this.usersRepository = usersRepository;
        this.mapRepository = mapRepository;
        this.photosRepository = photosRepository;
        this.coordinateRepository = coordinateRepository;
        this.fileUpload = fileUpload;
        this.pageService = pageService;

    }

    // 페이지 - 전체 가져오기
    @Override
    public PageResponseDto<ResponseWalkingPathDTO> getPage(Pageable pageable) {
        Page<WalkingPaths> walkingPaths = walkingPathsRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = walkingPaths.stream().map(walkingPath -> ResponseWalkingPathDTO.from(walkingPath, mapRepository.findByWalkingPaths(walkingPath), photosRepository.findTop1ByWalkingPaths(walkingPath))).collect(Collectors.toList());
        List<Integer> barNumber = pageService.getPaginationBarNumbers(pageable.getPageNumber(), walkingPaths.getTotalPages());
        return new PageResponseDto<>(responseWalkingPathDTOList, walkingPaths, barNumber);
    }

    // 페이지 - 검색
    @Override
    public PageResponseDto<ResponseWalkingPathDTO> getSearchPage(String keyword, Pageable pageable) {
        Page<WalkingPaths> walkingPaths = walkingPathsRepository.findByTitleContainingOrderByCreatedAtDesc(keyword, pageable);
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = walkingPaths.stream().map(walkingPath -> ResponseWalkingPathDTO.from(walkingPath, mapRepository.findByWalkingPaths(walkingPath), photosRepository.findTop1ByWalkingPaths(walkingPath))).toList();
        List<Integer> barNumber = pageService.getPaginationBarNumbers(pageable.getPageNumber(), walkingPaths.getTotalPages());
        return new PageResponseDto<>(responseWalkingPathDTOList, walkingPaths, barNumber);
    }

    // 페이지 - 산책로 조건 필터
    @Override
    public PageResponseDto<ResponseWalkingPathDTO> searchConditionPage(String keyword, String filters, Pageable pageable) {
        List<String> locations;
        HashMap<String, Integer> filtersMap;
        if(keyword.isEmpty() || keyword.isBlank())
            keyword = null;

        try {
            String[] filterAry = divideFilters(filters);
            locations = readLocation(filterAry[0]);
            filtersMap = readFilters(filterAry[1]);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Page<WalkingPaths> walkingPaths = walkingPathsRepository.filterWalkingPaths(keyword, locations, filtersMap.get("minTime"), filtersMap.get("maxTime"), String.valueOf(filtersMap.get("minDistance")), String.valueOf(filtersMap.get("maxDistance")), pageable);
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = walkingPaths.stream().map(walkingPath -> ResponseWalkingPathDTO.from(walkingPath, mapRepository.findByWalkingPaths(walkingPath), photosRepository.findTop1ByWalkingPaths(walkingPath))).toList();
        List<Integer> barNumber = pageService.getPaginationBarNumbers(pageable.getPageNumber(), walkingPaths.getTotalPages());
        return new PageResponseDto<>(responseWalkingPathDTOList, walkingPaths, barNumber);
    }

    public String[] divideFilters(String filters) throws UnsupportedEncodingException {
        String filterString = URLDecoder.decode(filters, "utf-8");
        int idx = filterString.indexOf("|");
        String[] filterAry = new String[2];
        filterAry[0] = filterString.substring(0, idx);
        filterAry[1] = filterString.substring(idx + 1);
        return filterAry;
    }

    public List<String> readLocation(String location) {
        // location[]
        String[] readDetail = location.split(":");
        List<String> locations;
        if (readDetail.length < 2)
            locations = null;
        else if (readDetail[1].contains(",")) {
            String[] locationAry = readDetail[1].split(",");
            locations = Arrays.stream(locationAry).toList();
        } else
            locations = new ArrayList<>() {{
                add(readDetail[1]);
            }};

        return locations;
    }

    public HashMap<String, Integer> readFilters(String filters) {
        // (minTime, maxTime, minDistance, maxDistance)
        String[] filterAry = filters.split("\\|");
        HashMap<String, Integer> filterMap = new HashMap<>();
        for (String detail : filterAry) {
            String[] readDetail = detail.split(":");
            filterMap.put(readDetail[0], Integer.valueOf(readDetail[1]));
        }
        return filterMap;
    }

    // 산책로 하나 조회
    @Override
    public ResponseWalkingPathDetailDTO readWalkingPath(long id) {
        WalkingPaths walkingPaths = walkingPathsRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));
        ResponseWalkingPathDetailDTO dto = ResponseWalkingPathDetailDTO.from(walkingPaths.getUsers().getId(), walkingPaths, mapRepository.findByWalkingPaths(walkingPaths), coordinateRepository.findByWalkingPaths(walkingPaths), photosRepository.findByWalkingPaths(walkingPaths));
        return dto;
    }

    // 산책로 작성
    //TODO 산책로 작성 시 FileIOException이 발생하여도 트랜잭션이 발동하지 않음
    @Override
    @Transactional
    public WalkingPathsMapDTO createWalkingPath(RequestWalkingPathDTO requestDTO, List<MultipartFile> files) { // , UsersDTO usersDTO
        Users users = usersRepository.getReferenceById(1);
        WalkingPaths entity = WalkingPaths.builder()
                .users(users)
                .title(requestDTO.getTitle())
                .addr(requestDTO.getAddr())
                .build();
        WalkingPaths walkingPaths = walkingPathsRepository.save(entity);
        // 지도
        if(requestDTO.getRequestMapDTO().getDistance() != null) {
            Map map = Map.from(walkingPaths, requestDTO.getRequestMapDTO());
            mapRepository.save(map);
            for (int i = 0; i < requestDTO.getRequestMapDTO().getCoordinateX().size(); i++) {
                Coordinate coordinate = Coordinate.of(walkingPaths, requestDTO.getRequestMapDTO().getCoordinateX().get(i), requestDTO.getRequestMapDTO().getCoordinateY().get(i));
                coordinateRepository.save(coordinate);
            }
        }
        // 사진
        if (files != null) {
            FileVo fileVo = new FileVo(files);
            java.util.Map<String, String> filesName = null;
            try {
                filesName = fileUpload.upload(fileVo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (java.util.Map.Entry<String, String> entry : filesName.entrySet()) {
                Photos photos = Photos.builder()
                        .walkingPaths(walkingPaths)
                        .imgName(entry.getKey())
                        .imgPath(entry.getValue())
                        .build();
                photosRepository.save(photos);
            }
        }
        return WalkingPathsMapDTO.from(walkingPaths);
    }

    // 산책로 수정
    @Override
    @Transactional
    public void modifyWalkingPath(RequestWalkingPathDTO requestWalkingPathDTO, int walkingPathsId, List<MultipartFile> files) {
        WalkingPaths walkingPaths = walkingPathsRepository.findById(walkingPathsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));

        Optional.ofNullable(requestWalkingPathDTO.getTitle()).ifPresent(walkingPaths::updateTitle);
        Optional.ofNullable(requestWalkingPathDTO.getAddr()).ifPresent(walkingPaths::updateAddr);

        WalkingPaths entity = walkingPathsRepository.save(walkingPaths);
        // 지도
        if(requestWalkingPathDTO.getRequestMapDTO().getDistance() != null) {
            Map map = mapRepository.findByWalkingPaths(entity);
            if(map != null) {
                map.updateTime(requestWalkingPathDTO.getRequestMapDTO().getTime());
                map.updateDistance(requestWalkingPathDTO.getRequestMapDTO().getDistance());
                coordinateRepository.deleteAllByWalkingPaths(entity);
            }
            else {
                map = Map.from(entity, requestWalkingPathDTO.getRequestMapDTO());
            }
            mapRepository.save(map);
            for (int i = 0; i < requestWalkingPathDTO.getRequestMapDTO().getCoordinateX().size(); i++) {
                Coordinate coordinate = Coordinate.of(entity, requestWalkingPathDTO.getRequestMapDTO().getCoordinateX().get(i), requestWalkingPathDTO.getRequestMapDTO().getCoordinateY().get(i));
                coordinateRepository.save(coordinate);
            }
        }
        // 사진
        if (files != null) {
            FileVo fileVo = new FileVo(files);
            java.util.Map<String, String> filesName = null;
            try {
                filesName = fileUpload.upload(fileVo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            photosRepository.deleteAllByWalkingPaths(entity);
            for (java.util.Map.Entry<String, String> entry : filesName.entrySet()) {
                Photos photos = Photos.builder()
                        .walkingPaths(walkingPaths)
                        .imgName(entry.getKey())
                        .imgPath(entry.getValue())
                        .build();
                photosRepository.save(photos);
            }
        }
    }

    // 조회수 업데이트
    @Override
    @Transactional
    public void updateView(Long walkingPathsId) {
        WalkingPaths walkingPaths = walkingPathsRepository.findById(walkingPathsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));
        walkingPathsRepository.updateView(walkingPathsId);
    }

    // 산책로 삭제
    @Override
    @Transactional
    public void deleteWalkingPath(int id, CustomPrincipal customPrincipal) {
        WalkingPaths walkingPaths = walkingPathsRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));
        if(customPrincipal == null || !Objects.equals(walkingPaths.getUsers().getId(), customPrincipal.userId()))
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        mapRepository.deleteAllByWalkingPaths(walkingPaths);
        photosRepository.deleteAllByWalkingPaths(walkingPaths);
        coordinateRepository.deleteAllByWalkingPaths(walkingPaths);
        walkingPathsRepository.deleteById(id);
    }
//    @Override
//    public PageResponseDto getQ(String keyword){
//        List<String> location = new ArrayList<>();
//        location.add("주소");
//        System.out.println("위치 리스트 : " + location);
//        int minTime = 0;
//        int maxTime = 1000;
//        String minDistance = "0";
//        String maxDistance = "1000";
//        Pageable pageable = PageRequest.of(0, 1);
//        Page<WalkingPaths> walkingPathsMapDTOPage = walkingPathsRepository.filterWalkingPaths(keyword,null,minTime,maxTime,minDistance,maxDistance,pageable);
//        List<WalkingPaths> list = walkingPathsMapDTOPage.getContent();
//        List<WalkingPathsMapDTO> walkingPathsMapDTOList = new ArrayList<>();
//        for(WalkingPaths w : list){
//            walkingPathsMapDTOList.add(WalkingPathsMapDTO.from(w, photosRepository.findByWalkingPaths(w),mapRepository.findByWalkingPaths(w)));
//        }
//        List<Integer> barNumber = pageService.getPaginationBarNumbers(walkingPathsMapDTOPage.getNumber(), walkingPathsMapDTOPage.getTotalPages());
//
//        return new PageResponseDto(list, walkingPathsMapDTOPage, barNumber);
//    }
}
