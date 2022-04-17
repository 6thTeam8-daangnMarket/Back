package com.sparta.clone_backend.dto;

import lombok.Getter;

@Getter
public class PostLikeDto {
    private Long postId;
    private String userName;

    public PostLikeDto(Long postId, String userName) {
        this.postId = postId;
        this.userName = userName;
    }
}
