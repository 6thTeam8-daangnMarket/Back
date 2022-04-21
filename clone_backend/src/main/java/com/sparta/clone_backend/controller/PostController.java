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
import java.io.UnsupportedEncodingException;
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

    // 게시글 작성
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


    // 전체 게시글 조회, 페이징 처리 완료, 시간 변경 필요, 토큰 없이 조회 불가,,, 수정 필요
    @GetMapping("/api/posted/{pageno}")
    public PostsResponseDto showAllPost(@PathVariable("pageno") int pageno, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new PostsResponseDto(postService.showAllPost(pageno-1, userDetails));
    }


    // 특정 게시글 조히
    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(201)
                .header("status","201")
                .body(postService.getPostDetail(postId, userDetails));
    }

    // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId,
                                                    @RequestParam(value = "postTitle",required = false) String postTitle,
                                                    @RequestParam(value = "postContents",required = false) String postContents,
                                                    @RequestParam(value = "imageUrl", required = false) MultipartFile multipartFile,
                                                    @RequestParam(value = "price", required = false) int price,
                                                    @RequestParam(value = "category",required = false) String category,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails)
    throws IOException{
        System.out.println(multipartFile);
        if(multipartFile.isEmpty()){
            System.out.println("postcontroller 게시글 제목" + postTitle);
            PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents,  price, category);
            postService.editPost(postId,postRequestDto, userDetails.getUser());
        }else{
            String imageUrl = S3Uploader.updateImage(multipartFile, "static", postId);
            System.out.println("postcontroller 이미지Url : "+imageUrl);
            PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents, imageUrl, price, category);
            postService.editPost(postId,postRequestDto, userDetails.getUser());
        }

        return new ResponseEntity<String>(HttpStatus.OK);
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
    //검색 기능
    @GetMapping("/api/search/{keyword}/{pageno}")
    public PostsResponseDto getSearchPostList(
            @PathVariable(value = "keyword", required = false) String keyword, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("pageno") int pageno) throws UnsupportedEncodingException {
        return new PostsResponseDto(postService.getSearchPost(keyword, userDetails, pageno-1));
    }

    //카테고리별 조회
    @GetMapping("/api/category/{category}/{pageno}")
    public PostsResponseDto getCategoryPostList(
            @PathVariable String category, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("pageno") int pageno) throws UnsupportedEncodingException {

        return new PostsResponseDto(postService.getCategoryPost(category, userDetails, pageno-1));
    }

}
