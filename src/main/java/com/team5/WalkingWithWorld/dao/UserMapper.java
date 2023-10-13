package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.Users;
import com.team5.WalkingWithWorld.domain.UsersDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    //로그인
    @Select("select * from users where email = #{email} and password=#{password}")
    public Users getUser(LoginDto loginDto);


    @Select("select * from users")
    public List<UsersDto> getUsers();
    @Insert("insert into users values (null,#{name},#{password},#{email},#{addr})")
    public boolean createUser(UsersDto userDto);
}
