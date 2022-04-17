package com.sparta.clone_backend.controller;


import ch.qos.logback.core.status.Status;
import com.sparta.clone_backend.dto.*;

import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostService;
//
//import com.sparta.clone_backend.service.S3Uploader;
import com.sparta.clone_backend.service.S3Uploader;
import com.sparta.clone_backend.utils.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<StatusMessage> nullex(Exception e) {
        System.err.println(e.getClass());
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.BAD_REQUEST);
        statusMessage.setData(null);
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
    }

//     게시글 작성
    @PostMapping("/api/write")
    public ResponseEntity<StatusMessage> upload(
            @RequestParam("postTitle") String postTitle,
            @RequestParam("postContents") String postContents,
            @RequestParam(value = "imageUrl") MultipartFile multipartFile,
            @RequestParam("price") int price,
            @RequestParam("location") String location,
            @RequestParam("nickName") String nickName,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException
    {
        String imageUrl = S3Uploader.upload(multipartFile, "static");

        PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents, imageUrl, price, location, nickName);

        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        statusMessage.setData(null);
        postService.createPost(postRequestDto, userDetails.getUser());
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
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
    public ResponseEntity<StatusMessage> editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        statusMessage.setData(postService.editPost(postId,requestDto, userDetails.getUser()));
        if (HttpHeaders.EMPTY.isEmpty()) {
            return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
//        return postService.editPost(postId,requestDto, userDetails);
    }


    // 게시글 삭제
    @DeleteMapping("api/posts/{postId}")
    public ResponseEntity<StatusMessage> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        statusMessage.setData(postService.deletePost(postId, userDetails.getUser()));
        if (HttpHeaders.EMPTY.isEmpty()) {
            return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
//        return ResponseEntity.ok()
//                .body("삭제 완료!");
    }

    // 유저정보, 장바구니 조회
    @GetMapping("/user/mypage")
    public UserPageResponseDto getUserPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getUserPage(userDetails);
    }


}
