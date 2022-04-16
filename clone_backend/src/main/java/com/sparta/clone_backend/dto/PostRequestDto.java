package com.sparta.clone_backend.dto;

import lombok.Getter;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


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
    private String nickname;

    public PostRequestDto(String postTitle, String postContents, String imageUrl, int price, String location, String nickname) {
        this.postTitle = postTitle;
        this.postContents = postContents;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
        this.nickname = nickname;
    }
}

