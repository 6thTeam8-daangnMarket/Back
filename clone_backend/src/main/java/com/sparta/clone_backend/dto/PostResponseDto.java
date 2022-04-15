package com.sparta.clone_backend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {

    private String postTitle;
    private String imageUrl;
    private int price;
    private String location;
    private LocalDateTime createdAt;
    private Long postId;
    private int likeCount;


    public PostResponseDto(String postTitle, String imageUrl, int price, String location, LocalDateTime createdAt, Long postId, int likeCount){
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
        this.createdAt = createdAt;
        this.postId = postId;
        this.likeCount = likeCount;

    }

    public PostResponseDto(String postTitle,String imageUrl, int price, String location, LocalDateTime createdAt, Long postId){
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
        this.createdAt = createdAt;
        this.postId = postId;
    }


}
