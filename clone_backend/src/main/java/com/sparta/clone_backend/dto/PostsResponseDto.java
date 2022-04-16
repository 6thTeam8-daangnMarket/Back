package com.sparta.clone_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostsResponseDto {

    private String postTitle;
    private String imageUrl;
    private int price;
    private String location;
    private LocalDateTime modifiedAt;
    private int likeCount;
    private Long postId;
    private LocalDateTime createdAt;

    public PostsResponseDto(String postTitle, String imageUrl, int price, String location, LocalDateTime createdAt, LocalDateTime modifiedAt, Long postId, int likeCount){
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.postId = postId;
        this.likeCount = likeCount;

    }
}


