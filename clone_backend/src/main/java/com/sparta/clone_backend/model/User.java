package com.sparta.clone_backend.model;

import com.sparta.clone_backend.dto.SignupRequestDto;
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

    @Column(nullable = false)
    private String location;

    public User(String userName, SignupRequestDto signupRequestDto, String passWordEncode) {
        this.userName = userName;
        this.nickName = signupRequestDto.getNickName();
        this.passWord = passWordEncode;
        this.location = signupRequestDto.getLocation();
    }
}
