package com.sparta.clone_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class PostLikeResponseDto {
    private Long postId;
    private String postTitle;
    private String postContents;
    private String imageUrl;
    private int price;
    private  String location;
    private String nickName;
    private String category;
    private boolean like;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
