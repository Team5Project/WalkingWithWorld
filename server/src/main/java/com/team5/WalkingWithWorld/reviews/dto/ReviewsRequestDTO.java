package com.team5.WalkingWithWorld.reviews.dto;

import com.team5.WalkingWithWorld.reviews.entity.Reviews;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewsRequestDTO {

    private String content;

    public  Reviews toEntity(Users users, WalkingPaths walkingPaths){
        return Reviews.of(
                null,
                users,
                walkingPaths,
                content
        );
    }
}
