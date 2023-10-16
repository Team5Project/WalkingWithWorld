package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.domain.ReviewsDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewsMapper {
    @Select("select * from reviews")
    public List<ReviewsDTO> reviewslist();

    @Insert("insert into reviews(users_id,walking_paths_id,content,created_at,created_by,modified_at,modified_by) " +
            "values (#{usersId},#{walkingPathsId},#{content},#{createdAt},#{createdBy},#{modifiedAt},#{modifiedBy})")
    public boolean insertReviews (ReviewsDTO vo);

    @Delete("delete from reviews where id = #{id}")
    public boolean deleteReviews (int id);

    @Update("update reviews set content = #{content}, modified_at = #{modifiedAt}, modified_by = #{modifiedBy} " +
            "where id = #{id}")
    public boolean updateReviews(ReviewsDTO vo);


}


