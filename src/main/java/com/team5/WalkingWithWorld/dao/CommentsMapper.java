package com.team5.WalkingWithWorld.dao;

import com.team5.WalkingWithWorld.domain.CommentsDTO;
import org.apache.ibatis.annotations.*;

import javax.annotation.processing.Generated;
import java.util.List;

@Mapper
public interface CommentsMapper {
    //랜덤 글 5개 보기
    @Select("select " +
                "c.id, u.name, c.content, c.created_at " +
            "from comments c " +
            "join users u on c.users_id = u.id " +
            "order by c.created_at desc limit 5")
    public List<CommentsDTO> list();

    @Select("select c.id, c.walking_paths_id, u.name,c.content, c.created_by,c.created_at " +
            "from comments c " +
            "join users u on c.users_id = u.id " +
            "where walking_paths_id = #{id} " +
            "order by c.created_at desc limit 5")
    public List<CommentsDTO> getCommentById(int id);

    //내글 리스트
//    @Select("select" +
//                "c.id , c.content, c.created_at" +
//            "from Comments c " +
//            "join users u on c.users_id = u.id" +
//            "where u.id = 세션아이디" +
//            "order by c.created_at desc;")
//    public List<CommentsDTO> myList();

    //글쓰기
    @Insert("insert into comments (users_id, walking_paths_id,content ,created_at) " +
            "values (#{users_id}, #{walkingPathsId}, #{content}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public boolean write(CommentsDTO comments);

    //글쓰기폼에 정보 전송
    @Select("select id, content from comments where id=${id}")
    public CommentsDTO updateComments(int id);

    //수정하기
    @Update("update comments set content = #{content}, modified_at = now() where id = ${id}")
    public boolean update(CommentsDTO comments);

    //삭제하기
    @Delete("DELETE FROM comments WHERE id = ${id}")
    public boolean delete(int id);
}
