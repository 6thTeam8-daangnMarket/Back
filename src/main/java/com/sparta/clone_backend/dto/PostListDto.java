package com.sparta.clone_backend.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostListDto {

    private Long postId;
    private String postTitle;
    private String imageUrl;
    private int price;
    private String location;
    private String createdAt;
    private String modifiedAt;
    private int likeCount;
    private String category;

    // 전체 게시글 조회 - 페이징 처리
    public PostListDto(Long id, String postTitle, String imageUrl, int price, String location, String convertLocaldatetimeToTime, String convertLocaldatetimeToTime1, int like, String category) {
        this.postId = id;
        this.postTitle = postTitle;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
        this.createdAt = convertLocaldatetimeToTime;
        this.modifiedAt = convertLocaldatetimeToTime1;
        this.likeCount = like;
        this.category = category;
    }
}


