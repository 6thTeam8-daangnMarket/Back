package com.sparta.clone_backend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateChkDto {
    private String username;
    private String nickname;

    public DuplicateChkDto (String username, String nickname){
        this.username = username;
        this.nickname = nickname;
    }
}
