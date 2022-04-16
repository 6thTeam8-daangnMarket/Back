package com.sparta.clone_backend.controller;


import com.sparta.clone_backend.dto.*;

import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostService;
//
//import com.sparta.clone_backend.service.S3Uploader;
import com.sparta.clone_backend.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final S3Uploader S3Uploader;

//    // 게시글 생성
//    @PostMapping("/api/write")
//    public ResponseEntity<String> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        postService.createPost(requestDto, userDetails.getUser());
//        return ResponseEntity.ok()
//                .body("작성 완료!");
//    }

//     게시글 작성
    @PostMapping("/api/write")
    public void upload(
            @RequestParam("postTitle") String postTitle,
            @RequestParam("postContents") String postContents,
            @RequestParam("imageUrl") MultipartFile multipartFile,
            @RequestParam("price") int price,
            @RequestParam("location") String location,
            @RequestParam("nickname") String nickname,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException
    {
        String imageUrl = S3Uploader.upload(multipartFile, "static");

        PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents, imageUrl, price, location, nickname);
        postService.createPost(postRequestDto, userDetails.getUser());
    }

    // 전체 게시글 조회
    @GetMapping("/api/posts")
    public List<PostsResponseDto> getPost() {
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

    //유저정보, 장바구니 조회
    @GetMapping("/user/mypage")
    public UserPageResponseDto getUserPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getUserPage(userDetails);
    }


}
