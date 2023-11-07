package com.team5.WalkingWithWorld.walkingPaths.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team5.WalkingWithWorld.global.entity.AuditingFields;
import com.team5.WalkingWithWorld.users.entity.Users;
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
@Entity
@Builder
@ToString
public class WalkingPaths extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;
    private String title;
    private String addr;
}
