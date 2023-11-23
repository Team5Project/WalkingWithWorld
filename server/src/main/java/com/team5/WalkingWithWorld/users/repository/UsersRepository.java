package com.team5.WalkingWithWorld.users.repository;

import com.team5.WalkingWithWorld.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUsersByEmailAndPassword(String email, String password);

    Optional<Users> findUsersByEmail(String email);

    Optional<Users> findUsersById(Long id);
    Users getReferenceById(Long id);
}