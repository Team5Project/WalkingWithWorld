package com.team5.WalkingWithWorld.dao;


import com.team5.WalkingWithWorld.global.domain.MapDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MapMapper {
    @Insert("insert into map (walking_paths_id, time, distance, coordinate_x, coordinate_y) values (#{walkingPathsId}, #{time}, #{distance}, #{coordinateX}, #{coordinateY})")
    boolean addMap(MapDTO dto);
    @Select("select time, distance, coordinate_x, coordinate_y from map where walking_paths_id = #{walkingPathsId}")
    List<MapDTO> ReadMap(int id);
}
