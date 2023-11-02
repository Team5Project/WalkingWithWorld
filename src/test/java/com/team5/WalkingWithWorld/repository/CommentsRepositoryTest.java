package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.domain.CommentsDTO;
import com.team5.WalkingWithWorld.entity.Comments;
import com.team5.WalkingWithWorld.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentsRepositoryTest {
    @Autowired
    private CommentsRepository cr;

    @Test
    public void list() {
        List<Comments> list = cr.findTop5ByWalkingPathsIdOrderByIdDesc(3);
        list.stream().forEach(System.out::println);
    }

    @Test
    void write() {
        Comments comment = new Comments();
        Users user = new Users();
        user.setId(1); // 사용자 ID 설정
        comment.setWalkingPathsId(2);
        comment.setContent("test");
        cr.save(comment);
        System.out.println(comment);
    }
}
