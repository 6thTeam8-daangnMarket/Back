package com.sparta.clone_backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;

import com.sparta.clone_backend.dto.PostRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends Timestamped{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    private Timestamp createDate;
    private Timestamp updateDate;
//
    public Post(PostRequestDto postRequestDto){
        this.postTitle = postRequestDto.getPostTitle();
        this.postContents = postRequestDto.getPostContents();
        this.imageUrl = postRequestDto.getImageUrl();
        this.price = postRequestDto.getPrice();
        this.location = postRequestDto.getLocation();
    }

    public void update(Long postId, String postTitle, String postContents, int price) {
        this.id= postId;
        this.postTitle = postTitle;
        this.postContents  = postContents;
        this.price = price;
    }

}
