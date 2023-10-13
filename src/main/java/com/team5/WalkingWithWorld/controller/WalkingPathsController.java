package com.team5.WalkingWithWorld.controller;

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
    /*
    @GetMapping("/walking-path")
    public ModelAndView readAll() {
        ModelAndView mav = new ModelAndView();
        List<WalkingPathsDTO> walkingPathList = dao.readAll();
        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("walking-path");
        return mav;
    }
    */
    //@PostMapping("/walking-path")
    @GetMapping("/walking-path")
    public ModelAndView update(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        
        // 임시 데이터
        WalkingPathsDTO dto = new WalkingPathsDTO();
        dto.setUsers_id(5);
        dto.setAddr("송파구 풍납동");
        dto.setTitle("풍납 토성");
        // 추후 결과 따른 msg 추가
        boolean updateResult = dao.addWalkingPath(dto);
        System.out.println(updateResult); // 확인용
        List<WalkingPathsDTO> walkingPathList = dao.readAll();
        mav.addObject("walkingPathList", walkingPathList);
        mav.setViewName("walking-path");
        return mav;
    }

    @GetMapping("/walking-path/users")
    @ResponseBody
    public List<WalkingPathsDTO> getWalkingPathsUserId(HttpSession session){
        int id = Integer.parseInt(session.getAttribute("Authorization").toString());
        System.out.println(id);

        return dao.realWithUserId(id);
    }
}
