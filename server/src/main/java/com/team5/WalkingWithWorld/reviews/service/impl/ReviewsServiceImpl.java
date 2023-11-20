package com.team5.WalkingWithWorld.reviews.service.impl;

import com.team5.WalkingWithWorld.global.config.auth.CustomPrincipal;
import com.team5.WalkingWithWorld.global.domain.FileVo;
import com.team5.WalkingWithWorld.global.domain.PhotosDTO;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.global.pagination.PageResponseDto;
import com.team5.WalkingWithWorld.global.pagination.PaginationService;
import com.team5.WalkingWithWorld.global.repository.PhotosRepository;
import com.team5.WalkingWithWorld.reviews.dto.ReviewsRequestDTO;
import com.team5.WalkingWithWorld.reviews.dto.ReviewsResponseDTO;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.reviews.repository.ReviewsRepository;
import com.team5.WalkingWithWorld.reviews.service.ReviewsService;
import com.team5.WalkingWithWorld.global.service.FileUpload;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import com.team5.WalkingWithWorld.walkingPaths.repository.WalkingPathsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final UsersRepository usersRepository;
    private final WalkingPathsRepository walkingPathsRepository;
    private final PhotosRepository photosRepository;
    private final FileUpload fileUpload;
    private final PaginationService paginationService;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository,
                              UsersRepository usersRepository,
                              WalkingPathsRepository walkingPathsRepository,
                              PhotosRepository photosRepository,
                              FileUpload fileUpload,
                              PaginationService paginationService) {
        this.reviewsRepository = reviewsRepository;
        this.usersRepository = usersRepository;
        this.walkingPathsRepository = walkingPathsRepository;
        this.photosRepository = photosRepository;
        this.fileUpload = fileUpload;
        this.paginationService = paginationService;
    }

    //리뷰 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<ReviewsResponseDTO> readReviewsList(Long walkingPathsId, Pageable pageable) {
        Page<Reviews> reviews = reviewsRepository.findAllByWalkingPathsId(walkingPathsId, pageable);

        List<ReviewsResponseDTO> reviewsResponseDTOList = reviews.stream().map(review -> ReviewsResponseDTO.from(review, photosRepository.findByReviews(review))).toList();

        List<Integer> barNumber = paginationService.getPaginationBarNumbers(reviews.getNumber(), reviews.getTotalPages());
        return new PageResponseDto<>(reviewsResponseDTOList, reviews, barNumber);
    }


    // 리뷰 작성
    @Override
    @Transactional
    public Reviews createReviews(ReviewsRequestDTO reviewsRequestDTO, CustomPrincipal customPrincipal, List<MultipartFile> files, Long walkingPathsId) throws IOException {

        Users users = usersRepository.findById(Math.toIntExact(customPrincipal.userId())).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        WalkingPaths walkingPaths = walkingPathsRepository.findById(Math.toIntExact(walkingPathsId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WALKINGPATHS_NOT_FOUND));

        Reviews reviews = reviewsRepository.save(reviewsRequestDTO.toEntity(users, walkingPaths));


        FileVo fileVo = new FileVo(files);

        Map<String, String> filesName = fileUpload.upload(fileVo);
        PhotosDTO photosDTO = new PhotosDTO();
        photosDTO.setReviewsId(reviews.getId());
        for (Map.Entry<String, String> entry : filesName.entrySet()) {
            photosDTO.setImgName(entry.getKey());
            photosDTO.setImgPath(entry.getValue());
            System.out.println("사진 확인" + photosDTO);
            photosRepository.save(photosDTO.toEntity(reviews, null));
        }

        return reviews;
    }

    // 리뷰 수정
    @Override
    @Transactional
    public Reviews updateReviews(Long walkingPathsId, Long reviewsId, ReviewsRequestDTO reviewsRequestDTO, List<MultipartFile> files) {
        Reviews reviews = reviewsRepository.findById(reviewsId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));

        Optional.ofNullable(reviewsRequestDTO.getContent()).ifPresent(reviews::updateContent);
        reviewsRepository.save(reviews);
        return reviews;
    }
    @Override
    @Transactional
    public void deleteReviews(Long reviewsId, String email){
        reviewsRepository.deleteReviewsByIdAndUsersEmail(reviewsId,email);
    }

}
