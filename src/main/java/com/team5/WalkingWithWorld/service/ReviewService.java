package com.team5.WalkingWithWorld.service;

import com.team5.WalkingWithWorld.dao.PhotosMapper;
import com.team5.WalkingWithWorld.dao.ReviewsMapper;
import com.team5.WalkingWithWorld.domain.FileVo;
import com.team5.WalkingWithWorld.domain.PhotosDTO;
import com.team5.WalkingWithWorld.domain.ReviewsDTO;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewService {
    private ReviewsMapper reviewsMapper;
    private PhotosMapper photosMapper;
    private FileUpload fileUpload;

    public ReviewService(ReviewsMapper reviewsMapper, PhotosMapper photosMapper, FileUpload fileUpload) {
        this.reviewsMapper = reviewsMapper;
        this.photosMapper = photosMapper;
        this.fileUpload = fileUpload;
    }

    public int createReview(ReviewsDTO reviewDTO, FileVo multipartFile) throws IOException{
        //리뷰
        reviewsMapper.insertReviews(reviewDTO);

        //리뷰 이미지
        Map<String, String> filesName = fileUpload.upload(multipartFile);

        PhotosDTO photosDTO = new PhotosDTO();
        photosDTO.setReviewsId(reviewDTO.getId());
        System.out.println(reviewDTO.getId());
        photosDTO.setWalkingPathsId(reviewDTO.getWalkingPathsId());
        for(Map.Entry<String,String> entry:filesName.entrySet()){
            photosDTO.setImgName(entry.getKey());
            photosDTO.setImgPath(entry.getValue());
            photosMapper.addReviewPhotos(photosDTO);
        }
        return reviewDTO.getId();
    }

    public ReviewsDTO updateReview(int id, int loginId,ReviewsDTO reviewsDTO, FileVo multipartFile) throws IOException {
        ReviewsDTO review = reviewsMapper.getReviewByIdAndReferenceUserId(id,loginId);


        if(reviewsDTO.getContent() != null && !reviewsDTO.getContent().isEmpty()){
            review.setContent(reviewsDTO.getContent());
        } else{
            System.out.println("비어있습니다.");
        }

        Map<String, String> filesName = fileUpload.upload(multipartFile);

        PhotosDTO photosDTO = new PhotosDTO();
        photosDTO.setReviewsId(review.getId());
        System.out.println(review.getId());
        photosDTO.setWalkingPathsId(review.getWalkingPathsId());
        for(Map.Entry<String,String> entry:filesName.entrySet()){
            photosDTO.setImgName(entry.getKey());
            photosDTO.setImgPath(entry.getValue());
            photosMapper.addReviewPhotos(photosDTO);
        }
        reviewsMapper.updateReviews(review);
        return review;
    }
}
