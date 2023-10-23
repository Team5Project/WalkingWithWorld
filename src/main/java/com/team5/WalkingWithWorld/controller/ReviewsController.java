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

    @GetMapping("/reviews/{walking-paths-id}/write")
    public String reviews(@PathVariable("walking-paths-id") int id,
                          Model model,
                          HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        WalkingPathsMapDTO walkingPaths = pathsMapper.readWalkingPathMap(id);
        walkingPaths.setMapList(mapMapper.ReadMap(id) );
        walkingPaths.setPhotosList(photoDao.readPhotos(id));


        model.addAttribute("walkingPaths",walkingPaths);
        model.addAttribute("referer");

        return "reviews_write_form";
    }

    @PostMapping("/reviews/list/{walking-paths-id}")
    public String getReviewList(Model model,
                                @PathVariable("walking-paths-id") int id
                                ) {

        System.out.println(id);
        List<ReviewsDTO> list = dao.reviewListByWalkingPathsId(id);

        for(ReviewsDTO dto:list){
            dto.setPhotosList(photoDao.readReviewPhotos(dto.getId()));
        }
        System.out.println(list);

        model.addAttribute("walkingPaths", walkingPathService.readWalkingPathById(id));
        model.addAttribute("reviewList", list);

        if(list.isEmpty()){
            System.out.println("스태틱 리뷰");
            return "reviews ::#static_reviews";
        }

        System.out.println("다이나믹 리뷰");
        return "reviews :: #reviews";
    }


    //리뷰 작성
    @PostMapping("/reviews/{walking-paths-id}/write")
    public String createReview(@Login UsersDTO loginUser,
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

        return "redirect:/walking-path/"+id;
    }

    @GetMapping("/reviews/delete")
    public ModelAndView delete(int id) {
        boolean result = dao.deleteReviews(id);
        ModelAndView mav = new ModelAndView();
        if (result) {
            mav.addObject("list", dao.reviewlist());
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
            mav.addObject("list", dao.reviewlist());
        }
        mav.setViewName("reviews_write_form");
        return mav;
    }

}
