package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);
    Boolean existsByNickname(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByNickname(String nickname);
}