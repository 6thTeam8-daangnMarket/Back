package com.sparta.clone_backend.controller;


import com.sparta.clone_backend.dto.PostDetailResponseDto;
import com.sparta.clone_backend.dto.PostRequestDto;
import com.sparta.clone_backend.dto.PostsResponseDto;
import com.sparta.clone_backend.dto.UserPageResponseDto;
import com.sparta.clone_backend.model.Post;
import com.sparta.clone_backend.security.UserDetailsImpl;
import com.sparta.clone_backend.service.PostService;
import com.sparta.clone_backend.service.S3Uploader;
import com.sparta.clone_backend.utils.StatusMessage;
import com.sparta.clone_backend.validator.SearchValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final S3Uploader S3Uploader;
    private final SearchValidator searchValidator;

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
        statusMessage.setData(null);
        postService.createPost(postRequestDto, userDetails.getUser());
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

//    // 전체 게시글 조회
//    @GetMapping("/api/posts")
//    public Page<Post> getPost(@PageableDefault(size = 10) Pageable pageable
//        @RequestParam("page") int page,
//        @RequestParam("size") int size,
//        @RequestParam("sortBy") String sortBy,
//        @RequestParam("isAsc") boolean isAsc,
//        @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
//        Long userId = userDetails.getUser().getId();
//        page = page - 1;
//        return postService.getPost(pageable);
//    }

    //특정게시글 조회
    @GetMapping("/api/posts/{postId}")
    public PostDetailResponseDto getPostDetail(@PathVariable Long postId){
        return postService.getPostDetail(postId);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<StatusMessage> getPost() {
        HttpHeaders httpHeaders = new HttpHeaders();
        StatusMessage statusMessage = new StatusMessage();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        statusMessage.setData(postService.getPost());
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);

    }

    //특정게시글 조회
//    @GetMapping("/api/posts/{postId}")
//    public ResponseEntity<StatusMessage> getPostDetail(@PathVariable Long postId){
//        HttpHeaders httpHeaders = new HttpHeaders();
//        StatusMessage statusMessage = new StatusMessage();
//        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
//        statusMessage.setData(postService.getPostDetail(postId));
//        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
//    }


     // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<StatusMessage> editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        statusMessage.setData(postService.editPost(postId,requestDto, userDetails.getUser()));
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }


    // 게시글 삭제
    @DeleteMapping("api/posts/{postId}")
    public ResponseEntity<StatusMessage> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StatusMessage statusMessage = new StatusMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        statusMessage.setStatus(StatusMessage.StatusEnum.OK);
        statusMessage.setData(postService.deletePost(postId, userDetails.getUser()));
        return new ResponseEntity<>(statusMessage, httpHeaders, HttpStatus.OK);
    }

    // 유저정보, 장바구니 조회
    @GetMapping("/user/mypage")
    public UserPageResponseDto getUserPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.getUserPage(userDetails);
    }

    //직접 사용자 검색 기능
    @GetMapping("/search")
    public List<PostsResponseDto> getSearchBoardList(
            @RequestParam(value = "category", required = false) Optional<String> category,
            @RequestParam(value = "filtertype", required = false) Optional<String> filtertype,
            @RequestParam(value = "searchtitle", required = false) Optional<String> searchtitle,
            @RequestParam(value = "mapdata", required = false) Optional<String> mapdata) {
        // 각각의 변수에 대한 default값 설정
        String validmapdata = SearchValidator.DefaultMapData(mapdata);
        String validFilterData = SearchValidator.DefaultFilterData(filtertype);
        String validSearchData = SearchValidator.DefaultSearchData(searchtitle);
        String validCategory = SearchValidator.DefaultCategoryData(category);
        System.out.println(validmapdata + validFilterData + validSearchData);

        return postService.getSearchPost(validFilterData, validSearchData, validmapdata, validCategory);
    }



}
