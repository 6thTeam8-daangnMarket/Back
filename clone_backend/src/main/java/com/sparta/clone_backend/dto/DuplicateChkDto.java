package com.sparta.clone_backend.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateChkDto {
    private String userName;
    private String nickName;

    public DuplicateChkDto (String userName, String nickName){
        this.userName = userName;
        this.nickName = nickName;
    }
}
