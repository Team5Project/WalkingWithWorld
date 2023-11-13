package com.team5.WalkingWithWorld.reviews.service;

import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.reviews.dto.ReviewsRequestDTO;
import com.team5.WalkingWithWorld.reviews.dto.ReviewsResponseDTO;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewsService {
    //리뷰 리스트 조회
    PageResponseDto<ReviewsResponseDTO> readReviewsList(Long walkingPathsId, Pageable pageable);

    Reviews createReviews(ReviewsRequestDTO reviewsRequestDTO, UsersDTO usersDto, List<MultipartFile> files, Long walkingPathsId) throws IOException;

    // 리뷰 수정
    Reviews updateReviews(Long walkingPathsId, Long reviewsId, ReviewsRequestDTO reviewsRequestDTO, List<MultipartFile> files);
}
