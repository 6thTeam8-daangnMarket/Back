package com.sparta.clone_backend.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String passwordCheck;

}
