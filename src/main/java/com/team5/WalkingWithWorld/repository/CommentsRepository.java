package com.team5.WalkingWithWorld.repository;


import com.team5.WalkingWithWorld.domain.CommentsDTO;
import com.team5.WalkingWithWorld.entity.Comments;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Integer> {
    @Query("select " +
            "c.id, u.name, c.content, c.created_at " +
            "from Comments c " +
            "join Users u on c.users_id = u.id " +
            "order by c.created_at desc limit 5")
    public List<Comments> list();

    public List<CommentsDTO> getCommentById(int id);
    Comments save(Comments comments);
}
