package com.sparta.clone_backend.repository;

import com.sparta.clone_backend.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndUserId(Long postId, Long user);
//    Page<Post> findAllByOrderByModifiedAtDesc(Pageable pageable);

    List<Post> findAllByOrderByModifiedAtDesc();

    //mapdata,검색(searchtitle)를 받아서 (appear) 보여주는 정보만 보여주면서 정렬한다. 기준 cost,quility,recent
    @Query(nativeQuery = true, value = "select *  from post b where b.location=:mapdata and b.category =:category and b.title like %:searchtitle% order by b.price asc")
    List<Post> findByAndMapAndSearchByPrice(String mapdata, @Param("searchtitle")String searchtitle, String category);

//    @Query(nativeQuery = true, value = "select *  from board b where b.board_region=:mapdata and b.main_appear = true and b.title like %:searchtitle% order by b.dailyrentalfee desc LIMIT :limitcount")
//    List<Post> findByAndMapAndSearchByQuality(String mapdata, @Param("searchtitle")String searchtitle, String category);
//
//    @Query(nativeQuery = true, value = "select *  from board b where b.board_region=:mapdata and b.main_appear = true and b.title like %:searchtitle% order by b.modified_at desc LIMIT :limitcount")
//    List<Post> findByAndMapAndSearchByCreatedAt(String mapdata, @Param("searchtitle") String searchtitle, String category);
}
