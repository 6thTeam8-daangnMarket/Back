package com.sparta.clone_backend.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String userName;
    private String nickName;
    private String passWord;
    private String passWordCheck;
    private String location;

}
