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
    private String createdAt;
    private int likeCount;
    private Long postId;
    private String modifiedAt;

    public PostsResponseDto(String postTitle, String imageUrl, int price, String location, String createdAt, String modifiedAt, Long postId, int likeCount){
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


