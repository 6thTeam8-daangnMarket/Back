package com.sparta.clone_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;


    public PostLike(String userName, Post post) {
        this.userName = userName;
        this.post = post;
    }
}
