package com.team5.WalkingWithWorld.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@ToString
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer walkingPathsId;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users users;

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;

    @CreationTimestamp
    private java.sql.Date created_at;
    private java.sql.Date modified_at;
}
