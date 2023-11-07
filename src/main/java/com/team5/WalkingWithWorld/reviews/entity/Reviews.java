package com.team5.WalkingWithWorld.reviews.entity;

import com.team5.WalkingWithWorld.global.entity.AuditingFields;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class Reviews extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Users users;
    @ManyToOne
    private WalkingPaths walkingPaths;
    private String content;




    public static Reviews of(Long id,Users users, WalkingPaths walkingPaths, String content) {
        return new Reviews(null,users, walkingPaths, content);
    }
}
