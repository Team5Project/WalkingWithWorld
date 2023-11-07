package com.team5.WalkingWithWorld.reviews.service.impl;

import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.global.domain.PhotosDTO;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.global.repository.PhotosRepository;
import com.team5.WalkingWithWorld.reviews.dto.ReviewsRequestDTO;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.reviews.repository.ReviewsRepository;
import com.team5.WalkingWithWorld.reviews.service.ReviewsService;
import com.team5.WalkingWithWorld.service.FileUpload;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final UsersRepository usersRepository;
    private final WalkingPathsRepository walkingPathsRepository;
    private final PhotosRepository photosRepository;
    private final FileUpload fileUpload;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository,
                              UsersRepository usersRepository,
                              WalkingPathsRepository walkingPathsRepository,
                              PhotosRepository photosRepository,
                              FileUpload fileUpload) {
        this.reviewsRepository = reviewsRepository;
        this.usersRepository = usersRepository;
        this.walkingPathsRepository = walkingPathsRepository;
        this.photosRepository = photosRepository;
        this.fileUpload = fileUpload;
    }
    @Override
    public Reviews createReviews(ReviewsRequestDTO reviewsRequestDTO, UsersDTO usersDto, FileVo fileVo, Long walkingPathsId) throws IOException {
        // 리뷰 작성
        Users users = usersRepository.findById(Math.toIntExact(usersDto.getId())).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        WalkingPaths walkingPaths = walkingPathsRepository.findById(Math.toIntExact(walkingPathsId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));

        Reviews reviews = reviewsRepository.save(reviewsRequestDTO.toEntity(users, walkingPaths));

        Map<String, String> filesName = fileUpload.upload(fileVo);
        PhotosDTO photosDTO = new PhotosDTO();
        photosDTO.setReviewsId(reviews.getId());
        for(Map.Entry<String,String> entry:filesName.entrySet()){
            photosDTO.setImgName(entry.getKey());
            photosDTO.setImgPath(entry.getValue());
            photosRepository.save(photosDTO.toEntity(reviews,null));
        }
        return reviews;
    }
}
