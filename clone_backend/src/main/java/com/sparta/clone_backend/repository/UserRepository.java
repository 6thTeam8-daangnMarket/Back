package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
<<<<<<< HEAD
    Optional<User> findByNickname(String nickname);
}
=======

    // 아이디 중복 검사
    boolean existsByUsername(String username);

    // 닉네임 중복 검사
    boolean existsByNickname(String nickName);
}
>>>>>>> origin/write&detail
