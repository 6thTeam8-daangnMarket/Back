package com.sparta.clone_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostResponseDto {
    private String username;
    private String postTitle;
    private String postContents;
    private String imageUrl;
    private int price;
    private String location;
    private String nickname;
    private String createdAt;
    private String modifiedAt;
}