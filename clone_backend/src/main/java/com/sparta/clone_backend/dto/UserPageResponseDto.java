package com.sparta.clone_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserPageResponseDto {

    private String nickname;
    private List<PostsResponseDto> likeposts;

    public UserPageResponseDto(String nickname, List<PostsResponseDto> likeposts){
        this.nickname = nickname;
        this.likeposts = likeposts;
    }
}


