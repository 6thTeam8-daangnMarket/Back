package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

}
