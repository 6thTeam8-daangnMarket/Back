package com.sparta.clone_backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostResponseDto {
    private Long postId;
    private String userName;
    private String postTitle;
    private String postContents;
    private String imageUrl;
    private int price;
    private String location;
    private String nickName;
    private int likeCount;
    private LocalDateTime createdAt;

    // 게시글 생성
    public PostResponseDto(Long postId, String postTitle, String imageUrl, int price, String location, LocalDateTime createdAt, int likeCount){
        this.postId = postId;
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
    }

    // 게시글 수정
    public PostResponseDto(Long postId, String postContents) {
        this.postId = postId;
        this.postContents = postContents;
    }


}

