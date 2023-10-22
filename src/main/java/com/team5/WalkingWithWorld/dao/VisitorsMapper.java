package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.domain.VisitorsDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VisitorsMapper {
    @Select("select * from visitors")
    public List<VisitorsDTO> visitorslist();

    @Insert("insert into visitors(name, content, created_at, password) values (#{name}, #{content}, now(), #{password})")
    public boolean insertVisitors(VisitorsDTO dto);

    @Delete("delete from visitors where id=#{id} and password=#{password}")
    public boolean deleteVisitors(int id ,String password);

}
