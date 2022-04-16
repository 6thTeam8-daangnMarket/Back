package com.sparta.clone_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

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


//    public Post(PostRequestDto requestDto) {
//        this.postTitle = requestDto.getPostTitle();
//        this.postContents = requestDto.getPostContents();
//        this.imageUrl = requestDto.getImageUrl();
//        this.price = requestDto.
//    }
}
