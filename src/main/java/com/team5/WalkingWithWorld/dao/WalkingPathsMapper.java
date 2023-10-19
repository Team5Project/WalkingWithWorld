package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.domain.SearchDTO;
import com.team5.WalkingWithWorld.domain.WalkingPathsDTO;
import com.team5.WalkingWithWorld.domain.WalkingPathsMapDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface WalkingPathsMapper {
    @Select("select id, users_id, title, addr, created_at, created_by, modified_at, modified_by from walking_paths order by created_at desc")
    List<WalkingPathsDTO> readAll();
    @Select("select id, users_id, title, addr, created_at, created_by, modified_at, modified_by from walking_paths order by created_at desc")
    List<WalkingPathsMapDTO> readAllWalkingPathsMap();
    @Insert("Insert into walking_paths (users_id, title, addr, created_at, created_by) values (#{usersId}, #{title}, #{addr}, now(), #{createdBy})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addWalkingPath(WalkingPathsDTO dto);

    @Select("select id, users_id, title, addr, created_at, created_by, modified_at, modified_by from walking_paths where id = #{walkingPathId}")
    WalkingPathsMapDTO readWalkingPath(int WalkingPathId);

    @Select("select id, users_id, title, addr, created_at, created_by, modified_at, modified_by " +
            "from walking_paths where title like concat('%', #{keyword}, '%') or addr like concat('%', #{keyword}, '%') order by created_at desc")
    List<WalkingPathsMapDTO> searchWalkingPathByKeyword(String keyword);

    @Select({"<script> select walking_paths.id, users_id, title, addr, created_at, created_by, modified_at, modified_by",
            "from walking_paths inner join map on walking_paths.id = map.walking_paths_id" +
            "<where>" +
            "<if test='location != null'> addr like concat('%', #{location}, '%')</if>" +
            "<if test='keyword != null'> and (addr like concat('%', #{keyword}, '%') or title like concat('%', #{keyword}, '%'))</if>" +
            "<if test='minTime > 0'> and time >= ${minTime}</if>" +
            "<if test='maxTime > 0'> <![CDATA[and time <= ${maxTime}]]></if>" +
            "<if test='minDistance > 0'> and distance >= ${minDistance}</if>" +
            "<if test='maxDistance > 0'> <![CDATA[and distance <= ${maxDistance}]]></if>" +
            "</where>group by walking_paths.id order by created_at desc</script>"})
    List<WalkingPathsMapDTO> searchWalkingPathWithSearchDTO(SearchDTO dto);

    @Update("update walking_paths set title = #{title}, addr = #{addr}, modified_at = now(), modified_by = #{modifiedBy} where id = #{id}")
    int updateWalkingPath(WalkingPathsDTO dto);

    @Delete("delete from walking_paths where id = #{walkingPathId}")
    int deleteWalkingPath(int WalkingPathId);

    //@Select("select id,title,addr,created_at,created_by from walking_paths where users_id=#{userId}")
    //List<WalkingPathsDTO> realWithUserId(int userId);
}
