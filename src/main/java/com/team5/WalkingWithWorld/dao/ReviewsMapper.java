package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.domain.ReviewsDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewsMapper {
    @Select("select * from reviews")
    public List<ReviewsDTO> reviewlist();

    @Select("select * from reviews where walking_paths_id = #{id}")
    public List<ReviewsDTO> reviewListById(int id);

    @Insert("insert into reviews(users_id,walking_paths_id,content,created_at,created_by,modified_at,modified_by) " +
            "values (#{usersId},#{walkingPathsId},#{content},now(),#{createdBy},#{modifiedAt},#{modifiedBy})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public boolean insertReviews (ReviewsDTO vo);

    @Delete("delete from reviews where id = #{id}")
    public boolean deleteReviews (int id);

    @Update("update reviews set content = #{content}, modified_at = #{modifiedAt}, modified_by = #{modifiedBy} " +
            "where id = #{id}")
    public boolean updateReviews(ReviewsDTO vo);


}


