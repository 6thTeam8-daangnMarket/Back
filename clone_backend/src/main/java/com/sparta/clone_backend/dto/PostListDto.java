package com.sparta.clone_backend.dto;

import com.sparta.clone_backend.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
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
    private boolean like;

    // 전체 페이지 게시글 조회
    public PostListDto(Post post, String convertLocaldatetimeToTime, String convertLocaldatetimeToTime1, int likeCount, Boolean like) {
        this.postId = post.getId();
        this.postTitle = post.getPostTitle();
        this.imageUrl = post.getImageUrl();
        this.price = post.getPrice();
        this.location = post.getLocation();
        this.createdAt = convertLocaldatetimeToTime;
        this.modifiedAt = convertLocaldatetimeToTime1;
        this.likeCount = likeCount;
        this.category = post.getCategory();
        this.like = like;
    }

    // 유저 페이지 게시글 조회
    public PostListDto(Post likedPost, String convertLocaldatetimeToTime, String convertLocaldatetimeToTime1, int likeCount) {
        this.postId = likedPost.getId();
        this.postTitle = likedPost.getPostTitle();
        this.imageUrl = likedPost.getImageUrl();
        this.price = likedPost.getPrice();
        this.location = likedPost.getLocation();
        this.createdAt = convertLocaldatetimeToTime;
        this.modifiedAt = convertLocaldatetimeToTime1;
        this.likeCount = likeCount;
        this.category = likedPost.getCategory();
        this.like = true;
    }
}


