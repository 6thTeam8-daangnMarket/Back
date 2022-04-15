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

    // 게시글 작성 - 테스트용
    @PostMapping("/api/post")
    public Post upload(
            @RequestParam("postTitle") String postTitle,
            @RequestParam("postContents") String postContents,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("price") int price,
            @RequestParam("location") String location,

            @RequestParam("nickname") String nickname
//            ,@AutentificationPrincipal UserDetailsImpl userDetails
    ) throws IOException
    {
        //userDetials.getUser().getNickname()

        PostRequestDto postRequestDto = new PostRequestDto(
                postTitle,
                postContents,
                imageUrl,
                price,
                location,
                nickname);
        return postService.savePost(postRequestDto
//      userDetails.getUser()  : postRequestDto 인자 뒤에 붙어야함
        );
    }

    // 전체 게시글 조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> getPost() {
        return postService.getPost();
    }

    //특정게시글 조회
    @GetMapping("/api/posts/{postId}")
    public PostDetailResponseDto getPostDetail(@PathVariable Long postId
//    , @AuthentificationPrincipal UserdetailsImpl userdetails
    ){
        return postService.getPostDetail(postId);
    }


}
