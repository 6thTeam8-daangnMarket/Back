package com.sparta.clone_backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class PostsResponseDto {
    private List<PostListDto> postList;
    private int totalPage;

    // 전체 페이지 조회 시 페이징 처리
    public PostsResponseDto(Page<PostListDto> showAllPost) {
        this.postList = showAllPost.getContent();
        this.totalPage = showAllPost.getTotalPages();
    }
}
