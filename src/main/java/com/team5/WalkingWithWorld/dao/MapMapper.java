package com.team5.WalkingWithWorld.dao;


import com.team5.WalkingWithWorld.domain.MapDTO;
import com.team5.WalkingWithWorld.domain.SearchDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MapMapper {
    @Insert("insert into map (walking_paths_id, time, distance, coordinate_x, coordinate_y) values (#{walkingPathsId}, #{time}, #{distance}, #{coordinateX}, #{coordinateY})")
    boolean addMap(MapDTO dto);
    @Select("select time, distance, coordinate_x, coordinate_y from map where walking_paths_id = #{walkingPathsId}")
    List<MapDTO> readMap(int walkingPathsId);
    @Select("select time, distance from map where walking_paths_id = #{walkingPathsId} limit 1")
    List<MapDTO> readTimeDistance(int walkingPathsId);
    @Select({"<script>select walking_paths_id, time, distance from map" +
            "<where> time > -1" +
            "<if test='minTime > 0'> and time >= ${minTime}</if>" +
            "<if test='maxTime > 0'> <![CDATA[and time <= ${maxTime}]]></if>" +
            "<if test='minDistance > 0'> and distance >= ${minDistance}</if>" +
            "<if test='maxDistance > 0'> <![CDATA[and distance <= ${maxDistance}]]></if>" +
            "</where>" +
            "</script>"})
    List<MapDTO> searchMap(SearchDTO searchDTO);
}
