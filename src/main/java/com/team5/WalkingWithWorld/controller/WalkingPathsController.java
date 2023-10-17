package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.*;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.service.WalkingPathService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class WalkingPathsController {
    @Autowired
    WalkingPathsMapper dao;
    @Autowired
    PhotosMapper photoDao;
    @Autowired
    WalkingPathService walkingPathService;

    //리스트
    @GetMapping("/walking-path")
    public ModelAndView readAllWalkingPath(@Login UsersDto loginUser) {
        ModelAndView mav = new ModelAndView();
        List<WalkingPathsDTO> walkingPathList = dao.readAll();

        for(WalkingPathsDTO dto : walkingPathList) {
           dto.setPhotosList(photoDao.readPhotos(dto.getId()));
        }

        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("walking-path");
        return mav;
    }

    //산책로 등록
    @PostMapping("/walking-path")
    public ModelAndView createWalkingPath(WalkingPathsMapDTO dto,
                                          @Login UsersDto loginUser,
                                          FileVo files) throws IOException {
        System.out.println(dto.getCourse()); ////////////////////////////
        ModelAndView mav = new ModelAndView();
        dto.setUsersId(loginUser.getId());
        dto.setCreatedBy(loginUser.getName());

        // 추후 결과 따른 msg 추가
        int walkingPathId = walkingPathService.createWalkingPath(dto, files);

        System.out.println("게시글 생성 완료 : " + walkingPathId);
        mav.setViewName("redirect:/walking-path/" + walkingPathId);
        return mav;
    }

    @GetMapping("/walking-path/write")
    public ModelAndView goToWrite(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("referer", request.getHeader("referer"));
        mav.setViewName("walking-path_form");
        return mav;
    }

    @GetMapping("/walking-path/{walking-path-id}")
    @ResponseBody
    public ModelAndView getWalkingPathById(@PathVariable("walking-path-id") int id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        WalkingPathsMapDTO walkingPaths = dao.readWalkingPath(id);
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