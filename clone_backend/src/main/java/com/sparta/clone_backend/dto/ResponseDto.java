package com.sparta.clone_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {
    private boolean result;
    private String errormessage;
    private String username;
    private String nickname;

    public ResponseDto(boolean result) {
        this.result = result;
    }

    public ResponseDto(boolean result, String message) {
        this.result = result;
        this.errormessage = message;
    }

    public ResponseDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public ResponseDto() {

    }
}
