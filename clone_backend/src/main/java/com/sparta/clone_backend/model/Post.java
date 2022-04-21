package com.sparta.clone_backend.model;


import com.sparta.clone_backend.dto.PostRequestDto;
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

    @Column(nullable = false)
    private String category;

    @Column(nullable = true)
    private String location;

    @Column(nullable = false)
    private String nickName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
//
    public Post(PostRequestDto postRequestDto){
        this.postTitle = postRequestDto.getPostTitle();
        this.postContents = postRequestDto.getPostContents();
        this.imageUrl = postRequestDto.getImageUrl();
        this.price = postRequestDto.getPrice();
        this.category = postRequestDto.getCategory();
    }

    public void update(Long postId, String postTitle, String postContents, int price) {
        this.id= postId;
        this.postTitle = postTitle;
        this.postContents  = postContents;
        this.price = price;
    }

    // 게시글 내용 수정
    public void update(Long postId, PostRequestDto requestDto) {
        this.id = postId;
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
        this.imageUrl = requestDto.getImageUrl();
        this.price = requestDto.getPrice();
        this.category =requestDto.getCategory();
    }
}
