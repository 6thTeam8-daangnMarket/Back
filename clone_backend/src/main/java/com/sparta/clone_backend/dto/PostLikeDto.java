package com.sparta.clone_backend.dto;

import lombok.Getter;

@Getter
public class PostLikeDto {
    private Long postId;
    private String username;

    public PostLikeDto(Long postId, String username) {
        this.postId = postId;
        this.username = username;
    }
}
