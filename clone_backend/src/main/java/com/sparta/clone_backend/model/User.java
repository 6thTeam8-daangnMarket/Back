package com.sparta.clone_backend.model;

import com.sparta.clone_backend.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
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

    @Column(nullable = true)
    private String location;

    @Column(unique = true)
    private Long kakaoId;

    public User(String userName, SignupRequestDto signupRequestDto, String passWordEncode) {
        this.userName = userName;
        this.nickName = signupRequestDto.getNickName();
        this.passWord = passWordEncode;
        this.location = signupRequestDto.getLocation();
    }
    public User(String userName, String nickName, String passWordEncode, Long kakaoId) {
        this.userName = userName;
        this.nickName = nickName;
        this.passWord = passWordEncode;
        this.kakaoId = kakaoId;
    }
}
