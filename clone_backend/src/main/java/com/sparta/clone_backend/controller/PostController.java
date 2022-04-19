package com.sparta.clone_backend.controller;


import com.sparta.clone_backend.dto.PostRequestDto;
import com.sparta.clone_backend.dto.UserPageResponseDto;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostService;
import com.sparta.clone_backend.service.S3Uploader;
import com.sparta.clone_backend.utils.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;

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

    @ExceptionHandler({MissingServletRequestParameterException.class, NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<StatusMessage> nullex(Exception e) {
        System.err.println(e.getClass());
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.BAD_REQUEST);
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
    }

//     게시글 작성
    @PostMapping("/api/write")
    public ResponseEntity<StatusMessage> upload(
            @RequestParam("postTitle") String postTitle,
            @RequestParam("postContents") String postContents,
            @RequestParam(value = "imageUrl") MultipartFile multipartFile,
            @RequestParam("price") int price,
            @RequestParam("category") String category,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException
    {
        String imageUrl = S3Uploader.upload(multipartFile, "static");

        PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents, imageUrl, price, category);

        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        postService.createPost(postRequestDto, userDetails.getUser());
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }
//
//    // 전체 게시글 조회
//    @GetMapping("/api/posts")
//    public Page<Post> getPost(@PageableDefault(size = 10) Pageable pageable
////        @RequestParam("page") int page,
////        @RequestParam("size") int size,
////        @RequestParam("sortBy") String sortBy,
////        @RequestParam("isAsc") boolean isAsc,
////        @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
////        Long userId = userDetails.getUser().getId();
////        page = page - 1;
//        return postService.getPost(pageable);
//    }
//
//    //특정게시글 조회
//    @GetMapping("/api/posts/{postId}")
//    public PostDetailResponseDto getPostDetail(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return postService.getPostDetail(postId, userDetails);
//    }

    @GetMapping("/api/posts")
    public ResponseEntity<StatusMessage> getPost() {
        HttpHeaders httpHeaders = new HttpHeaders();
        StatusMessage statusMessage = new StatusMessage();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        postService.getPost();
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);

    }

    //특정게시글 조회
    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<StatusMessage> getPostDetail(@PathVariable Long postId){
        HttpHeaders httpHeaders = new HttpHeaders();
        StatusMessage statusMessage = new StatusMessage();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        postService.getPostDetail(postId);
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }
    // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<StatusMessage> editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        postService.editPost(postId,requestDto, userDetails.getUser());
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }


    // 게시글 삭제
    @DeleteMapping("api/posts/{postId}")
    public ResponseEntity<StatusMessage> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        postService.deletePost(postId, userDetails.getUser());
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

    // 유저정보, 장바구니 조회
    @GetMapping("/user/mypage")
    public UserPageResponseDto getUserPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getUserPage(userDetails);
    }


}
