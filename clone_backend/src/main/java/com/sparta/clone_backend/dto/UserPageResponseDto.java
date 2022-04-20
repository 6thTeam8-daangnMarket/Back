package com.sparta.clone_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
public class UserPageResponseDto {

    private String nickName;
    private List<PostListDto> likeposts;
    private int totalpage;


    public UserPageResponseDto(Page<PostListDto> userPage) {
        this.nickName = getNickName();
        this.likeposts = userPage.getContent();
        this.totalpage = userPage.getTotalPages();
    }
}


