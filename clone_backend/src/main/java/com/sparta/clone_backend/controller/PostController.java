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

//     게시글 작성 -> 토큰이 없을 경우 500에러/예외처리 필요할 것 같음 (사용자 권한 적용)
//    @PostMapping("/api/write")
//    public ResponseEntity<StatusMessage> upload(
//            @RequestParam("postTitle") String postTitle,
//            @RequestParam("postContents") String postContents,
//            @RequestParam(value = "imageUrl") MultipartFile multipartFile,
//            @RequestParam("price") int price,
//            @RequestParam("category") String category,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) throws IOException
//    {
//        String imageUrl = S3Uploader.upload(multipartFile, "static");
//
//        PostRequestDto postRequestDto = new PostRequestDto(postTitle, postContents, imageUrl, price, category);
//
//        StatusMessage statusMessage = new StatusMessage();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
//        statusMessage.setData(null);
//        postService.createPost(postRequestDto, userDetails.getUser());
//        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
//    }

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

    // 게시글 전체 조회
//    @GetMapping("/api/posts")
//    public ResponseEntity<StatusMessage> getPost() {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        StatusMessage statusMessage = new StatusMessage();
//        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        statusMessage.setStatus(StatusEnum.OK);
//        statusMessage.setData(postService.getPost());
//        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
//
//    }

//    // 게시물 전체 조회 - ResponseEntity를 활용해 페이징 처리 실험중 -> 미완성
//    @GetMapping("/api/posts")
//    public ResponseEntity<Page<PostsResponseDto>> getPost(@PageableDefault(size = 10) Pageable pageable) {
//
//        postService.getPost(pageable);
//        return ResponseEntity.status(201)
//                .header("status","201")
//                .body(postService.getPost(pageable));
//    }

    // 게시글 전체 조회
//    @GetMapping("/api/posts")
//    public ResponseEntity<List<PostsResponseDto>> getPost() {
//
//        postService.getPost();
//        return ResponseEntity.status(201)
//                .header("status", "201")
//                .body(postService.getPost());
//    }

    // 게시물 전체 조회 - ResponseEntity 사용 X
//    @GetMapping("/api/posts")
//    public List<PostListDto> getPost(@PageableDefault(size = 10) Pageable pageable) {
//        return postService.getPost(pageable);
//    }

    // 전체 게시글 조회, 페이징 처리 완료, 시간 변경 필요, 토큰 없이 조회 불가,,, 수정 필요
    @GetMapping("/api/posted/{pageno}")
    public PostsResponseDto showAllPost(@PathVariable("pageno") int pageno) {
        return new PostsResponseDto(postService.showAllPost(pageno-1));
    }

//    @GetMapping("/api/posted/{pageno}")
//    public PostsResponseDto showAllPost(@PathVariable("pageno") int pageno) {
//        return new PostsResponseDto(postService.showAllPost(pageno-1));
//    }

//    특정게시글 조회
    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<StatusMessage> getPostDetail(@PathVariable Long postId){
        HttpHeaders httpHeaders = new HttpHeaders();
        StatusMessage statusMessage = new StatusMessage();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusEnum.OK);
        statusMessage.setData(postService.getPostDetail(postId));
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<PostResponseDto> editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        StatusMessage statusMessage = new StatusMessage();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        statusMessage.setStatus(StatusEnum.OK);
//        statusMessage.setData();
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
    @GetMapping("/user/mypage")
    public UserPageResponseDto getUserPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getUserPage(userDetails);
    }
    //검색 기능
    @GetMapping("/api/search/{keyword}")
    public List<PostListDto> getSearchPostList(
            @PathVariable(value = "keyword", required = false) String keyword) throws UnsupportedEncodingException {
        return postService.getSearchPost(keyword);
    }

    //카테고리별 조회
    @GetMapping("/api/category/{category}")
    public List<PostListDto> getCategoryPostList(
            @PathVariable String category) throws UnsupportedEncodingException {

        return postService.getCategoryPost(category);
    }

}
