package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    Optional<Object> findByNickName(String nickName);
    Optional<User> findByKakaoId(Long kakaoId);
}