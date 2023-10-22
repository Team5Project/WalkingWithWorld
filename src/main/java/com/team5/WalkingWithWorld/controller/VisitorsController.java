package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.VisitorsMapper;
import com.team5.WalkingWithWorld.domain.VisitorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class VisitorsController {
    @Autowired
    VisitorsMapper dao;

    @GetMapping("/visitorslist")
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView();
        List<VisitorsDTO> list = dao.visitorslist();
        System.out.println(list);
        mav.addObject("list", list);
        mav.setViewName("visitorView");
        return mav;
    }

    @PostMapping("/visitor/delete")
    public ModelAndView delete(@RequestBody VisitorsDTO visitorsDTO) {
        boolean result = dao.deleteVisitors(visitorsDTO.getId(),visitorsDTO.getPassword());
        ModelAndView mav = new ModelAndView();

        System.out.println(visitorsDTO);

        if(result) {
            mav.addObject("list", dao.visitorslist());
            mav.setViewName("visitorView::#deleteList");
            return mav;
        }else{
            mav.addObject("list", dao.visitorslist());
            mav.addObject("message", "비밀번호가 다릅니다");
            mav.setViewName("visitorView::#deleteList");
        }
        return mav;
    }

    @RequestMapping(value="/insertVisitors")
    public ModelAndView insert(VisitorsDTO dto){
        boolean result=dao.insertVisitors(dto);
        ModelAndView mav=new ModelAndView();
        if(result){
            mav.addObject("list", dao.visitorslist());
        }
        mav.setViewName("visitorView");
        return mav;
    }

    @RequestMapping(value="/insertVisitorsForm")
    public String showInsertForm(){
        return "insertVisitorsForm";
    }


}
