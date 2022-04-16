package com.sparta.clone_backend.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String postTitle;
    private String postContents;
    private String imageUrl;
    private int price;
    private String location;
    private String createdAt;
    private String nickname;
}
