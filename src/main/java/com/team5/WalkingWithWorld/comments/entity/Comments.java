package com.team5.WalkingWithWorld.comments.entity;

import com.team5.WalkingWithWorld.global.entity.AuditingFields;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class Comments extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(optional = false)
    @JoinColumn(name = "walking_paths_id")
    private WalkingPaths walkingPaths;

    private String content;


    public static Comments of (Long id,
                               Users users,
                               WalkingPaths walkingPaths,
                               String content
    ){
        return new Comments(id,
                users,
                walkingPaths,
                content
        );
    }

}
