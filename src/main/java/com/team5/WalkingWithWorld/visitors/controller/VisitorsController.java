package com.team5.WalkingWithWorld.visitors.controller;

import com.team5.WalkingWithWorld.dao.VisitorsMapper;
import com.team5.WalkingWithWorld.visitors.dto.VisitorsDTO;
import com.team5.WalkingWithWorld.visitors.entity.Visitors;
import com.team5.WalkingWithWorld.visitors.repository.VisitorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class VisitorsController {
    @Autowired
    VisitorsMapper dao;
    @Autowired
    VisitorsRepository visitorsRepository;


    @GetMapping("/visitorslist")
    public ModelAndView list(){
        List<Visitors> list = visitorsRepository.findAll();
        ModelAndView mav = new ModelAndView();
        System.out.println(list);
        mav.addObject("list", list);
        mav.setViewName("visitorView");
        return mav;
    }

    @PostMapping("/visitor/delete")
    @Transactional
    public ModelAndView delete(@RequestBody VisitorsDTO visitorsDTO) {
        ModelAndView mav = new ModelAndView();
        try {
            visitorsRepository.deleteByIdAndPassword(visitorsDTO.getId(), visitorsDTO.getPassword());
            mav.addObject("list", visitorsRepository.findAll());
            mav.setViewName("visitorView::#deleteList");
            return mav;
        }catch(EmptyResultDataAccessException e){
            mav.addObject("list", visitorsRepository.findAll());
            mav.addObject("message", "비밀번호가 일치하지 않습니다.");
            mav.setViewName("visitorView::#deleteList");
        }
        return mav;
    }

    @PostMapping("/insertVisitors")
    @Transactional
    public ModelAndView insert(Visitors vo){
        ModelAndView mav=new ModelAndView();
       try{
           visitorsRepository.save(vo);
           mav.addObject("list", visitorsRepository.findAll());
        }catch (Exception e){
           mav.addObject("msg", "오류가 발생했습니다.");
       }
        mav.setViewName("visitorView");
        return mav;
    }

    @RequestMapping(value="/insertVisitorsForm")
    public String showInsertForm(){
        return "insertVisitorsForm";
    }


}
