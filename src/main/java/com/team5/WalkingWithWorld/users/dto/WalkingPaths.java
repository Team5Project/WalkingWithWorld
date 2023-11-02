package com.team5.WalkingWithWorld.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@ToString
public class WalkingPaths {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@ManyToOne
    //@Column(name = "users_id")
    private Integer usersId;
    private String title;
    private String addr;
    @CreatedDate
    private java.sql.Date createdAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private java.sql.Date modifiedAt;
    @LastModifiedBy
    private String modifiedBy;
}
