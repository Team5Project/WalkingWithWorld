package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.ReviewsMapper;
import com.team5.WalkingWithWorld.domain.ReviewsDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewsController {
    @Autowired
    ReviewsMapper dao;

/*    @GetMapping("/reviews")
    public String reviews(){

        return "reviewsPage";
    }*/

    @GetMapping("/reviewslist")
    public ModelAndView reviewslist(){
        ModelAndView mav = new ModelAndView();
        List<ReviewsDTO> list = dao.reviewslist();
        mav.addObject("list", list);
        mav.setViewName("reviewsPage");
        return mav;
    }

    @PostMapping("/reviews")
    @ResponseBody
    public List<ReviewsDTO> createReview(HttpSession session, ReviewsDTO reviewsDTO){
//        int id = Integer.parseInt(session.getAttribute("Authorization"));

        ReviewsDTO review = reviewsDTO;

        review.setUsersId(1);
        review.setWalkingPathsId(1);
        review.setCreatedAt(LocalDateTime.now());
        review.setCreatedBy("test");

        System.out.println(review);

        dao.insertReviews(review);

        List<ReviewsDTO> list = dao.reviewslist();
//        list = dao.reviewslist();

        System.out.println(list);
        return list;
    }

    @GetMapping("/reviews/delete")
    public ModelAndView delete(int id){
        boolean result=dao.deleteReviews(id);
        ModelAndView mav=new ModelAndView();
        if(result){
            mav.addObject("list", dao.reviewslist());
        }
        mav.setViewName("reviewsPage");
        return mav;
    }

    @GetMapping("/reviews/update")
    public ModelAndView update(ReviewsDTO vo){
        boolean result = dao.updateReviews(vo);
        ModelAndView mav = new ModelAndView();
        if(result){
            mav.addObject("list", dao.reviewslist());
        }
        mav.setViewName("reviewsPage");
        return mav;
    }

}
