package com.sparta.clone_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
//@Builder
public class UserPageResponseDto {

    private String nickName;
    private List<PostListDto> likePosts;
    private int totalPage;

    public UserPageResponseDto(String nickName, List<PostListDto> likeposts){
        this.nickName = nickName;
        this.likePosts = likeposts;
    }

    // 마이 페이지 조회 (페이징 처리)
    public UserPageResponseDto(Page<PostListDto> userPage) {
        this.nickName = getNickName();
        this.likePosts = userPage.getContent();
        this.totalPage = userPage.getTotalPages();

    }
}


