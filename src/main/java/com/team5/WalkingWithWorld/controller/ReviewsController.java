package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.MapMapper;
import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.ReviewsMapper;
import com.team5.WalkingWithWorld.dao.WalkingPathsMapper;
import com.team5.WalkingWithWorld.domain.*;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
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
import java.util.Objects;

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
    @Autowired
    ReviewsMapper reviewsMapper;

    @GetMapping("/reviews/{walking-paths-id}/write")
    public String reviews(@PathVariable("walking-paths-id") int id,
                          Model model,
                          HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        WalkingPathsMapDTO walkingPaths = pathsMapper.readWalkingPathMap(id);
//        walkingPaths.setMapList(mapMapper.ReadMap(id) );
//        walkingPaths.setPhotosList(photoDao.readPhotos(id));


        model.addAttribute("walkingPaths", walkingPaths);
        model.addAttribute("referer");

        return "reviews_write_form";
    }

    @PostMapping("/reviews/list/{walking-paths-id}")
    public String getReviewList(Model model,
                                @PathVariable("walking-paths-id") int id
    ) {

        System.out.println(id);
        List<ReviewsDTO> list = dao.reviewListByWalkingPathsId(id);

        for (ReviewsDTO dto : list) {
            dto.setPhotosList(photoDao.readReviewPhotos(dto.getId()));
        }

        model.addAttribute("walkingPaths", walkingPathService.readWalkingPathById(id));
        model.addAttribute("reviewList", list);

        if (list.isEmpty()) {
            return "reviews ::#static_reviews";
        }

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

        int reviewId = reviewService.createReview(reviewsDTO, files);


        mav = walkingPathsController.getWalkingPathById(id, request);

        return "redirect:/walking-path/" + id;
    }


    @PostMapping("/reviews/delete")
    public String delete(@RequestBody ReviewsDTO reviewsDTO,
                         @Login UsersDTO login,
                         Model model) {
        boolean result = dao.deleteReviews(reviewsDTO.getId(), login.getId());

        if (!Objects.equals(login.getName(), reviewsDTO.getCreatedBy())) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        if (!result) {
            throw new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND);
        }

        return "redirect:/walking-path/" + reviewsDTO.getWalkingPathsId();
    }

    @GetMapping(value = "/reviews/modify/{review-id}")
    public String getModifyForm(@PathVariable("review-id") int id,
                                @Login UsersDTO login,
                                Model model) {
        ReviewsDTO review = reviewsMapper.getReviewByIdAndReferenceUserId(id, login.getId());
        review.setPhotosList(photoDao.readReviewPhotos(id));

        if (!Objects.equals(review.getCreatedBy(), login.getName())) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        model.addAttribute("review", review);
        return "reviews_modify_form";
    }

    @PostMapping("/reviews/modify/{review-id}")
    public String modifyReview(ReviewsDTO vo,
                               FileVo files,
                               HttpServletRequest request,
                               @Login UsersDTO login,
                               Model model,
                               @PathVariable("review-id") int id) throws IOException {


        ReviewsDTO review = reviewService.updateReview(id,login.getId(),vo,files);

        return "redirect:/walking-path/" + review.getWalkingPathsId();
    }

}
