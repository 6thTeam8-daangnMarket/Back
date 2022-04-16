package com.sparta.clone_backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;

import com.sparta.clone_backend.dto.PostRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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

//    private List<MultipartFile> imageUrl = new ArrayList<>();

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Post(PostRequestDto postRequestDto){
        this.postTitle = postRequestDto.getPostTitle();
        this.postContents = postRequestDto.getPostContents();
        this.imageUrl = postRequestDto.getImageUrl();
        this.price = postRequestDto.getPrice();
        this.location = postRequestDto.getLocation();
    }

    private Timestamp createDate;
    private Timestamp updateDate;

    public void update(Long postId, String postTitle, String postContents, int price) {
        this.id= postId;
        this.postTitle = postTitle;
        this.postContents  = postContents;
        this.price = price;
    }


//    public Post(PostRequestDto requestDto) {
//        this.postTitle = requestDto.getPostTitle();
//        this.postContents = requestDto.getPostContents();
//        this.imageUrl = requestDto.getImageUrl();
//        this.price = requestDto.
//    }
}
