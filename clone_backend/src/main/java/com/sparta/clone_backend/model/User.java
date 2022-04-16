package com.sparta.clone_backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

<<<<<<< HEAD
    public User(String username, String passwordEncode,String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.password = passwordEncode;
=======
    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
>>>>>>> origin/write&detail
    }
}
