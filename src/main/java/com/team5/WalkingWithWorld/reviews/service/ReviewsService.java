package com.team5.WalkingWithWorld.reviews.service;

import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.reviews.dto.ReviewsRequestDTO;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;

import java.io.IOException;

public interface ReviewsService {
    Reviews createReviews(ReviewsRequestDTO reviewsRequestDTO, UsersDTO usersDto, FileVo fileVo, Long walkingPathsId) throws IOException;
}
