package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.CommentsMapper;
import com.team5.WalkingWithWorld.domain.CommentsDTO;
import com.team5.WalkingWithWorld.domain.UsersDto;
import com.team5.WalkingWithWorld.global.Login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    @PostMapping("/comments/list/{walking-paths-id}")
    public String list(Model model,
                       @PathVariable("walking-paths-id") int id) {
        List<CommentsDTO> list = dao.getCommentById(id);
        model.addAttribute("commentList", list);
        return "comments :: #comments";
    }

    @PostMapping(value = "/comments/{walking-paths-id}",produces = "application/json; charset=utf-8")
    public String writeComment(@RequestBody @Valid CommentsDTO dto,
                               @Login UsersDto usersDto,
                               @PathVariable("walking-paths-id") int id,
                               Model model,
                               HttpServletRequest request) {
        String ref = request.getHeader("Referer");

        dto.setUsers_id(usersDto.getId());
        dto.setWalkingPathsId(id);
        dao.write(dto);

        List<CommentsDTO> list = dao.getCommentById(id);
        model.addAttribute("commentList", list);
        return "comments :: #comments";
    }

    @RequestMapping(value = "/comments/updateComments", produces = "application/json; charset=utf-8")
    @ResponseBody
    public CommentsDTO updateComments(int id) {
        return dao.updateComments(id);
    }

    @RequestMapping("/comments/update")
    public ModelAndView update(CommentsDTO dto) {
        boolean result = dao.update(dto);
        ModelAndView mav = new ModelAndView();
//        mav.addObject("list", dao.list());
        mav.setViewName("redirect:/comment");
        return mav;
    }

    @PostMapping(value = "/comments/delete", produces = "application/json; charset=utf-8")
    public String delete(@RequestBody CommentsDTO dto,
                         Model model) {

        boolean result = dao.delete(dto.getId());

        System.out.println(dto.getId());
        System.out.println(dto.getWalkingPathsId());

        List<CommentsDTO> list = dao.getCommentById(dto.getWalkingPathsId());
        model.addAttribute("commentList", list);

        return "comments :: #comments";

    }
}
