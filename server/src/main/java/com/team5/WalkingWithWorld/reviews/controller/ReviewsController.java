package com.team5.WalkingWithWorld.reviews.controller;

import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.reviews.dto.ReviewsRequestDTO;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.reviews.service.ReviewsService;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ReviewsController {
    private final ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }
//MVC Controller
/*    @PostMapping("/reviews/{walking-paths-id}/write")
    public String createReview(@Login UsersDTO loginUser,
                               @PathVariable("walking-paths-id") int id,
                               ReviewsRequestDTO reviewsRequestDTO,
                               FileVo files,
                               HttpServletRequest request) throws IOException {
        ModelAndView mav;
        reviewsRequestDTO.setUsersId(loginUser.getId());
        reviewsRequestDTO.setWalkingPathsId(id);
        int reviewId = reviewService.createReview(reviewsRequestDTO, files);


        mav = walkingPathsController.getWalkingPathById(id, request);

        return "redirect:/walking-path/" + id;
    }
    @GetMapping("/reviews/{walking-paths-id}/write")
    public String reviews(@PathVariable("walking-paths-id") int id,
                          Model model,
                          HttpServletRequest request) {
        String referer = request.getHeader("Referer");

        WalkingPathsMapDTO walkingPaths = pathsMapper.readWalkingPathMap(id);
        walkingPaths.setMapList(mapMapper.ReadMap(id) );
        walkingPaths.setPhotosList(photoDao.readPhotos(id));


        model.addAttribute("walkingPaths", walkingPaths);
        model.addAttribute("referer");

        return "reviews_write_form";
    }

    @PostMapping("/reviews/list/{walking-paths-id}")
    public String getReviewList(Model model,
                                @PathVariable("walking-paths-id") int id
    ) {

        System.out.println(id);
        List<ReviewsRequestDTO> list = dao.reviewListByWalkingPathsId(id);

        for (ReviewsRequestDTO dto : list) {
            dto.setPhotosList(photoDao.readReviewPhotos(dto.getId()));
        }

        model.addAttribute("walkingPaths", walkingPathService.readWalkingPathById(id));
        model.addAttribute("reviewList", list);

        if (list.isEmpty()) {
            return "reviews ::#static_reviews";
        }

        return "reviews :: #reviews";
    }


    @PostMapping("/reviews/delete")
    public String delete(@RequestBody ReviewsRequestDTO reviewsRequestDTO,
                         @Login UsersDTO login,
                         Model model) {
        boolean result = dao.deleteReviews(reviewsRequestDTO.getId(), login.getId());

        if (!Objects.equals(login.getName(), reviewsRequestDTO.getCreatedBy())) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        if (!result) {
            throw new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND);
        }

        return "redirect:/walking-path/" + reviewsRequestDTO.getWalkingPathsId();
    }

    @GetMapping(value = "/reviews/modify/{review-id}")
    public String getModifyForm(@PathVariable("review-id") int id,
                                @Login UsersDTO login,
                                Model model) {
        ReviewsRequestDTO review = reviewsMapper.getReviewByIdAndReferenceUserId(id, login.getId());
        review.setPhotosList(photoDao.readReviewPhotos(id));

        if (!Objects.equals(review.getCreatedBy(), login.getName())) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        model.addAttribute("review", review);
        return "reviews_modify_form";
    }

    @PostMapping("/reviews/modify/{review-id}")
    public String modifyReview(ReviewsRequestDTO vo,
                               FileVo files,
                               @Login UsersDTO login,
                               @PathVariable("review-id") int id) throws IOException {


        ReviewsRequestDTO review = reviewService.updateReview(id,login.getId(),vo,files);

        return "redirect:/walking-path/" + review.getWalkingPathsId();
    }*/
    @GetMapping("/{walking-paths-id}/reviews")
    public ResponseEntity getReviewsList(@PathVariable("walking-paths-id") Long id,
                                         @PageableDefault Pageable pageable){
        PageResponseDto pageResponseDto = reviewsService.readReviewsList(id, pageable);

        return new ResponseEntity(pageResponseDto, HttpStatus.OK);
    }

    //리뷰 작성
    @PostMapping(value = "/{walking-paths-id}/reviews",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createReviews(@Login UsersDTO loginUser,
                                        @PathVariable("walking-paths-id") Long id,
                                        @RequestPart ReviewsRequestDTO reviewsRequestDTO,
                                        @RequestPart List<MultipartFile> files) throws IOException {

        Reviews reviews = reviewsService.createReviews(reviewsRequestDTO, loginUser,files , id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PatchMapping(value = "/{walking-paths-id}/reviews/{reviews-id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity updateReviews(@Login UsersDTO loginUser,
                                        @PathVariable("walking-paths-id") Long walkingPathsId,
                                        @PathVariable("reviews-id") Long reviewId,
                                        @RequestPart ReviewsRequestDTO reviewsRequestDTO,
                                        @RequestPart List<MultipartFile> files){
        Reviews reviews = reviewsService.updateReviews(walkingPathsId, reviewId, reviewsRequestDTO, files);
        return new ResponseEntity(reviews, HttpStatus.OK);
    }

}
