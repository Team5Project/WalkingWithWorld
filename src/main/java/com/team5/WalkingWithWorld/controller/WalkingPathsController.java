package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.UserMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.WalkingPathsDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WalkingPathsController {
    @Autowired
    WalkingPathsMapper dao;
    @Autowired
    UserMapper userDao;

    @GetMapping("/walking-path")
    public ModelAndView readAllWalkingPath() {
        ModelAndView mav = new ModelAndView();
        List<WalkingPathsDTO> walkingPathList = dao.readAll();
        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("walking-path");
        return mav;
    }

    @PostMapping("/walking-path")
    public ModelAndView createWalkingPath(WalkingPathsDTO dto, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        int userId = (int) session.getAttribute("Authorization");
        System.out.println("유저 아이디 : " + userId); // 확인용

        dto.setUsers_id(userId);
        dto.setCreated_by(userDao.getUserById(userId).getName()); // 이 부분을 userDTO 사용??

        // 추후 결과 따른 msg 추가
        boolean updateResult = dao.addWalkingPath(dto);
        System.out.println(updateResult); // 확인용
        List<WalkingPathsDTO> walkingPathList = dao.readAll();
        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("redirect:/walking-path");
        return mav;
    }

    @GetMapping("/walking-path/{walking-path-id}")
    @ResponseBody
    public WalkingPathsDTO getWalkingPathById(@PathVariable("walking-path-id") int id) {
        return dao.readWalkingPath(id);
    }
}