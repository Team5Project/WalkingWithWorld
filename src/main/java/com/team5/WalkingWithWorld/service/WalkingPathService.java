package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.MapMapper;
import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.FileVo;
import com.team5.WalkingWithWorld.domain.PhotosDTO;
import com.team5.WalkingWithWorld.domain.WalkingPathsMapDTO;
import org.springframework.stereotype.Service;

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

    public int createWalkingPath(WalkingPathsMapDTO walkingPathsMapDTO, FileVo multipartFile) {
        //산책로
        walkingPathsMapper.addWalkingPath(walkingPathsMapDTO);
        //지도
        mapMapper.addMap(walkingPathsMapDTO);
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
}
