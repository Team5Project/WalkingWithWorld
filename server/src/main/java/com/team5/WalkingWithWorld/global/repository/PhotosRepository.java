package com.team5.WalkingWithWorld.global.repository;

import com.team5.WalkingWithWorld.global.entity.Photos;
import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotosRepository extends JpaRepository<Photos, Integer> {
    List<Photos> findByWalkingPaths(WalkingPaths walkingPaths);
    //TODO 리뷰와 사진 호출 시 null 값이면 에러가 나기에 칼럼을 선택해서 호출
    @Query("select p.id, p.imgName, p.imgPath, p.reviews.id from Photos as p where Reviews = :reviews")
    List<Photos> findALLByReviews(Reviews reviews);
    Photos findTop1ByWalkingPaths(WalkingPaths walkingPaths);

    void deleteAllByWalkingPaths(WalkingPaths walkingPaths);
}