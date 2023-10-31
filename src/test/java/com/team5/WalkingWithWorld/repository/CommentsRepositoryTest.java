package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.Comments;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentsRepositoryTest {
    @Autowired
    private CommentsRepository cr;

    @Test
    void findAll(){
        List<Comments> list = cr.findAll();
        list.stream().forEach(System.out::println);
    }

    @Test
    void write(){
        Comments co = new Comments();
        co.setUsers_id(1);
        co.setWalkingPathsId(2);
        co.setContent("test");
        cr.save(co);
    }
}
