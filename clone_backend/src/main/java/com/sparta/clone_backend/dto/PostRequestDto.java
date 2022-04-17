package com.sparta.clone_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class PostRequestDto {
    private String postTitle;
    private String postContents;
    private String imageUrl;
    private int price;
    private String location;
    private String createdAt;
    private String nickName;

    public PostRequestDto(String postTitle, String postContents, String imageUrl, int price, String location, String nickName) {
        this.postTitle = postTitle;
        this.postContents = postContents;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
        this.nickName = nickName;
    }
}

