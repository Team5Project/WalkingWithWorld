package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.MapMapper;
import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public int createWalkingPath(WalkingPathsDTO walkingPathsDTO, FileVo multipartFile, MapDTO mapDTO, String course) {
        //산책로
        walkingPathsMapper.addWalkingPath(walkingPathsDTO);
        //지도
        List<MapDTO> mapList = new ArrayList<>();
        String[] courseSplit = course.split(",");
        mapDTO.setWalkingPathsId(walkingPathsDTO.getId());
        for(int i = 0; i < courseSplit.length; i += 2) {
            mapDTO.setCoordinateX(courseSplit[i]);
            mapDTO.setCoordinateY(courseSplit[i + 1]);
            mapMapper.addMap(mapDTO);
        }

        //산책로 이미지
        Map<String, String> filesName = fileUpload.upload(multipartFile);

        PhotosDTO photosDTO = new PhotosDTO();
        photosDTO.setWalkingPathsId(walkingPathsMapDTO.getId());
        for(Map.Entry<String, String> entry : filesName.entrySet()) {
            photosDTO.setImgName(entry.getKey());
            photosDTO.setImgPath(entry.getValue());
            photosMapper.addPhotos(photosDTO);
        }
        return walkingPathsMapDTO.getId();
    }

    public WalkingPathsMapDTO readWalkingPathById(int id) {
        WalkingPathsMapDTO walkingPathsMapDTO = walkingPathsMapper.readWalkingPath(id);
        List<MapDTO> mapDTOList = mapMapper.ReadMap(id);
        walkingPathsMapDTO.setMapList(mapDTOList);
        return walkingPathsMapDTO;
    }
}
