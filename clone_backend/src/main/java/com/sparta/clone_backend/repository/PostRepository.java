package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndUserId(Long postId, Long user);

    List<Post> findAllByOrderByCreatedAtDesc();

    //검색어를 받아서 최신순으로 정렬한다.
    @Query(value = "select * from post p where p.post_title like %:keyword% order by p.modified_at desc", nativeQuery = true)
    List<Post> searchByKeyword(@Param("keyword")String keyword);

    //카테고리를 받아서 최신순으로 정렬한다.
    @Query(value = "select * from post p where p.category=:category order by p.modified_at desc", nativeQuery = true)
    List<Post> searchByCategory(@Param("category")String category);

}
