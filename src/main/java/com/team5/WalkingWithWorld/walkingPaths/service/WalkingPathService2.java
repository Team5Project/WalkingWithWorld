package com.team5.WalkingWithWorld.walkingPaths.service;

import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.global.dto.RequestMapDTO;
import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.global.repository.MapRepository;
import com.team5.WalkingWithWorld.global.repository.PhotosRepository;
import com.team5.WalkingWithWorld.service.FileUpload;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import com.team5.WalkingWithWorld.walkingPaths.dto.RequestWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDTO;
import com.team5.WalkingWithWorld.walkingPaths.dto.ResponseWalkingPathDetailDTO;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WalkingPathService2 {
    private final WalkingPathsRepository walkingPathsRepository;
    private final UsersRepository usersRepository;
    private final MapRepository mapRepository;
    private final PhotosRepository photosRepository;
    private final FileUpload fileUpload;

    public WalkingPathService2(WalkingPathsRepository walkingPathsRepository, UsersRepository usersRepository,
                               MapRepository mapRepository,
                               PhotosRepository photosRepository, FileUpload fileUpload) {
        this.walkingPathsRepository = walkingPathsRepository;
        this.usersRepository = usersRepository;
        this.mapRepository = mapRepository;
        this.photosRepository = photosRepository;
        this.fileUpload = fileUpload;
    }
    // 전체 리스트
    public List<ResponseWalkingPathDTO> readAll() {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findAllByOrderByCreatedAtDesc();
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = new ArrayList<>();
        for(WalkingPaths walkingPaths : walkingPathsList) {
            responseWalkingPathDTOList.add(ResponseWalkingPathDTO.from(walkingPaths, mapRepository.findTop1ByWalkingPaths(walkingPaths), photosRepository.findTop1ByWalkingPaths(walkingPaths)));
        }
        return responseWalkingPathDTOList;
    }
    // 산책로 하나 조회
    public ResponseWalkingPathDetailDTO readWalkingPath(int id) throws Exception {
        WalkingPaths walkingPaths = walkingPathsRepository.findById(id).orElseThrow(() -> new Exception("존재하지 않는 게시글"));
        ResponseWalkingPathDetailDTO dto = ResponseWalkingPathDetailDTO.from(walkingPaths, mapRepository.findByWalkingPaths(walkingPaths), photosRepository.findByWalkingPaths(walkingPaths));
        return dto;
    }
    // 산책로 검색
    public List<ResponseWalkingPathDTO> searchByKeyword(String keyword) {
        List<WalkingPaths> walkingPathsList = walkingPathsRepository.findByTitleContainingOrAddrContaining(keyword, keyword);
        List<ResponseWalkingPathDTO> responseWalkingPathDTOList = new ArrayList<>();
        for(WalkingPaths walkingPaths : walkingPathsList) {
            responseWalkingPathDTOList.add(ResponseWalkingPathDTO.from(walkingPaths, mapRepository.findTop1ByWalkingPaths(walkingPaths), photosRepository.findTop1ByWalkingPaths(walkingPaths)));
        }
        return responseWalkingPathDTOList;
    }
    // 산책로 작성
    public int createWalkingPath(RequestWalkingPathDTO requestWalkingPath,
                                          List<RequestMapDTO> requestMapDTO,
                                 List<MultipartFile> multipartFile) throws IOException { // , UsersDTO usersDTO
        Users users = usersRepository.getReferenceById(1);
        WalkingPaths entity = WalkingPaths.builder()
                .users(users)
                .title(requestWalkingPath.getTitle())
                .addr(requestWalkingPath.getAddr())
                .build();
        WalkingPaths walkingPaths = walkingPathsRepository.save(entity);
        // 지도
        for(RequestMapDTO dto : requestMapDTO) {
            Map map = Map.builder()
                    .walkingPaths(walkingPaths)
                    .time(dto.getTime())
                    .distance(dto.getDistance())
                    .coordinateX(dto.getCoordinateX())
                    .coordinateY(dto.getCoordinateY())
                    .build();
            mapRepository.save(map);
        }
        // 사진
        FileVo fileVo = new FileVo(multipartFile);
        java.util.Map<String, String> filesName = fileUpload.upload(fileVo);
        for(java.util.Map.Entry<String, String> entry : filesName.entrySet()) {
            Photos photos = Photos.builder()
                    .walkingPaths(walkingPaths)
                    .imgName(entry.getKey())
                    .imgPath(entry.getValue())
                    .build();
            photosRepository.save(photos);
        }
        return walkingPaths.getId();
    }
    // 산책로 수정
    public WalkingPaths modifyWalkingPath(RequestWalkingPathDTO requestWalkingPathDTO, int id) throws Exception {
        // 유저 확인
        Optional<WalkingPaths> walkingPaths = walkingPathsRepository.findById(id);
        WalkingPaths result;
        if (walkingPaths.isPresent()) {
            WalkingPaths entity = WalkingPaths.builder()
                    .id(id)
                    .users(walkingPaths.get().getUsers())
                    .title(requestWalkingPathDTO.getTitle().isEmpty() ? walkingPaths.get().getTitle() : requestWalkingPathDTO.getTitle())
                    .addr(requestWalkingPathDTO.getAddr().isEmpty() ? walkingPaths.get().getAddr() : requestWalkingPathDTO.getAddr())
                    .build();
            result = walkingPathsRepository.save(entity);
        } else {
            throw new Exception("게시글을 찾을 수 없습니다.");
        }
        return result;
    }
    // 산책로 삭제
    public boolean deleteWalkingPath(int id) {
        Optional<WalkingPaths> walkingPaths = walkingPathsRepository.findById(id);
        boolean result = true;
        if(walkingPaths.isPresent())
            walkingPathsRepository.deleteById(id);
        else
            result = false;
        return result;
    }
}
