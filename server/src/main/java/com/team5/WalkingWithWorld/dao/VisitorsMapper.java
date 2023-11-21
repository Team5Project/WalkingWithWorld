package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.visitors.dto.VisitorsDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface VisitorsMapper {
    @Select("select * from visitors order by created_at desc")
    public List<VisitorsDTO> visitorslist();

    @Insert("insert into visitors(name, content, created_at, password) values (#{name}, #{content}, now(), #{password})")
    public boolean insertVisitors(VisitorsDTO dto);

    @Delete("delete from visitors where id=${id} and password=${password}")
    public boolean deleteVisitors(Long id ,String password);

    @Update("update visitors set name = #{name}, password = ${password}, content = #{content}, modified_at = now() where id=${id}")
    public boolean updateVisitors(VisitorsDTO dto);

}
