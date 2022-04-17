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
    private String userName;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String passWord;

    public User(String userName, String nickName, String passWordEncode) {
        this.userName = userName;
        this.nickName = nickName;
        this.passWord = passWordEncode;
    }
}
