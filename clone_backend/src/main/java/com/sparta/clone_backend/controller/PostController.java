package com.sparta.clone_backend.controller;

import com.sparta.clone_backend.dto.PostDetailResponseDto;
import com.sparta.clone_backend.dto.PostRequestDto;
import com.sparta.clone_backend.dto.PostResponseDto;
import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


    // 전체 게시글 조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> getPost() {
        return postService.getPost();
    }

    //특정게시글 조회
    @GetMapping("/api/posts/{postId}")
    public PostDetailResponseDto getPostDetail(@PathVariable Long postId, @AuthentificationPrincipal UserDetailsImpl userDetails){
        return postService.getPostDetail(postId, userDetails);
    }


//    //유저정보, 장바구니 조회
//    @GetMapping("/user/mypage}")
//    public mypageResponseDto getPostDetail(@AuthentificationPrincipal UserDetailsImpl userDetails){
//        return postService.getmypage(userDetails);
//    }

}
