package com.team5.WalkingWithWorld.walkingPaths.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.team5.WalkingWithWorld.global.entity.AuditingFields;
import com.team5.WalkingWithWorld.global.entity.Map;
import com.team5.WalkingWithWorld.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @ToString.Exclude
    private Users users;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String addr;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    public void setUsers(Users users) {
        this.users = users;
    }

    public static WalkingPaths of(Long id,
                                  Users users,
                                  String title,
                                  String addr,
                                  int view) {
        return new WalkingPaths(null, users, title, addr, view);
    }

    public  void updateTitle(String title){
        this.title = title;
    }

    public void updateAddr(String addr){
        this.addr = addr;
    }

}
