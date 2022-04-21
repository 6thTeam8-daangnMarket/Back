package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndUserId(Long postId, Long user);
//    Page<PostListDto> findAllByOrderByModifiedAtDesc(Pageable pageable);
//
//    List<Post> findAllByOrderByModifiedAtDesc();

    List<Post> findAllByOrderByCreatedAtDesc();

}
