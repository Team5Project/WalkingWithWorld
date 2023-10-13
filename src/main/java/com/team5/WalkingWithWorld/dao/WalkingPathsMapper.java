package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.domain.WalkingPathsDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WalkingPathsMapper {
    @Select("select id, users_id, title, addr, created_at, created_by from walking_paths")
    List<WalkingPathsDTO> readAll();
    @Insert("Insert into walking_paths (users_id, title, addr, created_at, created_by) values (#{users_id}, #{title}, #{addr}, now(), #{created_by})")
    Boolean addWalkingPath(WalkingPathsDTO dto);
    @Select("select id, users_id, title, addr, created_at, created_by from walking_paths where id = #{walkingPathId}")
    WalkingPathsDTO readOne(int WalkingPathId);

    //@Select("select id,title,addr,created_at,created_by from walking_paths where users_id=#{userId}")
    //List<WalkingPathsDTO> realWithUserId(int userId);
}
