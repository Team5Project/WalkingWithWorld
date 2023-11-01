package com.team5.WalkingWithWorld.repository;

import com.team5.WalkingWithWorld.entity.Users;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUsersByEmailAndPassword(String email, String password);
}