package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.domain.WalkingPathsMapDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MapMapper {
    @Insert("insert into map (walking_paths_id, time, distance, course) values (#{id}, #{time}, #{distance}, #{course})")
    boolean addMap(WalkingPathsMapDTO dto);
    @Select("select time, distance, course from map where walking_paths_id = #{id}")
    WalkingPathsMapDTO ReadMap(int id);
}
