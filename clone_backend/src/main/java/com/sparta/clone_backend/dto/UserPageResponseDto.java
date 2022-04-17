package com.sparta.clone_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserPageResponseDto {

    private String nickName;
    private List<PostsResponseDto> likeposts;

    public UserPageResponseDto(String nickName, List<PostsResponseDto> likeposts){
        this.nickName = nickName;
        this.likeposts = likeposts;
    }
}


