package com.sparta.clone_backend.controller;


import com.sparta.clone_backend.dto.PostRequestDto;

import com.sparta.clone_backend.dto.ResponseDto;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostService;
//
//import com.sparta.clone_backend.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.sparta.clone_backend.dto.PostDetailResponseDto;

import com.sparta.clone_backend.dto.PostResponseDto;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
//    private final S3Uploader s3Uploader;

    // 게시글 생성
    @PostMapping("/api/write")
    public ResponseEntity<String> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(requestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body("작성 완료!");
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

    // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public PostResponseDto editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.editPost(postId,requestDto, userDetails);
    }


    // 게시글 삭제
    @DeleteMapping("api/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
        return ResponseEntity.ok()
                .body("삭제 완료!");
    }




//    //유저정보, 장바구니 조회
//    @GetMapping("/user/mypage}")
//    public mypageResponseDto getPostDetail(@AuthentificationPrincipal UserDetailsImpl userDetails){
//        return postService.getmypage(userDetails);
//    }

//    // 이미지 업로드
//    @PostMapping("/images")
//    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
//        s3Uploader.upload(multipartFile, "static");
//        return "test";
//    }

}
