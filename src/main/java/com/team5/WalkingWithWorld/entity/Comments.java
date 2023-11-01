package com.team5.WalkingWithWorld.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @ManyToOne
//    @JoinColumn(name="WalkingPathsDTO_ID")
    private Integer walkingPathsId;
    private Integer users_id;
    private String name;

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
    private java.sql.Date created_at;
    private java.sql.Date modified_at;
}
