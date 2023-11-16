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
//    @Query("select p.id,p.imgName,p.imgPath, p.reviews.id from Photos as p where Reviews = :reviews")
//    List<Photos> findAllByReviews(Reviews reviews);
    Photos findTop1ByWalkingPaths(WalkingPaths walkingPaths);

    void deleteAllByWalkingPaths(WalkingPaths walkingPaths);
}