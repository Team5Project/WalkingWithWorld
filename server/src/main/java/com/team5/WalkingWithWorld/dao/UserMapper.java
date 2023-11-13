package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.users.dto.LoginDto;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    //로그인
    @Select("select * from users where email = #{email} and password=#{password}")
    public UsersDTO getUser(LoginDto loginDto);

    @Select("select * from users")
    public List<UsersDTO> getUsers();
    @Select("select * from users where id = ${userId}") // createWalkingPath()에서 사용
    public UsersDTO getUserById(int userId);
    @Insert("insert into users values (null,#{name},#{password},#{email},#{addr})")
    public boolean createUser(UsersDTO userDto);
}
