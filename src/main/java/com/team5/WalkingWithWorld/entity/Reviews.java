package com.team5.WalkingWithWorld.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@ToString
@Entity
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Users users;
    @ManyToOne
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
}
