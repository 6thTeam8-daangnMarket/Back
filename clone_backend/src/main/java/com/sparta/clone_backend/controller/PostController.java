package com.sparta.clone_backend.controller;


import com.sparta.clone_backend.dto.*;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostService;
import com.sparta.clone_backend.service.S3Uploader;
import com.sparta.clone_backend.utils.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import java.util.List;
import java.util.NoSuchElementException;

import static com.sparta.clone_backend.utils.StatusMessage.*;

@RequiredArgsConstructor
@RestController
@ResponseStatus(HttpStatus.OK)
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
        statusMessage.setStatus(StatusEnum.BAD_REQUEST);
        statusMessage.setData(null);
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    // 게시글 작성 -> 토큰이 없을 경우 500에러/예외처리 필요할 것 같음 (사용자 권한 적용)

    @PostMapping("/api/write")
    public ResponseEntity<String> upload(
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
        postService.createPost(postRequestDto, userDetails.getUser());
        return ResponseEntity.status(201)
                .header("status","201")
                .body("201");
}

    // 전체 게시글 조회, 페이징 처리 완료
    @GetMapping("/api/posted/{pageno}")
    public PostsResponseDto showAllPost(@PathVariable("pageno") int pageno) {
        return new PostsResponseDto(postService.showAllPost(pageno - 1));
    }

//    특정게시글 조회
    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable Long postId){
        return ResponseEntity.status(201)
                .header("status","201")
                .body(postService.getPostDetail(postId));
    }

    // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<PostResponseDto> editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<PostResponseDto>(postService.editPost(postId,requestDto, userDetails.getUser()), HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("api/posts/{postId}")
    public ResponseEntity<StatusMessage> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusEnum.OK);
        statusMessage.setData(postService.deletePost(postId, userDetails.getUser()));
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

//     유저정보, 장바구니 조회
    @GetMapping("/user/mypage/{pageno}")
    public UserPageResponseDto getUserPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("pageno") int pageno){
        return new UserPageResponseDto(userDetails, postService.getUserPage(userDetails, pageno-1));
    }

}
