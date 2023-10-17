package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.CommentsMapper;
import com.team5.WalkingWithWorld.domain.CommentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommentsController {
    @Autowired
    CommentsMapper dao;

    @PostMapping("/comments")
    public String list(Model model) {
        List<CommentsDTO> list = dao.list();
        model.addAttribute("commentList", list);
        return "comments :: #comments";
    }

    @PostMapping("/comments/{walking-paths-id}")
    public ModelAndView write(CommentsDTO dto,
                              @PathVariable("walking-paths-id") int id){
        boolean result = dao.write(dto);
        ModelAndView mav = new ModelAndView();
        mav.addObject("list",dao.list());
        mav.setViewName("redirect:/comment");
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
        mav.setViewName("redirect:/comment");
        return mav;
    }

    @RequestMapping("/comments/delete")
    public ModelAndView delete(String id){
        boolean result = dao.delete(id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("list",dao.list());
        mav.setViewName("redirect:/comment");
        return mav;
    }

}
