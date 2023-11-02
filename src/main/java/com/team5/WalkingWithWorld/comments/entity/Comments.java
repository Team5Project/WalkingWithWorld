package com.team5.WalkingWithWorld.comments.entity;

import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.walkingPaths.entity.WalkingPaths;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(optional = false)
    @JoinColumn(name = "walking_paths_id")
    private WalkingPaths walkingPaths;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
    @LastModifiedBy
    private String modifiedBy;

    public static Comments of (int id,
                              Users users,
                              WalkingPaths walkingPaths,
                              String content,
                              LocalDateTime createdAt,
                              String createdBy,
                              LocalDateTime modifiedAt,
                              String modifiedBy){
        return new Comments(id,
                users,
                walkingPaths,
                content,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy);
    }

}
