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
    private int usersId; // 세션에서 로그인한 유저의 id를 가져와서 넣어주면 됍니다
    private int walkingPathsId; // 위 PathVariable("walking-paths-id") -> /walking-paths/ 1
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
