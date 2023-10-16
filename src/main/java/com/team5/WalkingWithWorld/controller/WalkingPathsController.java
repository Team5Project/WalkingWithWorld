package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.UserMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.FileVo;
import com.team5.WalkingWithWorld.domain.PhotosDTO;
import com.team5.WalkingWithWorld.domain.UsersDto;
import com.team5.WalkingWithWorld.domain.WalkingPathsDTO;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.service.WalkingPathService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class WalkingPathsController {
    @Autowired
    WalkingPathsMapper dao;
    @Autowired
    PhotosMapper photoDao;
    @Autowired
    WalkingPathService walkingPathService;

    @GetMapping("/walking-path")
    public ModelAndView readAllWalkingPath() {
        ModelAndView mav = new ModelAndView();
        List<WalkingPathsDTO> walkingPathList = dao.readAll();

        for(WalkingPathsDTO dto : walkingPathList) {
           dto.setPhotosList(photoDao.readPhotos(dto.getId()));
        }

        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("walking-path");
        return mav;
    }

    @PostMapping("/walking-path")
    public ModelAndView createWalkingPath(WalkingPathsDTO dto,
                                          @Login UsersDto loginUser, FileVo files) {
        ModelAndView mav = new ModelAndView();

        dto.setUsersId(loginUser.getId());
        dto.setCreatedBy(loginUser.getName());


        // 추후 결과 따른 msg 추가
        int walkingPathId = walkingPathService.createWalkingPath(dto, files);
        System.out.println("게시글 생성 완료 : " + walkingPathId);

        mav.setViewName("redirect:/walking-path/" + walkingPathId);

        return mav;
    }

    @GetMapping("/walking-path/{walking-path-id}")
    @ResponseBody
    public ModelAndView getWalkingPathById(@PathVariable("walking-path-id") int id) {
        ModelAndView mav = new ModelAndView();
        WalkingPathsDTO walkingPaths = dao.readWalkingPath(id);
        List<PhotosDTO> photosList = photoDao.readPhotos(walkingPaths.getId());
        walkingPaths.setPhotosList(photosList);

        mav.addObject("walkingPaths", walkingPaths);
        mav.setViewName("walking-path_detail");
        return mav;
    }

    @GetMapping("/walking-path/photos/{walking-path-id}")
    @ResponseBody
    public List<PhotosDTO> getPhotosByWalkingPathId(@PathVariable("walking-path-id") int id) {
        return photoDao.readPhotos(id);
    }
}