package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    Optional<Object> findByNickName(String nickName);
    Optional<User> findByKakaoId(Long kakaoId);
}