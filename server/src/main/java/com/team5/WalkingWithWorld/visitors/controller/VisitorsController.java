package com.team5.WalkingWithWorld.visitors.controller;

import com.team5.WalkingWithWorld.dao.VisitorsMapper;
import com.team5.WalkingWithWorld.visitors.dto.VisitorsDTO;
import com.team5.WalkingWithWorld.visitors.entity.Visitors;
import com.team5.WalkingWithWorld.visitors.repository.VisitorsRepository;
import com.team5.WalkingWithWorld.visitors.service.VisitorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class VisitorsController {
    private final VisitorsService visitorsService;
    @Autowired
    VisitorsMapper dao;
    @Autowired
    VisitorsRepository visitorsRepository;

    public VisitorsController(VisitorsService visitorsService) { this.visitorsService = visitorsService;}


    @GetMapping("/list")
    public ResponseEntity<List<Visitors>> list() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(visitorsRepository.findAll());
    }

    /*     @PostMapping("/visitors/delete")
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
        } */
    @DeleteMapping("/{id}")
    /*@Transactional*/
    public ResponseEntity delete(@PathVariable("id") Long id, @RequestBody Visitors vo) {
        try{
        System.out.println("삭제 ID: " + id);
        System.out.println("password: "+vo.getPassword());
        /*visitorsRepository.findAll();*/
        visitorsService.deleteVisitors(id, vo.getPassword());
        return new ResponseEntity(HttpStatus.RESET_CONTENT);

        }catch(Exception e){
        System.err.println("Error in delete controller: " + e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

}
    @PostMapping("/visitor")
    public ResponseEntity insert(@RequestBody Visitors vo){
        Visitors visitors;
       try{
           visitors =  visitorsRepository.save(vo);
        }catch (Exception e){
            e.printStackTrace();
           return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
       }
        return new ResponseEntity(visitors, HttpStatus.OK);
    }

    @PutMapping("/visitor")
    @Transactional
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Visitors vo){
        Visitors visitors;
        try{
            visitors =  visitorsService.updateVisitors(id, vo.getPassword(), vo.getContent());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(visitors, HttpStatus.OK);
    }


}
