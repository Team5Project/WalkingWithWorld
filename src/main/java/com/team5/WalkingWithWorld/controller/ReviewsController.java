package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.MapMapper;
import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.ReviewsMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.*;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.service.ReviewService;
import com.team5.WalkingWithWorld.service.WalkingPathService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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
    @Autowired
    MapMapper mapMapper;
    @Autowired
    WalkingPathService walkingPathService;
    @Autowired
    WalkingPathsController walkingPathsController;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviews/{reviews-id}")
    public String reviews(@PathVariable("reviews-id") int id,
                          Model model,
                          HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        model.addAttribute("walkingPaths", pathsMapper.readWalkingPath(id));
        model.addAttribute("referer");

        return "reviews_write_form";
    }

    @PostMapping("/reviews")
    public String getReviewList(Model model ) {

        List<ReviewsDTO> list = dao.reviewslist();

        for(ReviewsDTO dto:list){
            dto.setPhotosList(photoDao.readReviewPhotos(dto.getId()));
        }

        model.addAttribute("reviewList", list);

        return "reviews :: #reviews";
    }


    //리뷰 작성
    @PostMapping("/reviews/{walking-paths-id}")
    public ModelAndView createReview(@Login UsersDto loginUser,
                               @PathVariable("walking-paths-id") int id,
                               ReviewsDTO reviewsDTO,
                               FileVo files,
                               HttpServletRequest request) throws IOException {
        ModelAndView mav;
        reviewsDTO.setUsersId(loginUser.getId());
        reviewsDTO.setWalkingPathsId(id);
        reviewsDTO.setCreatedBy(loginUser.getName());

        int reviewId = reviewService.createReview(reviewsDTO,files);


        mav = walkingPathsController.getWalkingPathById(id,request);

        return mav;
    }

    @GetMapping("/reviews/delete")
    public ModelAndView delete(int id) {
        boolean result = dao.deleteReviews(id);
        ModelAndView mav = new ModelAndView();
        if (result) {
            mav.addObject("list", dao.reviewslist());
        }
        mav.setViewName("reviews_write_form");
        return mav;
    }

    @RequestMapping(value = "/reviews/UpdatePage")
    @ResponseBody
    public ReviewsDTO updateReviews(ReviewsDTO dto) {
        dao.updateReviews(dto);
        return dto;
    }

    @GetMapping("/reviews/update")
    public ModelAndView update(ReviewsDTO vo) {
        boolean result = dao.updateReviews(vo);
        ModelAndView mav = new ModelAndView();
        if (result) {
            mav.addObject("list", dao.reviewslist());
        }
        mav.setViewName("reviews_write_form");
        return mav;
    }

}
