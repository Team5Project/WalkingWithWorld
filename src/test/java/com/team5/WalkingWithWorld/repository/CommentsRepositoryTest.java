package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.domain.CommentsDTO;
import com.team5.WalkingWithWorld.entity.Comments;
import com.team5.WalkingWithWorld.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CommentsRepositoryTest {
    @Autowired
    CommentsRepository cr;

    @Test
    public void list() {
        List<Comments> list = cr.findTop5ByWalkingPathsIdOrderByIdDesc(2);
        list.stream().forEach(System.out::println);
    }

    @Test
    void write() {
        Comments comment = new Comments();
        Users users = new Users();
        users.setId(1);
        comment.setUsers(users);
        comment.setWalkingPathsId(2);
        comment.setContent("test");
        cr.save(comment);
        System.out.println(comment);
    }

    @Test
    void updateComments(){
        Optional<Comments> comments = cr.findById(2);
        comments.get().setContent("update test");
        cr.save(comments.get());
        System.out.println(comments.get());
    }

    @Test
    void delete(){
        cr.deleteById(18);
        System.out.println(cr.findAll());
    }
}
