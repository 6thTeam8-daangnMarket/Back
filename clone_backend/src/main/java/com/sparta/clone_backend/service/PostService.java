package com.sparta.clone_backend.service;


import com.sparta.clone_backend.dto.*;

import com.sparta.clone_backend.model.Post;

import com.sparta.clone_backend.model.PostLike;
import com.sparta.clone_backend.model.User;
import com.sparta.clone_backend.repository.PostLikeRepository;
import com.sparta.clone_backend.repository.PostRepository;
import com.sparta.clone_backend.repository.UserRepository;
import com.sparta.clone_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

//    @Autowired
//    public PostService(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    };

    // 게시물 등록
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {

        Post post = Post.builder()
                        .user(user)
                        .postTitle(postRequestDto.getPostTitle())
                        .postContents(postRequestDto.getPostContents())
                        .imageUrl(postRequestDto.getImageUrl())
                        .price(postRequestDto.getPrice())
                        .location(postRequestDto.getLocation())
                        .createDate(Timestamp.valueOf(LocalDateTime.now()))
                        .updateDate(Timestamp.valueOf(LocalDateTime.now()))
                        .nickname(postRequestDto.getNickname())
                        .build();

        postRepository.save(post);

        return PostResponseDto.builder()

                .username(user.getUsername())
                .build();
    }

    // 게시글 삭제
    @Transactional
    public Object deletePost(Long postId, User user) {

        Post post = postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
                () -> new IllegalArgumentException("작성자만 삭제 가능합니다.")
        );
        Optional<PostLike> postLike = postLikeRepository.findById(postId);
        postLikeRepository.deleteById(postLike.get().getId());
        postRepository.deleteById(post.getId());

        return null;
    }

    //전체 게시글 조회
    public List<PostsResponseDto> getPost() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostsResponseDto> postsResponseDtos = new ArrayList<>();
        for (Post post : posts) {

            PostsResponseDto postsResponseDto = new PostsResponseDto(
                    post.getPostTitle(),
                    post.getImageUrl(),
                    post.getPrice(),
                    post.getLocation(),
                    post.getCreatedAt(),
                    post.getModifiedAt(),
                    post.getId(),
                    postLikeRepository.countByPost(post));
            postsResponseDtos.add(postsResponseDto);
        }
        return postsResponseDtos;
    }

    //상세 게시글 조회
    public PostDetailResponseDto getPostDetail(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).get();

        return new PostDetailResponseDto(
                post.getPostTitle(),
                post.getPostContents(),
                post.getImageUrl(),
                post.getPrice(),
                post.getLocation(),
                post.getCreatedAt(),
                postLikeRepository.countByPost(post),
                userDetails.getNickname()
        );
    }

    //유저 페이지,장바구니 조회
    public UserPageResponseDto getUserPage(UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();

        List<PostLike> postLikeObjects = postLikeRepository.findAllByUsername(username);
        List<PostsResponseDto> postsResponseDtos = new ArrayList<>();

        for (PostLike postLikeObject : postLikeObjects) {
            Post likedPost = postLikeObject.getPost();

            PostsResponseDto postsResponseDto = new PostsResponseDto(
                    likedPost.getPostTitle(),
                    likedPost.getImageUrl(),
                    likedPost.getPrice(),
                    likedPost.getLocation(),
                    likedPost.getCreatedAt(),
                    likedPost.getModifiedAt(),
                    likedPost.getId(),
                    postLikeRepository.countByPost(likedPost)
            );
            postsResponseDtos.add(postsResponseDto);

        }
        return new UserPageResponseDto(userDetails.getNickname(), postsResponseDtos);
    }

    // 게시글 수정 (아직은 내용만 수정 가능)
    @Transactional
    public PostResponseDto editPost(Long postId, PostRequestDto requestDto, User user) {

        Post post = postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
                () -> new IllegalArgumentException("작성자만 수정 가능합니다.")
        );
        post.update(postId, requestDto.getPostContents());

        PostResponseDto responseDto = new PostResponseDto(postId, post.getPostContents());
        return responseDto;
    }
}

