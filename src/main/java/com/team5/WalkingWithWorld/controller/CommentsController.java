package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.CommentsMapper;
import com.team5.WalkingWithWorld.domain.CommentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CommentsController {
    @Autowired
    CommentsMapper dao;

    @RequestMapping("/comments")
    public ModelAndView list() {
        List<CommentsDTO> list = dao.list();
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        mav.setViewName("commentsView");
        System.out.println(dao.list());
        return mav;
    }
    @RequestMapping(value = "/comments/write",
            method = RequestMethod.POST)
    public ModelAndView write(CommentsDTO dto){
        boolean result = dao.write(dto);
        ModelAndView mav = new ModelAndView();
        mav.addObject("list",dao.list());
        mav.setViewName("redirect:/comments");
        return mav;
    }

    @RequestMapping(value = "/comments/updateComments", produces = "application/json; charset=utf-8")
    @ResponseBody
    public CommentsDTO updateComments(int id) {
        return dao.updateComments(id);
    }

    @RequestMapping("/comments/update")
    public ModelAndView update(CommentsDTO dto){
        boolean result = dao.update(dto);
        ModelAndView mav = new ModelAndView();
        mav.addObject("list",dao.list());
        mav.setViewName("redirect:/comments");
        return mav;
    }

    @RequestMapping("/comments/delete")
    public ModelAndView delete(String id){
        boolean result = dao.delete(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("list",dao.list());
        mav.setViewName("redirect:/comments");
        return mav;
    }

}
