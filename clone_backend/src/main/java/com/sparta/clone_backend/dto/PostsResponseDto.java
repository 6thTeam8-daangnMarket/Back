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
//    private Long totalElements;

    public PostsResponseDto(Page<PostListDto> showAllPost) {
        this.postList = showAllPost.getContent();
        this.totalPage = showAllPost.getTotalPages();
//        this.totalElements = showAllPost.getTotalElements();
    }
}
