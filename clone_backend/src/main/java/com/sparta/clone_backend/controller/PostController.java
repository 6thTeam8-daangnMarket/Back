package com.sparta.clone_backend.controller;


import com.sparta.clone_backend.dto.PostRequestDto;

import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.sparta.clone_backend.dto.PostDetailResponseDto;

import com.sparta.clone_backend.dto.PostResponseDto;


import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


    // 게시글 생성
    @PostMapping("/api/write")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(requestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body("작성 완료!");
    }

    // 게시글 삭제
    @DeleteMapping("api/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
        return ResponseEntity.ok()
                .body("삭제 완료!");
    }





    // 전체 게시글 조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> getPost() {
        return postService.getPost();
    }

    //특정게시글 조회
    @GetMapping("/api/posts/{postId}")
    public PostDetailResponseDto getPostDetail(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getPostDetail(postId, userDetails);
    }


//    //유저정보, 장바구니 조회
//    @GetMapping("/user/mypage}")
//    public mypageResponseDto getPostDetail(@AuthentificationPrincipal UserDetailsImpl userDetails){
//        return postService.getmypage(userDetails);
//    }


}
