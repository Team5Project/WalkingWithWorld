package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.MapMapper;
import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WalkingPathService {
    private WalkingPathsMapper walkingPathsMapper;
    private PhotosMapper photosMapper;
    private FileUpload fileUpload;
    private MapMapper mapMapper;

    public WalkingPathService(WalkingPathsMapper walkingPathsMapper, PhotosMapper photosMapper, FileUpload fileUpload, MapMapper mapMapper) {
        this.walkingPathsMapper = walkingPathsMapper;
        this.photosMapper = photosMapper;
        this.fileUpload = fileUpload;
        this.mapMapper = mapMapper;
    }

    public List<WalkingPathsMapDTO> getList(SearchDTO dto) {
        return walkingPathsMapper.pagingList(dto);
    }

    // 산책로 생성
    public int createWalkingPath(WalkingPathsDTO walkingPathsDTO, UsersDTO user, FileVo multipartFile, MapDTO mapDTO, String course) throws IOException {
        //산책로
        walkingPathsDTO.setUsersId(user.getId());
        walkingPathsDTO.setCreatedBy(user.getName());
        if(walkingPathsMapper.addWalkingPath(walkingPathsDTO) != 1)
            return -1;
        //지도
        if(!course.isEmpty()) {
            String[] courseSplit = course.split(",");
            mapDTO.setWalkingPathsId(walkingPathsDTO.getId());
            for (int i = 0; i < courseSplit.length; i += 2) {
                mapDTO.setCoordinateX(courseSplit[i]);
                mapDTO.setCoordinateY(courseSplit[i + 1]);
                mapMapper.addMap(mapDTO);
            }
        }


        //산책로 이미지

        //이미지

        Map<String, String> filesName = fileUpload.upload(multipartFile);

        PhotosDTO photosDTO = new PhotosDTO();
        photosDTO.setWalkingPathsId(walkingPathsDTO.getId());
        for(Map.Entry<String, String> entry : filesName.entrySet()) {
            photosDTO.setImgName(entry.getKey());
            photosDTO.setImgPath(entry.getValue());
            photosMapper.addPhotos(photosDTO);
        }
        System.out.println("게시글 생성 완료 : " + walkingPathsDTO.getId());
        return walkingPathsDTO.getId();
    }

    // 산책로 하나 읽기
    public WalkingPathsMapDTO readWalkingPathById(int id) {
        WalkingPathsMapDTO walkingPathsMapDTO = walkingPathsMapper.readWalkingPathMap(id);
        if(walkingPathsMapDTO == null)
            return null;

        // 지도 읽기
        List<MapDTO> mapDTOList = mapMapper.ReadMap(id);
        if(!mapDTOList.isEmpty())
            walkingPathsMapDTO.setMapList(mapDTOList);

        // 이미지 읽기
        List<PhotosDTO> photosList = photosMapper.readPhotos(id);
        if(!photosList.isEmpty())
            walkingPathsMapDTO.setPhotosList(photosList);

        return walkingPathsMapDTO;
    }

    // 산책로 리스트 읽기 - 전체
    public List<WalkingPathsMapDTO> readWalkingPathList() {
        List<WalkingPathsMapDTO> walkingPathsMapDTOList = walkingPathsMapper.readAllWalkingPathsMap();
        setPhotoAndMap(walkingPathsMapDTOList);
        return walkingPathsMapDTOList;
    }

    // 산책로 리스트 읽기 - keyword 기준
    public List<WalkingPathsMapDTO> readWalkingPathListWithKeyword(String keyword) {
        List<WalkingPathsMapDTO> walkingPathsMapDTOList = walkingPathsMapper.searchWalkingPathByKeyword(keyword);
        setPhotoAndMap(walkingPathsMapDTOList);
        return walkingPathsMapDTOList;
    }

    // 산책로 리스트 읽기 - keyword와 searchDTO 기준
    public List<WalkingPathsMapDTO> readWalkingPathListWithSearchDTO(String searchWord, SearchDTO searchDTO) {
        searchDTO.setKeyword(searchWord.equals("null")?null:searchWord);
        List<WalkingPathsMapDTO> walkingPathsMapDTOList = walkingPathsMapper.searchWalkingPathWithSearchDTO(searchDTO); // 변경
        setPhotoAndMap(walkingPathsMapDTOList);
        return walkingPathsMapDTOList;
    }

    // 산책로 리스트 읽기 - photo, map setter
    public void setPhotoAndMap(List<WalkingPathsMapDTO> walkingPathMapList) {
        for(WalkingPathsMapDTO dto : walkingPathMapList) {
            dto.setPhotosList(photosMapper.readPhoto(dto.getId()));
            dto.setMapList(mapMapper.ReadMap(dto.getId()));
        }
    }

    // 산책로 수정
    public void  modifyWalkingPathWithUserName(WalkingPathsDTO walkingPathsDTO, String userName) {
        WalkingPathsDTO walkingPathsFromDB = walkingPathsMapper.readWalkingPath(walkingPathsDTO.getId());

        if(walkingPathsDTO.getTitle() != null && !walkingPathsDTO.getTitle().isEmpty() && !walkingPathsDTO.getTitle().isBlank())
            walkingPathsFromDB.setTitle(walkingPathsDTO.getTitle());
        if(walkingPathsDTO.getAddr() != null && !walkingPathsDTO.getAddr().isEmpty() && !walkingPathsDTO.getAddr().isBlank())
            walkingPathsFromDB.setAddr(walkingPathsDTO.getAddr());
        walkingPathsFromDB.setModifiedBy(userName);
        walkingPathsMapper.updateWalkingPath(walkingPathsFromDB);
    }
}
