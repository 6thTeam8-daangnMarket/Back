package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.dto.PostListDto;
import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.model.PostLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndUserId(Long postId, Long user);
    Page<PostListDto> findAllByOrderByModifiedAtDesc(Pageable pageable);

    List<Post> findAllByOrderByModifiedAtDesc();

    List<Post> findAllByOrderByCreatedAtDesc();

}
