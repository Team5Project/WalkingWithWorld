package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.UsersDto;
import com.team5.WalkingWithWorld.domain.WalkingPathsDTO;
import com.team5.WalkingWithWorld.global.Login;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
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

    /*
    @GetMapping("/walking-path")
    public ModelAndView readAllWalkingPath() {
        ModelAndView mav = new ModelAndView();
        List<WalkingPathsDTO> walkingPathList = dao.readAll();
        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("walking-path");
        return mav;
    }
    */
    //@PostMapping("/walking-path")

    @GetMapping("/walking-path")
    public ModelAndView createWalkingPath(@Login UsersDto loginUser) {
        ModelAndView mav = new ModelAndView();

        System.out.println(loginUser.getId());
        // 임시 데이터
        WalkingPathsDTO dto = new WalkingPathsDTO();
        dto.setUsers_id(2);
        dto.setAddr("올림픽대로 5");
        dto.setTitle("올림픽 공원 산책로");
        dto.setCreated_by("개굴이"); // 이 부분을 userDTO 사용??


        // 추후 결과 따른 msg 추가
//        boolean updateResult = dao.addWalkingPath(dto);
//        System.out.println(updateResult); // 확인용
        List<WalkingPathsDTO> walkingPathList = dao.readAll();
        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("walking-path");
        return mav;
    }

    @GetMapping("/walking-path/{walking-path-id}")
    @ResponseBody
    public WalkingPathsDTO getWalkingPathById(@PathVariable("walking-path-id") int id) {
        return dao.readOne(id);

    }
}