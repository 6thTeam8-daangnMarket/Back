package com.sparta.clone_backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private String location;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String category;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // 게시글 내용 수정
    public void update(Long postId, String postTitle, String postContents, String imageUrl, int price, String category) {
        this.id= postId;
        this.postTitle = postTitle;
        this.postContents  = postContents;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

}
