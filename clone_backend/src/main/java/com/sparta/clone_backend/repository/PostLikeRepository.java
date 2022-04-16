package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUsernameAndPost(String username, Post post);

}
