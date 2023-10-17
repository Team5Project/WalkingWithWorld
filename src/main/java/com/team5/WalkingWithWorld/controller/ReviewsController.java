package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.ReviewsMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.PhotosDTO;
import com.team5.WalkingWithWorld.domain.ReviewsDTO;
import com.team5.WalkingWithWorld.domain.UsersDto;
import com.team5.WalkingWithWorld.global.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ReviewsController {
    @Autowired
    ReviewsMapper dao;
    @Autowired
    WalkingPathsMapper pathsMapper;
    @Autowired
    PhotosMapper photoDao;

    @GetMapping("/reviews/{reviews-id}")
    public String reviews(@PathVariable("reviews-id") int id,
                          Model model){

        model.addAttribute("walkingPaths", pathsMapper.readWalkingPath(id));

        return "reviewsPage";
    }

    @PostMapping("/reviews")
    public String getReviewList(Model model, ReviewsDTO reviewsDTO){

        List<ReviewsDTO> list = dao.reviewslist();
        model.addAttribute("reviewList", list);

        return "reviews :: #reviews";
    }


    @PostMapping("/reviews/{walking-paths-id}")
    public String createReview(@Login UsersDto loginUser,
                                         @PathVariable("walking-paths-id") int id,
                                         ReviewsDTO reviewsDTO){

        reviewsDTO.setUsersId(loginUser.getId());
        reviewsDTO.setWalkingPathsId(id);
        reviewsDTO.setCreatedAt(LocalDateTime.now());
        reviewsDTO.setCreatedBy(loginUser.getName());

        List<PhotosDTO> photosList = photoDao.readPhotos(loginUser.getId());
        reviewsDTO.setPhotosList(photosList);

        dao.insertReviews(reviewsDTO);

        return "reviews";
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

    @RequestMapping(value="/reviews/UpdatePage")
    @ResponseBody
    public ReviewsDTO updateReviews(ReviewsDTO dto){
        dao.updateReviews(dto);
        return dto;
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
