package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.reviews.dto.ReviewsRequestDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewsMapper {
    @Select("select * from reviews")
    public List<ReviewsRequestDTO> reviewlist();

    @Select("select * from reviews where walking_paths_id = #{id}")
    public List<ReviewsRequestDTO> reviewListByWalkingPathsId(int id);

    @Insert("insert into reviews(users_id,walking_paths_id,content,created_at,created_by,modified_at,modified_by) " +
            "values (#{usersId},#{walkingPathsId},#{content},now(),#{createdBy},#{modifiedAt},#{modifiedBy})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public boolean insertReviews (ReviewsRequestDTO vo);

    @Select("select * from reviews where id = #{id} and users_id = #{userId}")
    public ReviewsRequestDTO getReviewByIdAndReferenceUserId(int id, int userId);

    @Delete("delete from reviews where id = #{id} and users_id = #{userId}")
    public boolean deleteReviews (int id, int userId);

    @Update("update reviews set content = #{content}, modified_at = #{modifiedAt}, modified_by = #{modifiedBy} " +
            "where id = #{id}")
    public boolean updateReviews(ReviewsRequestDTO vo);


}


