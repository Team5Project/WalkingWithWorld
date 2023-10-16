package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.FileVo;
import com.team5.WalkingWithWorld.domain.PhotosDTO;
import com.team5.WalkingWithWorld.domain.WalkingPathsDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WalkingPathService {
    private WalkingPathsMapper walkingPathsMapper;
    private PhotosMapper photosMapper;
    private FileUpload fileUpload;

    public WalkingPathService(WalkingPathsMapper walkingPathsMapper, PhotosMapper photosMapper, FileUpload fileUpload) {
        this.walkingPathsMapper = walkingPathsMapper;
        this.photosMapper = photosMapper;
        this.fileUpload = fileUpload;
    }

    public int createWalkingPath(WalkingPathsDTO walkingPathsDTO, FileVo multipartFile) {
        //산책로
        walkingPathsMapper.addWalkingPath(walkingPathsDTO);
        //산책로 이미지
        Map<String, String> filesName = fileUpload.upload(multipartFile);
        PhotosDTO photosDTO = new PhotosDTO();
        photosDTO.setWalkingPathsId(walkingPathsDTO.getId());
        for(Map.Entry<String, String> entry : filesName.entrySet()) {
            photosDTO.setImgName(entry.getKey());
            photosDTO.setImgPath(entry.getValue());
            photosMapper.addPhotos(photosDTO);
        }
        return walkingPathsDTO.getId();
    }
}
