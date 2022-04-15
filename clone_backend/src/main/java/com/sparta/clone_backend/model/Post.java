package com.sparta.clone_backend.model;

import com.sparta.clone_backend.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
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

//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private User user;

    public Post(PostRequestDto postRequestDto){
        this.postTitle = postRequestDto.getPostTitle();
        this.postContents = postRequestDto.getPostContents();
        this.imageUrl = postRequestDto.getImageUrl();
        this.price = postRequestDto.getPrice();
        this.location = postRequestDto.getLocation();
    }

}
