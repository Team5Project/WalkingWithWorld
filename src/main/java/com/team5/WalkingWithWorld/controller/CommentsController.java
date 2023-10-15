package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.CommentsMapper;
import com.team5.WalkingWithWorld.domain.CommentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommentsController {
    @Autowired
    CommentsMapper dao;
    @RequestMapping("/comments")
    public String gogo(){
        return "commentsView";
    }

    @RequestMapping("/comments/List")
    public ModelAndView list() {
        List<CommentsDTO> list = dao.list();
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        mav.setViewName("commentsView");
        return mav;
    }

}
